package service;

import adapter.PaymentGatewayAdapter;
import adapter.RazorPayAdapter;
import adapter.StripeAdapter;
import domain.Payment;
import java.util.UUID;
import repository.PaymentRepository;

public class PaymentService {
    private PaymentRepository paymentRepository;
    private PaymentGatewayAdapter defaultGateway;

    public PaymentService(PaymentRepository paymentRepository, PaymentGatewayAdapter defaultGateway) {
        this.paymentRepository = paymentRepository;
        this.defaultGateway = defaultGateway;
        System.out.println("[Payment_Service] initialized with default gateway: " + defaultGateway.getGatewayType());
    }

    public boolean processPayment(UUID ticketId, double amount) {
        // Create a new payment record with the default gateway
        Payment payment = new Payment(ticketId, amount, defaultGateway.getGatewayType());
        System.out.println("[Payment_Service] Created payment record with ID: " + payment.getId() + " for Ticket ID: "
                + ticketId + " using gateway: " + defaultGateway.getGatewayType());

        // Save the payment record before processing
        paymentRepository.save(payment);
        System.out.println(
                "[Payment_Service] Saved payment record with ID: " + payment.getId() + " for Ticket ID: " + ticketId);

        boolean success = defaultGateway.pay(ticketId, amount);

        if (success) {
            payment.markAsScuccess();
        } else {
            payment.markAsFailed();
        }

        // Update the payment record with the new status
        paymentRepository.update(payment);
        System.out.println("[Payment_Service] Updated payment record with ID: " + payment.getId() + " to status: "
                + payment.getPaymentStatus() + " for Ticket ID: " + ticketId);
        return success;
    }

    public boolean processPaymentWithRetry(UUID ticketId, double amount, int maxRetries) {
        // Attempt payment with retries
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            System.out
                    .println("[Payment_Service] Attempt " + attempt + " to process payment for Ticket ID: " + ticketId);
            boolean success = processPayment(ticketId, amount);
            if (success) {
                return true;
            }
            changePaymentGateway();
            System.out.println("[Payment_Service] Payment attempt " + attempt + " failed for Ticket ID: " + ticketId);
        }
        return false;
    }

    private void changePaymentGateway() {
        if (defaultGateway instanceof StripeAdapter) {
            defaultGateway = new RazorPayAdapter(); // Switch to RazorPay
            System.out.println("[Payment_Service] Switched payment gateway to RAZORPAY");
        } else {
            defaultGateway = new StripeAdapter(); // Switch to Stripe
            System.out.println("[Payment_Service] Switched payment gateway to STRIPE");
        }
    }

    public void setPaymentGatewayAdapter(PaymentGatewayAdapter adapter) {
        this.defaultGateway = adapter;
    }
}
