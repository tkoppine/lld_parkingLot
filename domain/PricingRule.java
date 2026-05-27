package domain;

import java.util.UUID;

public class PricingRule {
    private UUID id;
    private Vehicle.VehicleType vehicleType;
    private double ratePerHour;
    private double flatRate;

    public PricingRule(Vehicle.VehicleType vehicleType, double ratePerHour, double flatRate) {
        this.id = UUID.randomUUID();
        this.vehicleType = vehicleType;
        this.ratePerHour = ratePerHour;
        this.flatRate = flatRate;
    }

    public UUID getId() {
        return id;
    }

    public Vehicle.VehicleType getVehicleType() {
        return vehicleType;
    }

    public double getRatePerHour() {
        return ratePerHour;
    }

    public double getFlatRate() {
        return flatRate;
    }

    public void updateHourlyRate(double newRate) {
        this.ratePerHour = newRate;
    }

    public void updateFlatRate(double newFlatRate) {
        this.flatRate = newFlatRate;
    }

    public void updateRates(double newRatePerHour, double newFlatRate) {
        this.ratePerHour = newRatePerHour;
        this.flatRate = newFlatRate;
    }
}