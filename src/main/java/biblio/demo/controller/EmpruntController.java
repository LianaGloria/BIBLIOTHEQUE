package biblio.demo.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import biblio.demo.model.Adherent;
import biblio.demo.model.Emprunt;
import biblio.demo.model.Exemplaire;
import biblio.demo.model.Reservation;
import biblio.demo.repository.AdherentRepository;
import biblio.demo.repository.EmpruntRepository;
import biblio.demo.repository.ExemplaireRepository;
import biblio.demo.repository.LivreRepository;
import biblio.demo.repository.ReservationRepository;
import biblio.demo.service.EmpruntService;

@Controller
public class EmpruntController {

    @Autowired
    private AdherentRepository adherentRepository;

    @Autowired
    private LivreRepository livreRepository;

    @Autowired
    private EmpruntRepository empruntRepository;

    @Autowired
    private EmpruntService empruntservice; // ✅ Injection du service

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    // private empruntservice empruntservice;



    @GetMapping("/emprunt")
    public String afficherFormulaireEmprunt() {
        System.out.println("afficherFormulaireEmprunt appelé");
        return "enregistrement-emprunt";
    }




@PostMapping("/emprunt")
public String enregistrerEmprunt(@RequestParam Long idAdherent,
                                @RequestParam Long idExemplaire,
                                @RequestParam String typePret,
                                @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate datePret,
                                Model model) {

    Adherent adherent = adherentRepository.findById(idAdherent)
            .orElseThrow(() -> new RuntimeException("Adhérent introuvable"));

    Exemplaire exemplaire = exemplaireRepository.findById(idExemplaire)
            .orElseThrow(() -> new RuntimeException("Exemplaire introuvable"));


    String erreur = empruntservice.verifierEligibilite(adherent, exemplaire, datePret, typePret);
    if (erreur != null) {
        model.addAttribute("error", erreur);
        model.addAttribute("restePret", adherent.getRestePret());
        return "enregistrement-emprunt";
    }

    Emprunt emprunt = new Emprunt();
    emprunt.setAdherent(adherent);
    emprunt.setExemplaire(exemplaire);
    emprunt.setTypePret(typePret);
    emprunt.setDatePret(datePret);
    emprunt.setRendu(false);

    LocalDate dateRetourPrevu = datePret;
    if ("emporte".equalsIgnoreCase(typePret)) {
        adherent.setRestePret(adherent.getRestePret() - 1);
        adherentRepository.save(adherent);

        int duree = adherent.getProfil().getDureeEmporte();
        dateRetourPrevu = empruntservice.calculerDateRetourAvecFeries(datePret, duree);
    }

    emprunt.setDateRetourPrevu(dateRetourPrevu);
    empruntRepository.save(emprunt);

    // ✅ Traitement spécial si l'exemplaire était réservé
    if (exemplaire.getStatut() == Exemplaire.StatutExemplaire.reserve) {
        // Récupérer la réservation correspondante
        Reservation reservation = reservationRepository.findTopByAdherentAndExemplaireOrderByDateReservationDesc(
            adherent, exemplaire
        );

        if (reservation != null && reservation.getDateReservation().isEqual(datePret)) {
            // Ré-incrémenter quota
            adherent.setResteReservation(adherent.getResteReservation() + 1);
            adherentRepository.save(adherent);

            // Mettre à jour la réservation
            reservation.setStatutDemande(Reservation.StatutDemande.expiree);
            reservation.setStatutReservation(Reservation.StatutReservation.termine);
            reservationRepository.save(reservation);
        }
    }

    exemplaire.setStatut(Exemplaire.StatutExemplaire.emprunte);
    exemplaireRepository.save(exemplaire);

    return "redirect:/emprunts";
}






    @GetMapping("/emprunts")
    public String listeremprunts(Model model) {
        model.addAttribute("emprunts", empruntRepository.findAll());
        return "emprunts"; // nom de la vue Thymeleaf à afficher
    }

}
