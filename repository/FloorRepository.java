package repository;

import domain.Floor;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class FloorRepository {
    private Map<UUID, Floor> floors = new ConcurrentHashMap<>();

    public void save(Floor floor) {
        // save the floor to the repository and return the saved floor
        floors.put(floor.getId(), floor);
        System.err.println("[Floor_Repository] Floor saved: " + floor.getFloorNumber());
    }

    public boolean floorExits(int floorNumber) {
        for (Floor floor : floors.values()) {
            if (floor.getFloorNumber() == floorNumber) {
                return true;
            }
        }
        return false;
    }

    public Floor findByNumber(int floorNumber) {
        for (Floor floor : floors.values()) {
            if (floor.getFloorNumber() == floorNumber) {
                return floor;
            }
        }
        return null;
    }
}
