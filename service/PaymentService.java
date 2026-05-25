package service;

import adapter.PaymentGatewayAdapter;
import adapter.RazorPayAdapter;
import adapter.StripeAdapter;
import domain.Payment.PaymentGateway;
import repository.PaymentRepository;
import domain.Payment;
import adapter.RazorPayAdapter;
import adapter.StripeAdapter;
import java.util.UUID;

public class PaymentService {
    private PaymentRepository paymentRepository;
    private PaymentGatewayAdapter defaultGateway;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
        this.defaultGateway = new RazorPayAdapter();
    }

    public boolean processPayment(UUID ticketId, double amount) {
        
        Payment payment = new Payment(ticketId, amount, PaymentGateway.RAZORPAY);
        paymentRepository.save(payment);

        boolean success = defaultGateway.pay(ticketId, amount);

        if (success) {
            payment.markAsScuccess();
        } else {
            payment.markAsFailed();
        }

        paymentRepository.update(payment);
        return success;
    }

    public boolean processPaymentWithRetry(UUID ticketId, double amount, int maxRetries) {
       for (int attempt = 1; attempt <= maxRetries; attempt++) {
            boolean success = processPayment(ticketId, amount);
            if (success) {
                return true;
            }

            if (attempt > 1) {
                defaultGateway = new StripeAdapter(); // Switch to a different gateway for retry (for demonstration)
            }
            System.out.println("Retrying payment... Attempt " + attempt);
        }
        return false;
    }

    public void setPaymentGatewayAdapter(PaymentGatewayAdapter adapter) {
        this.defaultGateway = adapter;
    }
}
