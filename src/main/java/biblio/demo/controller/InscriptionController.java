package biblio.demo.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import biblio.demo.model.Adherent;
import biblio.demo.model.Profil;
import biblio.demo.model.Quotas;
import biblio.demo.repository.AdherentRepository;
import biblio.demo.repository.ProfilRepository;@Controller
public class InscriptionController {

    @Autowired
    private AdherentRepository adherentRepository;

    @Autowired
    private ProfilRepository profilRepository;

    @GetMapping("/inscription")
    public String afficherFormulaire(Model model) {
        model.addAttribute("adherent", new Adherent());
        model.addAttribute("profils", profilRepository.findAll());
        // System.out.println(profilRepository.findAll());
        return "inscription-adherent";
    }

    @PostMapping("/inscription")
    public String inscrire(@ModelAttribute Adherent adherent,
                            @RequestParam("profilId") Long profilId,
                            Model model) {

        // 1. Lier le profil
        Profil profil = profilRepository.findById(profilId)
                .orElseThrow(() -> new RuntimeException("Profil introuvable"));
        adherent.setProfil(profil);

        // 2. Vérifier si déjà existant
        Optional<Adherent> existant = adherentRepository.findByNomAndEmailAndMotDePasse(
                adherent.getNom(), adherent.getEmail(), adherent.getMotDePasse());

        if (existant.isPresent()) {
            model.addAttribute("error", "Cet adhérent est déjà inscrit !");
            model.addAttribute("profils", profilRepository.findAll());
            return "inscription-adherent";
        }

        // 3. Définir la date d’expiration
        LocalDate aujourdHui = LocalDate.now();
        int mois = profil.getDureeAdhesion(); // <-- attention au getter exact
        LocalDate dateExpiration = aujourdHui.plusMonths(mois);
        adherent.setDateExpiration(dateExpiration);

        // 4. Définir le statut (optionnel ici, car par défaut dans la classe)
        adherent.setStatut("actif");
        adherent.setPenaliteActive(false);


        // Initialiser les quotas restants depuis la table `quotas`
        Quotas quotas = profil.getquotas();
        if (quotas != null) {
            adherent.setRestePret(quotas.getPret());
            adherent.setResteProlongation(quotas.getProlongation());
            adherent.setResteReservation(quotas.getReservation());
        } else {
            // En cas de bug ou profil sans quotas associés
            adherent.setRestePret(0);
            adherent.setResteProlongation(0);
            adherent.setResteReservation(0);
        }


        // 5. Sauvegarder
        adherentRepository.save(adherent);

        return "redirect:/adherents";
    }



    @GetMapping("/adherents")
    public String listeradherents(Model model) {
        List<Adherent> tous = adherentRepository.findAll();
        LocalDate now = LocalDate.now();
    
        for (Adherent a : tous) {
            if (a.getDateExpiration() != null && now.isAfter(a.getDateExpiration())) {
                if (!"expiré".equals(a.getStatut())) {
                    a.setStatut("expiré");
                    adherentRepository.save(a);
                }
            }
        }
    
        model.addAttribute("adherents", tous);
        return "liste-adherents";
    }

}

