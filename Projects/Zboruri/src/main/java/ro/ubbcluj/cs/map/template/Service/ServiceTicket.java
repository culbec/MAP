package ro.ubbcluj.cs.map.template.Service;

import ro.ubbcluj.cs.map.template.Domain.Flight;
import ro.ubbcluj.cs.map.template.Domain.FlightDTO;
import ro.ubbcluj.cs.map.template.Domain.Ticket;
import ro.ubbcluj.cs.map.template.Exception.RepositoryException;
import ro.ubbcluj.cs.map.template.Exception.ServiceException;
import ro.ubbcluj.cs.map.template.Repository.TicketDBRepository;
import ro.ubbcluj.cs.map.template.Utilities.Event.EventType;
import ro.ubbcluj.cs.map.template.Utilities.Event.TicketEvent;
import ro.ubbcluj.cs.map.template.Utilities.Observer.ObservableImplemented;

public class ServiceTicket extends ObservableImplemented<TicketEvent> {
    private final TicketDBRepository ticketDBRepository;

    public ServiceTicket(TicketDBRepository ticketDBRepository) {
        this.ticketDBRepository = ticketDBRepository;
    }

    public void buyTicket(Ticket ticket) {
        try {
            this.ticketDBRepository.saveTicket(ticket);
            Flight flight = this.ticketDBRepository.getFlightOfTicket(ticket.getFlightId());
            int numberOfTickets = this.ticketDBRepository.getNumberOfTicketsOfFlight(flight.getId());

            this.notify(new TicketEvent(EventType.TICKET_BOUGHT, new FlightDTO(flight, numberOfTickets), new FlightDTO(flight, numberOfTickets - 1)));
        } catch (RepositoryException e) {
            throw new ServiceException("Cause: " + e.getMessage());
        }
    }

    public int getNumberOfTicketsOfFlight(Flight flight) {
        return this.ticketDBRepository.getNumberOfTicketsOfFlight(flight.getId());
    }
}
