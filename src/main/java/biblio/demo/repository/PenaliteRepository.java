package biblio.demo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import biblio.demo.model.Adherent;
import biblio.demo.model.Penalite;
import biblio.demo.model.Penalite.StatutPenalite;

public interface PenaliteRepository extends JpaRepository<Penalite, Long> {

    List<Penalite> findByEmprunt_AdherentAndDateEmpruntAutoriseAfter(Adherent adherent, LocalDate now);

    @Query("SELECT p FROM Penalite p WHERE p.dateEmpruntAutorise = :aujourdHui AND p.statut <> 'termine'")
    List<Penalite> findpenalitesARevoquer(@Param("aujourdHui") LocalDate aujourdHui);
    List<Penalite> findByEmprunt_AdherentAndStatut(Adherent adherent, StatutPenalite statut);
    Optional<Penalite> findTopByEmprunt_AdherentAndStatutOrderByDateCreationDesc(Adherent adherent, Penalite.StatutPenalite statut);

}
