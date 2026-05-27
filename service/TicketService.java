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
        System.out.println("[Ticket_Service] initialized");
    }

    public Optional<UUID> generateTicket(UUID vehicleId, UUID slotId, Vehicle.VehicleType vehicleType) {
        // Generate a new ticket for the given vehicle and parking slot
        Ticket ticket = new Ticket(vehicleId, slotId, vehicleType);
        System.out.println("[Ticket_Service] generated ticket with id: " + ticket.getId());

        // Save the ticket to the repository
        ticketRepository.save(ticket);
        System.out.println("[Ticket_Service] saved ticket with id: " + ticket.getId());

        return Optional.of(ticket.getId());
    }

    public Optional<Ticket> findTicketById(UUID ticketId) {
        // Retrieve the ticket from the repository using the ticket ID
        Optional<Ticket> ticket = ticketRepository.findById(ticketId);
        System.out.println("[Ticket_Service] retrieved ticket with id: " + ticketId);
        return ticket;
    }

    public void deactivateTicket(UUID ticketId) {
        // Deactivate the ticket by removing it from the repository
        ticketRepository.deactivateTicket(ticketId);
        System.out.println("[Ticket_Service] deactivated ticket with id: " + ticketId);
    }
}
