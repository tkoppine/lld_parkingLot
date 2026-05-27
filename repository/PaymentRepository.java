package repository;

import domain.Payment;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PaymentRepository {
    private Map<UUID, Payment> payments = new ConcurrentHashMap<>();
    private Map<UUID, List<UUID>> ticketToPayments = new ConcurrentHashMap<>();

    public Payment save(Payment payment) {
        // Save the payment record and associate it with the ticket ID
        payments.put(payment.getId(), payment);
        System.out.println("[Payment_Repository] Saved payment record with ID: " + payment.getId() + " for Ticket ID: "
                + payment.getTicketId() + " with status: " + payment.getPaymentStatus());
        // Associate payment ID with the ticket ID for easy retrieval
        ticketToPayments.computeIfAbsent(payment.getTicketId(), k -> new ArrayList<>()).add(payment.getId());
        System.out.println("[Payment_Repository] Associated payment ID: " + payment.getId() + " with Ticket ID: "
                + payment.getTicketId());
        return payment;
    }

    public Payment findById(UUID paymentId) {
        // Retrieve payment by its ID
        return payments.get(paymentId);
    }

    public void update(Payment payment) {
        // Update the payment record with the new status
        if (payments.containsKey(payment.getId())) {
            // Update the payment record with the new status
            payments.put(payment.getId(), payment);
            System.out.println("[Payment_Repository] Updated payment record with ID: " + payment.getId()
                    + " to status: " + payment.getPaymentStatus() + " for Ticket ID: " + payment.getTicketId());
        } else {
            System.out.println(
                    "[Payment_Repository] Attempted to update non-existing payment record with ID: " + payment.getId());
        }
    }
}
