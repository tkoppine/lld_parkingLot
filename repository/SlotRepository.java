
package repository;

import domain.ParkingSlot;
import domain.Vehicle;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SlotRepository {
    private Map<UUID, ParkingSlot> slots = new ConcurrentHashMap<>();

    public ParkingSlot save(ParkingSlot slot) {
        // In a real implementation, this would involve database operations
        slots.put(slot.getId(), slot);
        System.out.println("[Slot_Repository] has saved slot: " + slot.getId());
        return slot;
    }

    public Optional<ParkingSlot> allocateSlot(Vehicle.VehicleType vehicleType) {
        // Find an available slot for the given vehicle type
        for (ParkingSlot slot : slots.values()) {
            if (!slot.isOcuupied() && slot.getSlotType() == vehicleType) {
                slot.setOccupied(true);
                System.out.println(
                        "[Slot_Repository] allocated slot: " + slot.getId() + " for vehicle type: " + vehicleType);
                return Optional.of(slot);
            }
        }
        System.out.println("[Slot_Repository] no available slot for vehicle type: " + vehicleType);
        return Optional.empty();
    }

    public void releaseSlot(UUID slotId) {
        // Release the slot with the given ID
        ParkingSlot slot = slots.get(slotId);

        if (slot != null) {
            // Mark the slot as available
            System.out.println("[Slot_Repository] releasing slot: " + slotId);
            slot.setOccupied(false);
        }
    }
}
