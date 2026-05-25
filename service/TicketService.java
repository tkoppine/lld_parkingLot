package service;
import domain.Ticket;
import domain.Vehicle;
import java.util.Optional;
import java.util.UUID;
import repository.TicketRepository;

public class TicketService {
    TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Optional<UUID> generateTicket(UUID vehicleId, UUID slotId, Vehicle.VehicleType vehicleType) {
        Ticket ticket = new Ticket(vehicleId, slotId, vehicleType);
        ticketRepository.save(ticket);
        return Optional.of(ticket.getId());
    }

    public Optional<Ticket> findTicketById(UUID ticketId) {
        Optional<Ticket> ticket = ticketRepository.findById(ticketId);
        return ticket;
    }

    public void deactivateTicket(UUID ticketId) {
        ticketRepository.deactivateTicket(ticketId);
    }
}
