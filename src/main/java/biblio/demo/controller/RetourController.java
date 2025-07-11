package biblio.demo.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import biblio.demo.repository.AdherentRepository;
import biblio.demo.repository.RetourRepository;
import biblio.demo.service.RetourService;

@Controller
public class RetourController {

    @Autowired
    private RetourService retourservice;

    @Autowired
    private RetourRepository retourRepository;

    @Autowired
    private AdherentRepository adherentRepository;

    @GetMapping("/retour")
    public String afficherFormulaireRetour() {
        return "enregistrement-retour";
    }

    @PostMapping("/retour")
    public String traiterRetour(@RequestParam Long idAdherent,
                                @RequestParam Long idExemplaire,
                                @RequestParam("dateRetour") @org.springframework.format.annotation.DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateRetour,
                                Model model) {

        String erreur = retourservice.gererRetour(idAdherent, idExemplaire, dateRetour);

        if (erreur != null) {
            model.addAttribute("error", erreur);
            return "enregistrement-retour";
        }

        return "redirect:/retours";
    }


    @GetMapping("/retours")
    public String afficherListeretours(Model model) {
        model.addAttribute("retours", retourRepository.findAll());
        return "retours";
    }

//     @GetMapping("/test-increment")
// public String testIncrement() {
//     Adherent adherent = adherentRepository.findById(113L).get();
//     System.out.println("Avant: " + adherent.getRestePret());

//     adherent.setRestePret(adherent.getRestePret() + 1);
//     adherentRepository.save(adherent);

//     Adherent updated = adherentRepository.findById(113L).get();
//     System.out.println("Après: " + updated.getRestePret());

//     return "OK";
// }


}
