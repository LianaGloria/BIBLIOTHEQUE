package biblio.demo.repository;

import biblio.demo.model.Emprunt;
import biblio.demo.model.Retour;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RetourRepository extends JpaRepository<Retour, Long> {


}
