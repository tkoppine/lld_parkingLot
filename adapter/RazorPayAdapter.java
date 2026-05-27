package adapter;

import domain.Payment;
import java.util.Random;
import java.util.UUID;

public class RazorPayAdapter implements PaymentGatewayAdapter {

    @Override
    public boolean pay(UUID ticketId, double amount) {
        System.out
                .println("Processing payment through RazorPay for Ticket ID: " + ticketId + " with amount: " + amount);

        Random random = new Random();
        boolean paymentSuccess = random.nextDouble() < 0.9; // 90% success rate

        System.out.println("Payment " + (paymentSuccess ? "successful" : "failed") + " for Ticket ID: " + ticketId);
        return paymentSuccess; // Assume payment is successful for this example
    }

    @Override
    public Payment.PaymentGateway getGatewayType() {
        return Payment.PaymentGateway.RAZORPAY;
    }
}
