package service;

import domain.ParkingSlot;
import domain.Vehicle;
import java.util.Optional;
import java.util.UUID;
import repository.SlotRepository;

public class SlotService {
    private SlotRepository slotRepository;

    public SlotService(SlotRepository slotRepository) {
        this.slotRepository = slotRepository;
        System.out.println("[Slot_Service] initialized.");
    }

    public Optional<UUID> allocateSlot(Vehicle.VehicleType vehicleType) {
        // Allocate a parking slot based on the vehicle type
        Optional<ParkingSlot> slot = slotRepository.allocateSlot(vehicleType);
        System.out.println("[Slot_Service] allocated slot for vehicle type: " + vehicleType);

        return slot.map(ParkingSlot::getId);
    }

    public void releaseSlot(UUID slotId) {
        // Release the parking slot with the given ID
        slotRepository.releaseSlot(slotId);
        System.out.println("[Slot_Service] released slot with ID: " + slotId);
    }
}
