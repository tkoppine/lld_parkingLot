package service;

import domain.PricingRule;
import domain.Ticket;
import domain.Vehicle;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import repository.PricingRuleRepository;

public class PricingService {
    PricingRuleRepository pricingRuleRepository;

    public PricingService(PricingRuleRepository pricingRuleRepository) {
        this.pricingRuleRepository = pricingRuleRepository;
        System.out.println("[Pricing_Service] initialized");
    }

    public double calculateFee(Ticket ticket) {
        // Fetch the pricing rule based on the vehicle type
        Vehicle.VehicleType vehicleType = ticket.getVehicleType();
        System.out.println("[Pricing_Service] Calculating fee for vehicle type: " + vehicleType);

        Optional<PricingRule> rule = pricingRuleRepository.findByVehicleType(vehicleType);
        if (rule.isEmpty()) {
            System.out.println("[Pricing_Service] No pricing rule found for vehicle type: " + vehicleType);
            throw new RuntimeException("No pricing rule found for vehicle type: " + vehicleType);
        }
        // Calculate fee based on the pricing rule
        PricingRule pricingRule = rule.get();
        // For simplicity, we will use a flat fee or hourly fee based on the rule
        double flatFee = pricingRule.getFlatRate();
        // Calculate hourly fee based on the duration of parking
        double hourlyFee = calculateHourlyFee(ticket, pricingRule.getRatePerHour());
        // For this example, we will charge the customer the lesser of the flat fee or
        // the hourly fee
        double finalFee = Math.min(flatFee, hourlyFee);
        System.out.println("[Pricing_Service] Flat fee: " + flatFee + ", Hourly fee: " + hourlyFee);
        System.out.println("[Pricing_Service] Final fee calculated: " + finalFee);
        return finalFee; // Placeholder return value
    }

    private double calculateHourlyFee(Ticket ticket, double ratePerHour) {
        Duration duration = Duration.between(ticket.getEntryTime(), LocalDateTime.now());
        long hours = duration.toHours();

        if (hours < 1) {
            hours = 1; // Minimum charge for 1 hour
        }

        return hours * ratePerHour; // Placeholder return value
    }
}
