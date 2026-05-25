package repository;
import domain.PricingRule;
import domain.Vehicle;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class PricingRuleRepository {
    private Map<Vehicle.VehicleType, PricingRule> pricingRulesByVehicleType = new ConcurrentHashMap<>();


    public PricingRule save(PricingRule pricingRule) {
        pricingRulesByVehicleType.put(pricingRule.getVehicleType(), pricingRule);
        return pricingRule;
    }

    public Optional<PricingRule> findByVehicleType(Vehicle.VehicleType vehicleType) {
        return Optional.ofNullable(pricingRulesByVehicleType.get(vehicleType));
    }
}
