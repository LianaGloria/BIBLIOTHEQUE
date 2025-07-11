package biblio.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import biblio.demo.model.Adherent;
import biblio.demo.model.Emprunt;
import biblio.demo.model.Exemplaire;

@Repository
public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {
    // Exemple : nombre d’emprunts d’un adhérent
    long countByAdherentId(Long idAdherent);
    
    // Exemple : liste des emprunts en cours d’un adhérent
    // List<Emprunt> findByAdherentIdAndDateRetourReelleIsNull(Long idAdherent);

        // boolean existsByAdherentAndExemplaireLivreAndIsRenduFalseAndDateRetourPrevuAfterOrDateRetourPrevuEquals(
        // Adherent adherent, Livre livre, java.time.LocalDate today1, java.time.LocalDate today2
        // );

    // @Query("SELECT COUNT(e) > 0 FROM Emprunt e WHERE e.adherent = :adherent AND e.exemplaire.livre = :livre AND e.rendu = false AND e.dateRetourPrevu >= :today")
    // boolean hasEmpruntEnCours(@Param("adherent") Adherent adherent, @Param("livre") Livre livre, @Param("today") LocalDate today);
    List<Emprunt> findByAdherentAndRenduFalse(Adherent adherent);


    // List<Emprunt> findByAdherentAndIsRenduFalse(Adherent adherent);



    List<Emprunt> findByAdherentIdAndRenduFalse(Long adherentId);

    //  Boolean NonRenduAndEnCours() {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'NonRenduAndEnCours'");
    // }

    Emprunt findByAdherentAndExemplaireAndStatut(Adherent adherent, Exemplaire exemplaire, Emprunt.StatutEmprunt statut);



    @Query("""
    SELECT e FROM Emprunt e
    WHERE e.adherent.id = :adherentId
      AND e.exemplaire.livre.id = :livreId
      AND e.rendu = false
    """)
    List<Emprunt> findempruntsActifsParLivreEtAdherent(
        @Param("adherentId") Long adherentId,
        @Param("livreId") Long livreId
    );


    // Optional<Emprunt> findTopByExemplaireAndRenduFalseAndDatePretLessThanEqualAndDateRetourPrevuGreaterThanEqual(
    // Exemplaire exemplaire,
    // LocalDate dateDebut,
    // LocalDate dateFin

    // EmpruntRepository.java
@Query("SELECT e FROM Emprunt e WHERE e.exemplaire = :exemplaire AND e.exemplaire.statut = 'en_cours'")
List<Emprunt> findByExemplaireAndexemplairestatutIn(
    @Param("exemplaire") Exemplaire exemplaire
    // @Param("statuts") List<Exemplaire.StatutExemplaire> statuts
);




}
