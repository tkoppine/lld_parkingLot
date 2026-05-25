package domain;
import java.time.LocalDateTime;
import java.util.UUID;

public class Ticket {
    private final UUID id;
    private final UUID vehicleId;
    private final UUID slotId;
    private final LocalDateTime entryTime;

    public Ticket(UUID vehicleId, UUID slotId) {
        this.id = UUID.randomUUID();
        this.vehicleId = vehicleId;
        this.slotId = slotId;
        this.entryTime = LocalDateTime.now();
    }

    public UUID getId() {
        return id;
    }
}
