package service;

import domain.Floor;
import domain.ParkingSlot;
import domain.PricingRule;
import domain.Vehicle;
import java.util.Optional;
import repository.FloorRepository;
import repository.PricingRuleRepository;
import repository.SlotRepository;

public class AdminService {
    private FloorRepository floorRepository;
    private SlotRepository slotRepository;
    private PricingRuleRepository pricingRuleRepository;

    public AdminService(FloorRepository floorRepository, SlotRepository slotRepository,
            PricingRuleRepository pricingRuleRepository) {
        this.floorRepository = floorRepository;
        this.slotRepository = slotRepository;
        this.pricingRuleRepository = pricingRuleRepository;

        System.out.println("[Admin_Service] initialized.");
    }

    public void initializeParkingLot() {
        // This method sets up the initial state of the parking lot by adding default
        // floors, slots, and pricing rules.
        for (int i = 0; i < 3; i++) {
            // Add 3 floors to the parking lot
            addFloor(i);
        }

        // Add slots to floor 0
        addSlotsToFloor(0, Vehicle.VehicleType.BIKE, 20);
        addSlotsToFloor(0, Vehicle.VehicleType.CAR, 30);
        addSlotsToFloor(0, Vehicle.VehicleType.TRUCK, 5);

        // Add slots to floor 1
        addSlotsToFloor(1, Vehicle.VehicleType.CAR, 40);
        addSlotsToFloor(1, Vehicle.VehicleType.EV, 10);

        // Add slots to floor 2
        addSlotsToFloor(2, Vehicle.VehicleType.CAR, 35);
        addSlotsToFloor(2, Vehicle.VehicleType.EV, 15);

        // Initialize default pricing rules
        initializeDefaultPricingRules();
    }

    public void addFloor(int floorNumber) {
        boolean isFloorExists = floorRepository.floorExits(floorNumber);

        if (isFloorExists) {
            System.out.println("[Admin_Service] Floor " + floorNumber + " already exists. Skipping addition.");
            return;
        }

        Floor floor = new Floor(floorNumber);
        floorRepository.save(floor);
    }

    public void addSlotsToFloor(int floorNumber, Vehicle.VehicleType vehicleType, int count) {
        // Validate that the floor exists before adding slots
        Floor floor = floorRepository.findByNumber(floorNumber);
        System.out.println("[Admin_Service] Adding " + count + " " + vehicleType + " slots to floor " + floorNumber);

        if (floor == null) {
            // If the floor does not exist, throw an exception or handle it as needed
            throw new IllegalArgumentException("Floor not found: " + floorNumber);
        }

        for (int i = 0; i < count; i++) {
            // Create a new parking slot for the specified vehicle type and floor
            ParkingSlot slot = new ParkingSlot(vehicleType, floorNumber);
            // Save the slot to the repository and associate it with the floor
            slotRepository.save(slot);
            System.out.println(
                    "[Admin_Service] Added slot " + slot.getId() + " for " + vehicleType + " on floor " + floorNumber);
            // Add the slot to the floor's list of slots
            floor.addSlot(slot);
            System.out.println("[Admin_Service] Slot " + slot.getId() + " added to floor " + floorNumber);
        }
    }

    public void initializeDefaultPricingRules() {
        System.out.println("[Admin_Service] Initializing default pricing rules");

        PricingRule bikeRule = new PricingRule(Vehicle.VehicleType.BIKE, 10.0, 30.0);
        PricingRule carRule = new PricingRule(Vehicle.VehicleType.CAR, 20.0, 60.0);
        PricingRule truckRule = new PricingRule(Vehicle.VehicleType.TRUCK, 30.0, 90.0);
        PricingRule evRule = new PricingRule(Vehicle.VehicleType.EV, 15.0, 45.0);

        pricingRuleRepository.save(bikeRule);
        pricingRuleRepository.save(carRule);
        pricingRuleRepository.save(truckRule);
        pricingRuleRepository.save(evRule);
    }

    public void addFloorPublic(int floorNumber) {
        // This method is a public wrapper for the addFloor method, allowing external
        // callers to add floors to the parking lot.
        addFloor(floorNumber);
        System.out.println("[Admin_Service] Floor " + floorNumber + " added successfully.");
    }

    public void addSlotsToFloorPublic(int floorNumber, Vehicle.VehicleType slotType, int count) {
        // This method is a public wrapper for the addSlotsToFloor method, allowing
        // external callers to add slots to a specific floor in the parking lot.
        addSlotsToFloor(floorNumber, slotType, count);
        System.out.println("[Admin_Service] Added " + count + " " + slotType + " slots to floor " + floorNumber
                + " successfully.");
    }

    public void updatePricingRule(Vehicle.VehicleType vehicleType, double ratePerHour, double flatRate) {
        System.out.println("[Admin_Service] Updating pricing rule for " + vehicleType);

        try {
            Optional<PricingRule> rule = pricingRuleRepository.findByVehicleType(vehicleType);

            if (rule.isEmpty()) {
                System.out.println("[Admin_Service] Pricing rule not found for " + vehicleType);
                throw new IllegalArgumentException("Pricing rule not found for " + vehicleType);
            }

            PricingRule pricingRule = rule.get();
            // Update both hourly and flat rates for the specified vehicle type
            pricingRule.updateRates(ratePerHour, flatRate);
            System.out.println("[Admin_Service] Updated hourly rate to " + ratePerHour + " and flat rate to " + flatRate
                    + " for " + vehicleType);
            // Save the updated pricing rule back to the repository
            pricingRuleRepository.updatePricingRule(pricingRule);
            System.out.println("[Admin_Service] Pricing rule updated successfully for " + vehicleType);
        } catch (Exception e) {
            System.out.println("[Admin_Service] Error updating pricing rule: " + e.getMessage());
        }
    }

    public void updateFlatPricing(Vehicle.VehicleType vehicleType, double flatRate) {
        System.out.println("[Admin_Service] Updating flat pricing for " + vehicleType + " to " + flatRate);

        try {
            Optional<PricingRule> rule = pricingRuleRepository.findByVehicleType(vehicleType);

            if (rule.isEmpty()) {
                System.out.println("[Admin_Service] Pricing rule not found for " + vehicleType);
                throw new IllegalArgumentException("Pricing rule not found for " + vehicleType);
            }

            PricingRule pricingRule = rule.get();
            // Update only the flat rate for the specified vehicle type
            pricingRule.updateFlatRate(flatRate);
            System.out.println("[Admin_Service] Updated flat rate to " + flatRate + " for " + vehicleType);
            // Save the updated pricing rule back to the repository
            pricingRuleRepository.updatePricingRule(pricingRule);
            System.out.println("[Admin_Service] Flat pricing updated successfully for " + vehicleType);
        } catch (Exception e) {
            System.out.println("[Admin_Service] Error updating flat pricing: " + e.getMessage());
        }
    }

    public void updateHourlyPricing(Vehicle.VehicleType vehicleType, double ratePerHour) {
        System.out.println("[Admin_Service] Updating hourly pricing for " + vehicleType + " to " + ratePerHour);

        try {
            Optional<PricingRule> rule = pricingRuleRepository.findByVehicleType(vehicleType);

            if (rule.isEmpty()) {
                System.out.println("[Admin_Service] Pricing rule not found for " + vehicleType);
                throw new IllegalArgumentException("Pricing rule not found for " + vehicleType);
            }

            PricingRule pricingRule = rule.get();
            // Update only the hourly rate for the specified vehicle type
            pricingRule.updateHourlyRate(ratePerHour);
            System.out.println("[Admin_Service] Updated hourly rate to " + ratePerHour + " for " + vehicleType);
            // Save the updated pricing rule back to the repository
            pricingRuleRepository.updatePricingRule(pricingRule);
            System.out.println("[Admin_Service] Hourly pricing updated successfully for " + vehicleType);
        } catch (Exception e) {
            System.out.println("[Admin_Service] Error updating hourly pricing: " + e.getMessage());
        }
    }

    public void addPricingRule(Vehicle.VehicleType vehicleType, double ratePerHour, double flatRate) {
        PricingRule newRule = new PricingRule(vehicleType, ratePerHour, flatRate);
        // Save the new pricing rule to the repository
        pricingRuleRepository.save(newRule);
        System.out.println("[Admin_Service] Added new pricing rule for " + vehicleType + ": Hourly Rate = "
                + ratePerHour + ", Flat Rate = " + flatRate);
    }
}
