// package biblio.demo.controller;

// import biblio.demo.service.livreservice;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;

// @Controller
// public class LivreController {

//     @Autowired
//     private livreservice livreservice;

//     @GetMapping("/livres")
//     public String afficherlivres(Model model) {
//         model.addAttribute("livres", livreservice.getlivres());
//         return "livres";
//     }
// }


package biblio.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import biblio.demo.model.Exemplaire;
import biblio.demo.repository.ExemplaireRepository;

@Controller
public class LivreController {

    @Autowired
    private ExemplaireRepository exemplaireRepository;

    @GetMapping("/livres")
    public String listelivres(Model model) {
        List<Exemplaire> tousexemplaires = exemplaireRepository.findAll();
        model.addAttribute("exemplaires", tousexemplaires);
        return "livres";
    }

}
