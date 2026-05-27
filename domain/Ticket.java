package domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Ticket {
    private final UUID id;
    private final UUID vehicleId;
    private final Vehicle.VehicleType vehicleType;
    private final UUID slotId;
    private final LocalDateTime entryTime;

    public Ticket(UUID vehicleId, UUID slotId, Vehicle.VehicleType vehicleType) {
        this.id = UUID.randomUUID();
        this.vehicleId = vehicleId;
        this.slotId = slotId;
        this.entryTime = LocalDateTime.now();
        this.vehicleType = vehicleType;
    }

    public UUID getId() {
        return id;
    }

    public UUID getVehicleId() {
        return vehicleId;
    }

    public UUID getSlotId() {
        return slotId;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public Vehicle.VehicleType getVehicleType() {
        return vehicleType;
    }
}
