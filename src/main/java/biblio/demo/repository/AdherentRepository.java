package biblio.demo.repository;

import biblio.demo.model.Adherent;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdherentRepository extends JpaRepository<Adherent, Long> {
    Optional<Adherent> findByNomAndEmailAndMotDePasse(String nom, String email, String motDePasse);

    @Modifying
    @Transactional
    @Query("UPDATE Adherent a SET a.restePret = :nouveau WHERE a.id = :id")
    void updateRestePret(@Param("id") Long id, @Param("nouveau") int nouveau);

    @Modifying
    @Transactional
    @Query("UPDATE Adherent a SET a.restePret = a.restePret + 1 WHERE a.id = :id")
    void incrementRestePret(@Param("id") Long id);

    Optional<Adherent> findByEmailAndMotDePasse(String email, String motDePasse);

}

