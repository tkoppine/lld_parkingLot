package domain;
import java.time.LocalDateTime;
import java.util.UUID;

public class Receipt {
    private UUID id;
    private UUID ticketId;
    private LocalDateTime exitTime;
    private double totalFee;

    public Receipt(UUID ticketId, double totalFee) {
        this.id = UUID.randomUUID();
        this.exitTime = LocalDateTime.now();
        this.totalFee = totalFee;
        this.ticketId = ticketId;
    }

}