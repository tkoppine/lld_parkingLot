
package domain;

import java.util.UUID;

public class ParkingSlot {
    private UUID id;
    private int floorNumber;
    private Vehicle.VehicleType slotType;
    private boolean isOccupied;

    public ParkingSlot(Vehicle.VehicleType slotType, int floorNumber) {
        this.id = UUID.randomUUID();
        this.floorNumber = floorNumber;
        this.slotType = slotType;
        this.isOccupied = false;
    }

    public UUID getId() {
        return id;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public Vehicle.VehicleType getSlotType() {
        return slotType;
    }
}