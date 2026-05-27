package service;

import domain.Receipt;
import domain.Ticket;

public class ReceiptService {
    public Receipt generateReceipt(Ticket ticket, double fee) {
        // Generate receipt logic
        Receipt receipt = new Receipt(ticket.getId(), fee);

        return receipt;
    }

    public void markReceiptAsPaid(Receipt receipt) {
        // Mark receipt as paid logic
        receipt.markAsPaid();
    }

    public String generateReceiptText(Receipt receipt, Ticket ticket) {
        System.out.println("[SERVICE] Generating receipt text for: " + receipt.getId());

        StringBuilder receiptText = new StringBuilder();
        receiptText.append("📄 Receipt:\n");
        receiptText.append("=== PARKING RECEIPT ===\n");
        receiptText.append("Receipt ID: ").append(receipt.getId()).append("\n");
        receiptText.append("Ticket ID: ").append(ticket.getId()).append("\n");
        receiptText.append("Vehicle: DUMMY-").append(ticket.getId()).append(" (CAR)\n");
        receiptText.append("Entry Time: ").append(ticket.getEntryTime()).append("\n");
        receiptText.append("Exit Time: ").append(receipt.getExitTime()).append("\n");
        receiptText.append("Total Fee: $").append(String.format("%.2f", receipt.getTotalFee())).append("\n");
        receiptText.append("Payment Status: ").append(receipt.getPaymentStatus()).append("\n");
        receiptText.append("=====================\n");

        System.out.println("[SERVICE] Receipt text generated successfully");
        return receiptText.toString();
    }
}
