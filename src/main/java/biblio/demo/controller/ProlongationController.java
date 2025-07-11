package biblio.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import biblio.demo.model.Adherent;
import biblio.demo.model.Emprunt;
import biblio.demo.model.Prolongation;
import biblio.demo.repository.AdherentRepository;
import biblio.demo.repository.EmpruntRepository;
import biblio.demo.repository.ProlongationRepository;

@Controller
public class ProlongationController {

    @Autowired
    private ProlongationRepository prolongationRepository;

    @Autowired
    private EmpruntRepository empruntRepository;

    @Autowired
    private AdherentRepository adherentRepository;

    @GetMapping("/prolongations")
    public String listeprolongations(Model model) {
        // Récupérer les prolongations dont le statut de demande est 'en_attente'
        List<Prolongation> demandes = prolongationRepository.findByStatutDemande(Prolongation.StatutDemande.en_attente);

        // Ajouter la liste au modèle pour Thymeleaf
        model.addAttribute("demandes", demandes);

        // Retourner le nom de la vue Thymeleaf à afficher
        return "prolongations";
    }

    // cote adherent
    @PostMapping("/prolonger")
    public String demanderProlongation(@RequestParam long empruntId, RedirectAttributes redirectAttrs) {
        try {
            Emprunt emprunt = empruntRepository.findById(empruntId).orElseThrow();
            Adherent adherent = emprunt.getAdherent();
            // ✅ Vérification du statut de l'adhérent
            if (!"actif".equalsIgnoreCase(adherent.getStatut())) {
                redirectAttrs.addFlashAttribute("messageError", "Votre statut d’adhérent ne permet pas la demande de prolongation.");
                return "redirect:/liste-emprunts";
            }

            Prolongation p = new Prolongation();
            p.setEmprunt(emprunt);
            p.setDateDemande(LocalDate.now());
            p.setStatutDemande(Prolongation.StatutDemande.en_attente);
            p.setStatutProlongation(Prolongation.StatutProlongation.en_attente);

            prolongationRepository.save(p);

            redirectAttrs.addFlashAttribute("messageSuccess", "Votre demande de prolongation a bien été reçue.");
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttrs.addFlashAttribute("messageError", "Erreur lors de la demande de prolongation.");
        }

        return "redirect:/liste-emprunts";
    }


    @PostMapping("/prolongations/accepter")
    public String accepterProlongation(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
        Prolongation p = prolongationRepository.findById(id).orElseThrow();
        Emprunt emprunt = p.getEmprunt();
        Adherent adherent = emprunt.getAdherent();

        // Vérifications d'éligibilité
        if (!"actif".equalsIgnoreCase(adherent.getStatut())) {
            redirectAttributes.addFlashAttribute("message", "Refus : Adhérent inactif.");
            return "redirect:/prolongations";
        }

// // Vérification du statut de l'exemplaire
// Exemplaire exemplaire = emprunt.getExemplaire();
// if (exemplaire.getStatut() != Exemplaire.StatutExemplaire.emprunte) {
//     redirectAttributes.addFlashAttribute("message", "Refus : L'exemplaire n'est pas actuellement emprunté.");
//     return "redirect:/prolongations";
// }

// // Vérification de réservation future
// LocalDate dateReservation = exemplaire.getDateReservation(); // getter à ajouter si nécessaire
// LocalDate ancienneDateRetour = emprunt.getDateRetourPrevu();
// int nbJoursProlongation = adherent.getProfil().getDureeProlongation();
// LocalDate nouvelleDateRetour = ancienneDateRetour.plusDays(nbJoursProlongation);

// if (exemplaire.getStatut() == Exemplaire.StatutExemplaire.reserve &&
//     dateReservation != null &&
//     ( !dateReservation.isBefore(ancienneDateRetour) && !dateReservation.isAfter(nouvelleDateRetour) )) {
    
//     redirectAttributes.addFlashAttribute("message", "Refus : Ce livre est réservé dans la période de prolongation demandée.");
//     return "redirect:/prolongations";
// }


        if (adherent.isPenaliteActive()) {
            redirectAttributes.addFlashAttribute("message", "Refus : Pénalité active.");
            return "redirect:/prolongations";
        }

        if (adherent.getResteProlongation() <= 0) {
            redirectAttributes.addFlashAttribute("message", "Refus : Quota de prolongation épuisé.");
            return "redirect:/prolongations";
        }

        // Mise à jour des statuts
        p.setStatutDemande(Prolongation.StatutDemande.acceptee);
        p.setStatutProlongation(Prolongation.StatutProlongation.en_cours);

        // Calcul de la nouvelle date de retour
        int nbJoursProlongation = adherent.getProfil().getDureeProlongation();
        LocalDate nouvelleDateRetour = emprunt.getDateRetourPrevu().plusDays(nbJoursProlongation);
        p.setNouvelleDateRetour(nouvelleDateRetour);

        // Mise à jour de l'emprunt
        emprunt.setDateRetourPrevu(nouvelleDateRetour);
        emprunt.setProlongation(true);
        emprunt.setNbprolongations(emprunt.getNbprolongations() + 1);
        empruntRepository.save(emprunt);

        // Mise à jour du quota
        adherent.setResteProlongation(adherent.getResteProlongation() - 1);

        // Sauvegardes
        prolongationRepository.save(p);
        // Mise à jour du quota
        adherentRepository.save(adherent); // ✅ À ajouter


        redirectAttributes.addFlashAttribute("message", "Prolongation acceptée avec succès.");

        return "redirect:/prolongations";
    }




    @PostMapping("/prolongations/refuser")
    public String refuserProlongation(@RequestParam Integer id) {
        Prolongation p = prolongationRepository.findById(id).orElseThrow();
        p.setStatutDemande(Prolongation.StatutDemande.refusee);
        p.setStatutProlongation(Prolongation.StatutProlongation.termine);
        prolongationRepository.save(p);
        return "redirect:/prolongations";
    }


}
