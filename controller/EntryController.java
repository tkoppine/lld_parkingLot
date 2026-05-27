package controller;

import domain.Vehicle;
import java.util.Optional;
import java.util.UUID;
import service.SlotService;
import service.TicketService;

public class EntryController {
    private final SlotService slotService;
    private final TicketService ticketService;

    public EntryController(SlotService slotService, TicketService ticketService) {
        this.slotService = slotService;
        this.ticketService = ticketService;

        System.out.println("[Entry_Controller] initiated.");
    }

    public EntryResult enterVehicle(String licensePlate, Vehicle.VehicleType vehicleType) {
        try {
            // Create a vehicle instance
            Vehicle vehicle = new Vehicle(licensePlate, vehicleType);
            System.out.println("[Entry_Controller] Vehicle Created");

            // Allocate a parking slot for the vehicle
            Optional<UUID> slotId = slotService.allocateSlot(vehicleType);

            if (slotId.isEmpty()) {
                return new EntryResult(false, "No available slot for vehicle type: " + vehicleType, null, null);
            }

            // Generate a parking ticket for the vehicle
            Optional<UUID> ticketId = ticketService.generateTicket(vehicle.getId(), slotId.get(), vehicleType);

            if (ticketId.isEmpty()) {
                return new EntryResult(false, "Failed to generate ticket for vehicle: " + licensePlate, null, null);
            }

            System.out.println("[Entry_Controller] Vehicle Entered: " + licensePlate + ", Slot ID: " + slotId.get()
                    + ", Ticket ID: " + ticketId.get());
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
