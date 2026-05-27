package adapter;

import domain.Payment;
import java.util.UUID;

public interface PaymentGatewayAdapter {

    boolean pay(UUID ticketId, double amount);

    Payment.PaymentGateway getGatewayType();
}
