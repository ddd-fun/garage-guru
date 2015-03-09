package org.home.gg;


public class ParkingTicket {

    private final VehicleId vehicleId;
    private final SpotLocation spotLocation;

    public ParkingTicket(VehicleId vehicleId, SpotLocation spotLocation) {
        this.vehicleId = vehicleId;
        this.spotLocation = spotLocation;
    }

    public VehicleId getVehicleId() {
        return vehicleId;
    }

    public SpotLocation getSpotLocation() {
        return spotLocation;
    }

    @Override
    public String toString() {
        return String.format("Parking ticket: %s, location=%s", vehicleId, spotLocation);
    }
}
