package controller;

import domain.Vehicle;
import java.util.Optional;
import java.util.UUID;
import service.SlotService;
import service.TicketService;

public class EntryController {
    private final TicketService ticketService;
    private final SlotService slotService;

    public EntryController(TicketService ticketService, SlotService slotService) {
        this.ticketService = ticketService;
        this.slotService = slotService;
        System.out.println("EntryController initialized with TicketService and SlotService.");
    }

    public EntryResult enterVehicle(String licensePlate, Vehicle.VehicleType vehicleType) {
        try {
            Vehicle vehicle = new Vehicle(licensePlate, vehicleType);
            System.out.println("[Controller] Vehicle created: " + vehicle.getId());
            
            Optional<UUID> slotId = slotService.allocateSlot(vehicleType);

            if (slotId.isEmpty()) {
                return new EntryResult(false, "No available slot for vehicle type: " + vehicleType, null, null);
            }

            Optional<UUID> ticketId = ticketService.generateTicket(vehicle.getId(), slotId.get());

            if (ticketId.isEmpty()) {
                return new EntryResult(false, "Failed to generate ticket for vehicle: " + licensePlate, null, null);
            }

            return new EntryResult(true, "Vehicle entered successfully", ticketId.get(), slotId.get());
        } catch (Exception e) {
            return new EntryResult(false, "Error processing entry: " + e.getMessage(), null, null);
        }
    }



    public static class EntryResult {
        private final boolean success;
        private final String message;
        private final UUID ticketId;
        private final UUID slotId;

        public EntryResult(boolean success, String message, UUID ticketId, UUID slotId) {
            this.success = success;
            this.message = message;
            this.ticketId = ticketId;
            this.slotId = slotId;
        }

        public boolean isSuccess() {
            return success;
        }
        public String getMessage() {
            return message;
        }
        public UUID getTicketId() {
            return ticketId;
        }
        public UUID getSlotId() {
            return slotId;
        }
    }
}
