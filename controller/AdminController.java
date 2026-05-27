package controller;

import domain.Vehicle;
import service.AdminService;

public class AdminController {
    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
        System.out.println("[Admin_Controller] initialized.");
    }

    public void initializeParkingLot() {
        // This method can be used to set up the initial state of the parking lot, such
        // as adding default floors and slots.
        System.out.println("[Admin_Controller] Initializing parking lot...");
        adminService.initializeParkingLot();
    }

    public void addFloor(int floorNumber) {
        // This method allows the admin to add a new floor to the parking lot.
        System.out.println("[Admin_Controller] Adding floor: " + floorNumber);
        adminService.addFloor(floorNumber);
    }

    public void addSlotsToFloor(int floorNumber, Vehicle.VehicleType vehicleType, int count) {
        // This method allows the admin to add a specific number of slots for a given
        // vehicle type to a specific floor.
        System.out
                .println("[Admin_Controller] Adding " + count + " " + vehicleType + " slots to floor: " + floorNumber);
        adminService.addSlotsToFloor(floorNumber, vehicleType, count);
    }

    public void updatePricingRule(Vehicle.VehicleType vehicleType, double newHourlyRate, double newFlatRate) {
        // This method allows the admin to update the pricing rules for a specific
        // vehicle type, including both hourly and flat rates.
        System.out.println("[Admin_Controller] Updating pricing rule for " + vehicleType + ": Hourly Rate = "
                + newHourlyRate + ", Flat Rate = " + newFlatRate);
        adminService.updatePricingRule(vehicleType, newHourlyRate, newFlatRate);
    }

    public void updateHourlyRate(Vehicle.VehicleType vehicleType, double newHourlyRate) {
        // This method allows the admin to update only the hourly rate for a specific
        // vehicle type.
        System.out.println(
                "[Admin_Controller] Updating hourly rate for " + vehicleType + ": New Hourly Rate = " + newHourlyRate);
        adminService.updateHourlyPricing(vehicleType, newHourlyRate);
    }

    public void updateFlatRate(Vehicle.VehicleType vehicleType, double newFlatRate) {
        // This method allows the admin to update only the flat rate for a specific
        // vehicle type.
        System.out.println(
                "[Admin_Controller] Updating flat rate for " + vehicleType + ": New Flat Rate = " + newFlatRate);
        adminService.updateFlatPricing(vehicleType, newFlatRate);
    }

    public void addPricingRule(Vehicle.VehicleType vehicleType, double ratePerHour, double flatRate) {
        // This method allows the admin to add a new pricing rule for a specific vehicle
        // type, including both hourly and flat rates.
        System.out.println("[Admin_Controller] Adding pricing rule for " + vehicleType + ": Hourly Rate = "
                + ratePerHour + ", Flat Rate = " + flatRate);
        adminService.addPricingRule(vehicleType, ratePerHour, flatRate);
    }
}
