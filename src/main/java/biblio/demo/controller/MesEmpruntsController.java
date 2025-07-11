package biblio.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import biblio.demo.model.Adherent;
import biblio.demo.model.Emprunt;
import biblio.demo.repository.EmpruntRepository;
import jakarta.servlet.http.HttpSession;

@Controller  
public class MesEmpruntsController {

    @Autowired
    private EmpruntRepository empruntRepository;

    @GetMapping("/liste-emprunts")
    public String voirPrets(Model model, HttpSession session) {
        Adherent adherent = (Adherent) session.getAttribute("adherentConnecte");
        if (adherent == null) return "redirect:/login";
    
        List<Emprunt> emprunts = empruntRepository.findByAdherentIdAndRenduFalse(adherent.getId());
        model.addAttribute("emprunts", emprunts);
        return "liste-emprunts";
    }

}
