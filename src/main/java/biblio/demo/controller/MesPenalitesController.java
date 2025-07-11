package biblio.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import biblio.demo.model.Adherent;
import biblio.demo.model.Penalite;
import biblio.demo.model.Penalite.StatutPenalite;
import biblio.demo.repository.PenaliteRepository;
import jakarta.servlet.http.HttpSession;

@Controller
public class MesPenalitesController {

    @Autowired
    private PenaliteRepository penaliteRepository;

    @GetMapping("/mes-penalites")
    public String afficherpenalites(HttpSession session, Model model) {
        Adherent adherent = (Adherent) session.getAttribute("adherentConnecte");

        if (adherent == null) {
            return "redirect:/login";
        }

        List<Penalite> penalites = penaliteRepository.findByEmprunt_AdherentAndStatut(adherent, StatutPenalite.en_cours);
        model.addAttribute("penalites", penalites);

        return "mes-penalites";
    }
}
