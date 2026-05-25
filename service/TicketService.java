package service;
import domain.Ticket;
import java.util.Optional;
import java.util.UUID;
import repository.TicketRepository;

public class TicketService {
    TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Optional<UUID> generateTicket(UUID vehicleId, UUID slotId) {
        Ticket ticket = new Ticket(vehicleId, slotId);
        ticketRepository.save(ticket);
        return Optional.of(ticket.getId());
    }
}
