
package domain;
import java.util.UUID;

public class ParkingSlot {
    private UUID id;
    private int floorNumber;
    private Vehicle.VehicleType slotType;
    private boolean isOcuupied;

    public ParkingSlot(Vehicle.VehicleType slotType, int floorNumber) {
        this.id = UUID.randomUUID();
        this.floorNumber = floorNumber;
        this.slotType = slotType;
        this.isOcuupied = false;
    }

    public UUID getId() {
        return id;
    }

    public boolean isOcuupied() {
        return isOcuupied;
    }

    public void setOccupied(boolean occupied) {
        isOcuupied = occupied;
    }

    public Vehicle.VehicleType getSlotType() {
        return slotType;
    }
}