
package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Floor {
    private UUID floorId;
    private int floorNumber;
    private List<ParkingSlot> slots;

    public Floor(int floorNumber) {
        this.floorId = UUID.randomUUID();
        this.floorNumber = floorNumber;
        this.slots = new ArrayList<>();
    }

    public UUID getId() {
        return floorId;
    }

    public void addSlot(ParkingSlot slot) {
        slots.add(slot);
    }

    public int getFloorNumber() {
        return floorNumber;
    }
}
