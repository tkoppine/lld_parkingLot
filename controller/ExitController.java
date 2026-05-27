package controller;

import domain.Receipt;
import domain.Ticket;
import java.util.Optional;
import java.util.UUID;
import service.PaymentService;
import service.PricingService;
import service.ReceiptService;
import service.SlotService;
import service.TicketService;

public class ExitController {
    private TicketService ticketService;
    private PricingService pricingService;
    private PaymentService paymentService;
    private SlotService slotService;
    private ReceiptService receiptService;

    public ExitController(TicketService ticketService, PricingService pricingService, PaymentService paymentService,
            SlotService slotService, ReceiptService receiptService) {
        this.ticketService = ticketService;
        this.pricingService = pricingService;
        this.paymentService = paymentService;
        this.slotService = slotService;
        this.receiptService = receiptService;

        System.out.println("[Exit_Controller] initialized.");
    }

    public ExitResult exitVehicle(UUID ticketId) {

        try {
            // Validate the ticket ID and retrieve the corresponding ticket information
            Optional<Ticket> ticketOpt = ticketService.findTicketById(ticketId);
            System.out.println("[Exit_Controller] Processing exit for ticket ID: " + ticketId);

            if (ticketOpt.isEmpty()) {
                return new ExitResult(false, null, 0, "Invalid ticket ID");
            }

            Ticket ticket = ticketOpt.get();
            // Calculate the parking fee based on the ticket information and the applicable
            // pricing rules
            double fee = pricingService.calculateFee(ticket);
            System.out.println("[Exit_Controller] Calculated fee for ticket ID " + ticketId + ": " + fee);

            // Process the payment for the calculated fee, implementing retry logic for
            // transient failures
            boolean paymentSuccess = paymentService.processPaymentWithRetry(ticketId, fee, 3);
            System.out.println(
                    "[Exit_Controller] Payment processing result for ticket ID " + ticketId + ": " + paymentSuccess);

            if (!paymentSuccess) {
                return new ExitResult(false, null, fee, "Payment failed after multiple attempts");
            }

            // Generate a receipt for the transaction and mark it as paid
            Receipt receipt = receiptService.generateReceipt(ticket, fee);
            System.out.println("[Exit_Controller] Generated receipt for ticket ID " + ticketId + ": Receipt ID "
                    + receipt.getId());
            // Mark the receipt as paid in the system
            receiptService.markReceiptAsPaid(receipt);
            System.out.println("[Exit_Controller] Marked receipt as paid for ticket ID " + ticketId + ": Receipt ID "
                    + receipt.getId());

            // Release the parking slot associated with the ticket and deactivate the ticket
            // to prevent reuse
            slotService.releaseSlot(ticket.getSlotId());
            System.out.println(
                    "[Exit_Controller] Released slot for ticket ID " + ticketId + ": Slot ID " + ticket.getSlotId());
            // Deactivate the ticket to prevent reuse
            ticketService.deactivateTicket(ticketId);
            System.out.println("[Exit_Controller] Deactivated ticket ID: " + ticketId);

            return new ExitResult(paymentSuccess, receipt.getId(), fee, "Exit Successful");
        } catch (Exception e) {
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
