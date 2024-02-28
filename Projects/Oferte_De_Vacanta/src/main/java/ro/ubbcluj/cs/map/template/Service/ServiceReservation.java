package ro.ubbcluj.cs.map.template.Service;

import ro.ubbcluj.cs.map.template.Domain.Reservation;
import ro.ubbcluj.cs.map.template.Exception.RepositoryException;
import ro.ubbcluj.cs.map.template.Exception.ServiceException;
import ro.ubbcluj.cs.map.template.Repository.ReservationDBRepository;
import ro.ubbcluj.cs.map.template.Utilities.Event.EventType;
import ro.ubbcluj.cs.map.template.Utilities.Event.ReservationEvent;
import ro.ubbcluj.cs.map.template.Utilities.Observer.ObservableImplemented;

import java.time.LocalDateTime;
import java.util.Optional;

public class ServiceReservation extends ObservableImplemented<ReservationEvent> {
    private final ReservationDBRepository reservationDBRepository;

    public ServiceReservation(ReservationDBRepository reservationDBRepository) {
        this.reservationDBRepository = reservationDBRepository;
    }

    public void reserveRoom(Long clientId, Double hotelId, LocalDateTime startDate, int noNights) throws ServiceException {
        try {
            double id = this.reservationDBRepository.getValidId();
            Reservation reservation = new Reservation(id, clientId, hotelId, startDate, noNights);
            Optional<Reservation> optionalReservation = this.reservationDBRepository.save(reservation);
            if (optionalReservation.isPresent()) {
                throw new ServiceException("This reservation already exists!");
            }
            this.notify(new ReservationEvent(EventType.RESERVATION_ADDED, reservation));
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
