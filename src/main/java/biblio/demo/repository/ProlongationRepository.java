package biblio.demo.repository;

import biblio.demo.model.Prolongation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProlongationRepository extends JpaRepository<Prolongation, Integer> {
    List<Prolongation> findByStatutDemande(Prolongation.StatutDemande statut);
}
