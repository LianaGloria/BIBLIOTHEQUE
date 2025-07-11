package biblio.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import biblio.demo.model.Adherent;
import biblio.demo.model.Penalite;
import biblio.demo.model.Penalite.StatutPenalite;
import biblio.demo.repository.AdherentRepository;
import biblio.demo.repository.PenaliteRepository;
import jakarta.transaction.Transactional;

public class PenaliteService {
    
    @Autowired
    private PenaliteRepository penaliteRepository;

    @Autowired
    private AdherentRepository adherentRepository;

    @Transactional
    public void verifierpenalitesExpirees() {
        LocalDate today = LocalDate.now();

        List<Penalite> penalites = penaliteRepository.findpenalitesARevoquer(today);

        for (Penalite p : penalites) {
            Adherent a = p.getEmprunt().getAdherent();

            a.setPenaliteActive(false);
            adherentRepository.save(a);

            p.setStatut(StatutPenalite.termine);
            penaliteRepository.save(p);
        }
    }
}
