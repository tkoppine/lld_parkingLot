package domain;

import java.util.UUID;

public class Payment {
    private UUID id;
    private double amount;
    private UUID ticketId;
    private PaymentGateway paymentGateway;
    private PaymentStatus paymentStatus;

    public enum PaymentGateway {
        RAZORPAY, STRIPE
    }

    public enum PaymentStatus {
        SUCCESS, FAILED, PENDING
    }

    public Payment(UUID ticketId, double amount, PaymentGateway paymentGateway) {
        this.id = UUID.randomUUID();
        this.amount = amount;
        this.ticketId = ticketId;
        this.paymentGateway = paymentGateway;
        this.paymentStatus = PaymentStatus.PENDING;
    }

    public UUID getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public UUID getTicketId() {
        return ticketId;
    }

    public PaymentGateway getPaymentGateway() {
        return paymentGateway;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void markAsSuccess() {
        this.paymentStatus = PaymentStatus.SUCCESS;
    }

    public void markAsFailed() {
        this.paymentStatus = PaymentStatus.FAILED;
    }
}