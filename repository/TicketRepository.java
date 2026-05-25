package repository;
import domain.Ticket;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TicketRepository {
    private Map<UUID, Ticket> tickets;

    public TicketRepository() {
        tickets = new ConcurrentHashMap<>();
    }


    public void save(Ticket ticket) {
        tickets.put(ticket.getId(), ticket);
    }

    public Ticket findById(UUID ticketId) {
        return tickets.get(ticketId);
    }
}
