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
    }

    public double calculateFee(Ticket ticket) {
        
        Vehicle.VehicleType vehicleType = ticket.getVehicleType();

        Optional<PricingRule> pricingRuleOpt = pricingRuleRepository.findByVehicleType(vehicleType);
        if (pricingRuleOpt.isEmpty()) {
            throw new RuntimeException("No pricing rule found for vehicle type: " + vehicleType);
        }

        PricingRule pricingRule = pricingRuleOpt.get();

        double faltFee = pricingRule.getFlatRate();
        double hourlyFee = calculateHourlyFee(ticket, pricingRule.getRatePerHour());


        double finalFee = Math.min(faltFee, hourlyFee);

        System.out.println("Calculated fee for ticket " + ticket.getId() + ": " + finalFee);
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
