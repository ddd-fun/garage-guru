package org.home.gg;


public class ParkingTicket {

    private final VehicleId vehicleId;
    private final LotLocation lotLocation;

    public ParkingTicket(VehicleId vehicleId, LotLocation lotLocation) {
        this.vehicleId = vehicleId;
        this.lotLocation = lotLocation;
    }

    public VehicleId getVehicleId() {
        return vehicleId;
    }

    public LotLocation getLotLocation() {
        return lotLocation;
    }

    @Override
    public String toString() {
        return String.format("Parking ticket for vehicle %s. Location: %s", vehicleId, lotLocation);
    }
}
