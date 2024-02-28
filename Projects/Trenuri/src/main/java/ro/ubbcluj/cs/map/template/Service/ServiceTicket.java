package ro.ubbcluj.cs.map.template.Service;

import ro.ubbcluj.cs.map.template.Domain.Ticket;
import ro.ubbcluj.cs.map.template.Domain.Tuple;
import ro.ubbcluj.cs.map.template.Exception.RepositoryException;
import ro.ubbcluj.cs.map.template.Exception.ServiceException;
import ro.ubbcluj.cs.map.template.Repository.TicketDBRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ServiceTicket {
    private final TicketDBRepository ticketDBRepository;

    public ServiceTicket(TicketDBRepository ticketDBRepository) {
        this.ticketDBRepository = ticketDBRepository;
    }

    /**
     * Adds a ticket to the repository.
     * @param trainId Train ID of the ticket.
     * @param departureCityId Departure city ID of the ticket.
     * @param date Date of the ticket.
     * @throws ServiceException If a problem was encountered during the operation.
     */
    public void addTicket(String trainId, String departureCityId, LocalDate date) throws ServiceException {
        try {
            Ticket ticket = new Ticket(trainId, departureCityId, date);
            this.ticketDBRepository.save(ticket);
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Returns a map that contains the ticket and the number of tickets bought of that type.
     * @param departureCityId ID of the departure city.
     * @param date Date of the tickets.
     * @return Map that contains the ticket and the number of tickets bought of that type.
     * @throws ServiceException If a problem was encountered during the operation.
     */
    public Map<Ticket, Integer> countTickets(String departureCityId, LocalDate date) throws ServiceException {
        try {
            List<Ticket> tickets = this.ticketDBRepository.getTicketsOnDate(departureCityId, date);
            return tickets.stream().collect(Collectors.toMap(Function.identity(), ticket -> 1, Integer::sum));
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    /**
     * Returns the most sold ticket.
     * @return Tuple that contains the train ID and the date of the most sold ticket.
     * @throws ServiceException If a problem was encountered during the operation.
     */
    public Tuple<String, LocalDate> getMostSoldTicket() throws ServiceException {
        try {
            return this.ticketDBRepository.getMostSoldTicket();
        } catch (RepositoryException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
