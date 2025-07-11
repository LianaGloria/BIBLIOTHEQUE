package biblio.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biblio.demo.model.Adherent;
import biblio.demo.model.Penalite;
import biblio.demo.repository.AdherentRepository;
import biblio.demo.repository.PenaliteRepository;
import jakarta.annotation.PostConstruct;

@Service
public class NettoyagePenaliteService {

    @Autowired
    private PenaliteRepository penaliteRepository;

    @Autowired
    private AdherentRepository adherentRepository;

    @PostConstruct
    public void nettoyerpenalites() {
        LocalDate aujourdHui = LocalDate.now();
        List<Penalite> penalites = penaliteRepository.findpenalitesARevoquer(aujourdHui);

        for (Penalite p : penalites) {
            // Si déjà terminé, ne rien faire
            if (p.getStatut() != biblio.demo.model.Penalite.StatutPenalite.termine) {
                p.setStatut(biblio.demo.model.Penalite.StatutPenalite.termine);
                penaliteRepository.save(p);

                Adherent a = p.getEmprunt().getAdherent();
                a.setPenaliteActive(false);
                adherentRepository.save(a);

                System.out.println("Penalite terminee pour l adhérent ID : " + a.getId());
            }
        }
    }
}
