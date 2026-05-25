package adapter;

import java.util.Random;
import java.util.UUID;

public class StripeAdapter implements PaymentGatewayAdapter {

    @Override
    public boolean pay(UUID ticketId, double amount) {
        // Simulate payment processing with Stripe
        System.out.println("Processing payment with Stripe for Ticket ID: " + ticketId + " Amount: $" + amount);
        
        Random random = new Random();
        boolean paymentSuccess = random.nextDouble() < 0.9; // 90%
        System.out.println("Payment " + (paymentSuccess ? "successful" : "failed") + " for Ticket ID: " + ticketId);
        return paymentSuccess;
    }

}
