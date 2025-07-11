package biblio.demo.repository;

import biblio.demo.model.JourFerme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface JourFermeRepository extends JpaRepository<JourFerme, Long> {
    boolean existsByDate(LocalDate date);
    List<JourFerme> findByDateAfter(LocalDate date); // optionnel pour optimiser le calcul
}
