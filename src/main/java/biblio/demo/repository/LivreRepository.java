package biblio.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biblio.demo.model.Livre;

@Repository
public interface LivreRepository extends JpaRepository<Livre, Long> {
    // Tu peux ajouter ici des méthodes personnalisées si besoin
}
