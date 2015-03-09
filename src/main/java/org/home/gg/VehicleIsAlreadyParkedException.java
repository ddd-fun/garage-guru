package org.home.gg;


public class VehicleIsAlreadyParkedException extends RuntimeException {
    public VehicleIsAlreadyParkedException(VehicleId vehicleId, LotLocation location) {
       super(String.format("%s already parked on %s", vehicleId, location));
    }
}
