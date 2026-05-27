package repository;

import domain.PricingRule;
import domain.Vehicle;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class PricingRuleRepository {
    private Map<Vehicle.VehicleType, PricingRule> pricingRules = new ConcurrentHashMap<>();

    public PricingRule save(PricingRule pricingRule) {
        // Save or update the pricing rule for the specified vehicle type
        pricingRules.put(pricingRule.getVehicleType(), pricingRule);
        System.out.println("[PricingRule_Repository] Saved pricing rule for " + pricingRule.getVehicleType());
        return pricingRule;
    }

    public Optional<PricingRule> findByVehicleType(Vehicle.VehicleType vehicleType) {
        // Retrieve the pricing rule for the specified vehicle type, if it exists
        return Optional.ofNullable(pricingRules.get(vehicleType));
    }

    public void deleteByVehicleType(Vehicle.VehicleType vehicleType) {
        // Remove the pricing rule for the specified vehicle type from the repository
        System.out.println("[PricingRule_Repository] Deleting pricing rule for " + vehicleType);
        pricingRules.remove(vehicleType);
    }

    public boolean existsByVehicleType(Vehicle.VehicleType vehicleType) {
        // Check if a pricing rule exists for the specified vehicle type in the
        // repository
        return pricingRules.containsKey(vehicleType);
    }

    public void updatePricingRule(PricingRule pricingRule) {
        // Update the existing pricing rule for the specified vehicle type. If the rule
        // does not exist, throw an exception.
        try {
            if (!pricingRules.containsKey(pricingRule.getVehicleType())) {
                throw new IllegalStateException("Pricing rule not found for " + pricingRule.getVehicleType());
            }
            // Update the pricing rule in the repository
            pricingRules.put(pricingRule.getVehicleType(), pricingRule);
            System.out.println("[PricingRule_Repository] Updated pricing rule for " + pricingRule.getVehicleType());
        } catch (Exception e) {
            System.out.println("[PricingRule_Repository] Error updating pricing rule: " + e.getMessage());
        }
    }
}
