
package repository;
import domain.ParkingSlot;
import domain.Vehicle;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SlotRepository {
    private Map<UUID, ParkingSlot> slots = new ConcurrentHashMap<>();

    public Optional<ParkingSlot> allocateSlot(Vehicle.VehicleType vehicleType) {
        for (ParkingSlot slot : slots.values()) {
            if (!slot.isOcuupied() && slot.getSlotType() == vehicleType) {
                slot.setOccupied(true);
                return Optional.of(slot);
            }
        }
        return Optional.empty();
    }
}
