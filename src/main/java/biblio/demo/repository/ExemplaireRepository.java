package biblio.demo.repository;

import biblio.demo.model.Exemplaire;
import biblio.demo.model.Exemplaire.StatutExemplaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExemplaireRepository extends JpaRepository<Exemplaire, Long> {

    // Trouve un exemplaire disponible par id du livre et statut
    Optional<Exemplaire> findFirstByLivreIdAndStatut(Long livreId, StatutExemplaire statut);
    
    // Vérifie s'il existe au moins un exemplaire avec ce statut pour un livre donné
    boolean existsByLivreIdAndStatut(Long livreId, StatutExemplaire statut);

    List<Exemplaire> findByStatut(StatutExemplaire statut);

}
