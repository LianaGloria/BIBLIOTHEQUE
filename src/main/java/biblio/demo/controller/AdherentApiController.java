package biblio.demo.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import biblio.demo.model.Adherent;
import biblio.demo.repository.AdherentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/adherents")
public class AdherentApiController {

    @Autowired
    private AdherentRepository adherentRepository;

    @GetMapping("/{id}")
    public ResponseEntity<?> getAdherentDetails(@PathVariable Long id) {
        Optional<Adherent> optionalAdherent = adherentRepository.findById(id);
        if (optionalAdherent.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Adhérent non trouvé");
        }

        Adherent adherent = optionalAdherent.get();

        Map<String, Object> response = new HashMap<>();
        response.put("id", adherent.getId());
        response.put("nom", adherent.getNom());
        response.put("prenom", adherent.getPrenom());
        response.put("email", adherent.getEmail());
        response.put("adresse", adherent.getAdresse());
        response.put("statutAbonnement", adherent.getDateExpiration().isAfter(LocalDate.now()) ? "actif" : "expiré");
        response.put("restePret", adherent.getRestePret());
        response.put("resteProlongation", adherent.getResteProlongation());
        response.put("resteReservation", adherent.getResteReservation());
        response.put("penalite", adherent.isPenaliteActive() ? "oui" : "non");

        return ResponseEntity.ok(response);
    }
}
