package biblio.demo.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import biblio.demo.model.Livre;
import biblio.demo.repository.ExemplaireRepository;
import biblio.demo.repository.LivreRepository;

@RestController
@RequestMapping("/api/livres")
public class LivreAPIController {

    @Autowired
    private LivreRepository livreRepository;

    @Autowired
    private ExemplaireRepository exemplaireRepository;


    @GetMapping("/{id}")
    public ResponseEntity<?> getLivreAvecExemplaires(@PathVariable Long id) {
        Optional<Livre> optionalLivre = livreRepository.findById(id);
        if (optionalLivre.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Livre non trouvé");
        }

        Livre livre = optionalLivre.get();

        List<Map<String, Object>> exemplairesList = exemplaireRepository.findByLivre(livre).stream().map(ex -> {
            Map<String, Object> exJson = new HashMap<>();
            exJson.put("id", ex.getId());
            exJson.put("statut", ex.getStatut());
            return exJson;
        }).toList();

        Map<String, Object> response = new HashMap<>();
        response.put("id", livre.getId());
        response.put("titre", livre.getTitre());
        response.put("auteur", livre.getAuteur());
        response.put("description", livre.getDescription());
        response.put("ageMin", livre.getAgeMin());
        response.put("exemplaires", exemplairesList);

        return ResponseEntity.ok(response);
    }
}
