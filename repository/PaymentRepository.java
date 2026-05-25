package repository;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import domain.Payment;

public class PaymentRepository {
    private Map<UUID, Payment> payments = new ConcurrentHashMap<>();
    private Map<UUID, List<UUID>> ticketToPayments = new ConcurrentHashMap<>();

    public Payment save(Payment payment) {
        payments.put(payment.getId(), payment);
        ticketToPayments.computeIfAbsent(payment.getTicketId(), k -> new ArrayList<>()).add(payment.getId());
        return payment;
    }

    public Payment findById(UUID paymentId) {
        return payments.get(paymentId);
    }

    public void update(Payment payment) {
        if (payments.containsKey(payment.getId())) {
            payments.put(payment.getId(), payment);
        }
    }
}
