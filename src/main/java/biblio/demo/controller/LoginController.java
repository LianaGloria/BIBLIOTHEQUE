package biblio.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import biblio.demo.model.Adherent;
import biblio.demo.repository.AdherentRepository;
import jakarta.servlet.http.HttpSession;


@Controller
public class LoginController {

    @Autowired
    private AdherentRepository adherentRepository;


    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "login"; // va chercher templates/login.html
    }

    @PostMapping("/login")
    public String checkLogin(@RequestParam("email") String email,
                             @RequestParam("password") String motDePasse,
                             Model model,
                             HttpSession session) {
        Optional<Adherent> adherentOpt = adherentRepository.findByEmailAndMotDePasse(email, motDePasse);
                            
        if (adherentOpt.isPresent()) {
            // session.setAttribute("adherentId", adherentOpt.getId());
            session.setAttribute("adherentConnecte", adherentOpt.get());
            return "redirect:/liste-emprunts"; // redirige vers la liste des emprunts de l'adhérent
        } else {
            model.addAttribute("error", "Identifiants incorrects !");
            return "login"; // reste sur la page login
        }
    }


    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttrs) {
        session.invalidate();
        redirectAttrs.addFlashAttribute("messageSuccess", "Vous avez été déconnecté.");
        return "redirect:/login";
    }

    
}
