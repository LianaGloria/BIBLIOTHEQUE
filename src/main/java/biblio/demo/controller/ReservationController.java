package biblio.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import biblio.demo.model.Adherent;
import biblio.demo.model.Exemplaire;
import biblio.demo.model.Exemplaire.StatutExemplaire;
import biblio.demo.model.Reservation;
import biblio.demo.repository.AdherentRepository;
import biblio.demo.repository.ExemplaireRepository;
import biblio.demo.repository.ReservationRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class ReservationController {

    @Autowired
    private AdherentRepository adherentRepository;

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    // POST /reserver
@PostMapping("/reserver")
public String reserverExemplaire(@RequestParam("exemplaireId") Long exemplaireId,
                                 @RequestParam("dateReservation") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateReservation,
                                 HttpSession session,
                                 RedirectAttributes redirectAttrs) {

    Adherent adherent = (Adherent) session.getAttribute("adherentConnecte");
    if (adherent == null) {
        redirectAttrs.addFlashAttribute("messageError", "Vous devez être connecté pour réserver.");
        return "redirect:/login";
    }

    System.out.println(adherent.getResteReservation());

    if (adherent.getResteReservation() <= 0) {
        redirectAttrs.addFlashAttribute("messageError", "Quota de réservation atteint.");
        return "redirect:/livres";
    }

if (!"actif".equals(adherent.getStatut())) {
    redirectAttrs.addFlashAttribute("messageError", "Votre abonnement est expiré.");
    return "redirect:/livres";
}


    Exemplaire exemplaire = exemplaireRepository.findById(exemplaireId).orElse(null);
    if (exemplaire == null) {
        redirectAttrs.addFlashAttribute("messageError", "Exemplaire introuvable.");
        return "redirect:/livres";
    }

    // Vérifier si une réservation existe déjà
    boolean dejaReserve = reservationRepository.existsByAdherentAndExemplaireAndStatutReservationNot(
        adherent, exemplaire, Reservation.StatutReservation.termine
    );
    if (dejaReserve) {
        redirectAttrs.addFlashAttribute("messageError", "Vous avez déjà réservé cet exemplaire.");
        return "redirect:/livres";
    }

    // Créer la réservation
    Reservation reservation = new Reservation();
    reservation.setAdherent(adherent);
    reservation.setExemplaire(exemplaire);
    reservation.setDateReservation(dateReservation);
    reservation.setStatutDemande(Reservation.StatutDemande.en_attente);
    reservation.setStatutReservation(Reservation.StatutReservation.en_attente);

    reservationRepository.save(reservation);

    exemplaire.setStatut(StatutExemplaire.reserve);
    exemplaireRepository.save(exemplaire);

    adherent.setResteReservation(adherent.getResteReservation() - 1);
    adherentRepository.save(adherent);

    redirectAttrs.addFlashAttribute("messageSuccess", "Réservation enregistrée !");
    return "redirect:/livres";
}




    @GetMapping("/mes-reservations")
    public String voirMesreservations(HttpSession session, Model model, RedirectAttributes redirectAttrs) {
        Adherent adherentOpt = (Adherent) session.getAttribute("adherentConnecte");
    
        if (adherentOpt == null) {
            redirectAttrs.addFlashAttribute("messageError", "Vous devez être connecté pour voir vos réservations.");
            return "redirect:/login";
        }
    
        // Adherent adherent = adherentOpt.get();
        List<Reservation> mesreservations = reservationRepository.findByAdherent(adherentOpt);
        model.addAttribute("reservations", mesreservations);
    
        return "mes-reservations"; // nom du fichier HTML
    }
}
