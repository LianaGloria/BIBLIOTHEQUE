package biblio.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import biblio.demo.model.Reservation;
import biblio.demo.model.Adherent;
import biblio.demo.model.Exemplaire;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByAdherent(Adherent adherent);

    List<Reservation> findByStatutDemande(Reservation.StatutDemande statutDemande);

    boolean existsByAdherentAndExemplaireAndStatutReservationNot(Adherent adherent, Exemplaire exemplaire, Reservation.StatutReservation statut);

    Reservation findTopByAdherentAndExemplaireOrderByDateReservationDesc(Adherent adherent, Exemplaire exemplaire);


    List<Reservation> findByStatutDemandeAndStatutReservation(
        Reservation.StatutDemande statutDemande,
        Reservation.StatutReservation statutReservation
    );

    Optional findById(Long id);
}
