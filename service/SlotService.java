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
        System.out.println("SlotService initialized with SlotRepository.");
    }

    public Optional<UUID> allocateSlot(Vehicle.VehicleType vehicleType) {
        Optional<ParkingSlot> slot = slotRepository.allocateSlot(vehicleType);
        if (slot.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(slot.get().getId());
    }
}
