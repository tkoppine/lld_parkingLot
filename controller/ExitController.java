package controller;

import java.util.UUID;
import service.PricingService;
import service.TicketService;
import service.PaymentService;
import service.SlotService;
import service.ReceiptService;
import domain.Ticket;
import domain.Payment;
import domain.Receipt;

import java.util.Optional;

public class ExitController {
    private TicketService ticketService;
    private PricingService pricingService;
    private PaymentService paymentService;
    private PricingService pricingService;
    private SlotService slotService;
    private ReceiptService receiptService;

    public ExitController(TicketService ticketService, PricingService pricingService, PaymentService paymentService, SlotService slotService, ReceiptService receiptService) {
        this.ticketService = ticketService;
        this.pricingService = pricingService;
        this.paymentService = paymentService;
        this.slotService = slotService;
        this.receiptService = receiptService;
    }

    public ExitResult exitVehicle(UUID ticketId) {

        try {
            Optional<Ticket> ticketOpt = ticketService.findTicketById(ticketId);

            if (ticketOpt.isEmpty()) {
                return new ExitResult(false, null, 0, "Invalid ticket ID");
            }

            Ticket ticket = ticketOpt.get();
            double fee = pricingService.calculateFee(ticket);
            boolean paymentSuccess = paymentService.processPaymentWithRetry(ticketId, fee, 3);

            if (!paymentSuccess) {
                return new ExitResult(false, null, fee, "Payment failed after multiple attempts");
            }

            Receipt receipt = receiptService.generateReceipt(ticket, fee);
            receiptService.markReceiptAsPaid(receipt);

            slotService.releaseSlot(ticket.getSlotId());
            ticketService.deactivateTicket(ticketId);

            return new ExitResult(paymentSuccess, receipt.getId(), fee, "Exit Successful");
        }
        catch (Exception e) {
            return new ExitResult(false, null, 0, "Error processing exit: " + e.getMessage());
        }
    }

    public static class ExitResult {
        private final boolean success;
        private final String message;
        private final UUID receiptId;
        private final double fee;

        public ExitResult(boolean success, UUID receiptId, double fee, String message) {
            this.success = success;
            this.receiptId = receiptId;
            this.fee = fee;
            this.message = message;
        }
    }
}
