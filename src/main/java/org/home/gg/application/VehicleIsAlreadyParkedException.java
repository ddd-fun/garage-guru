package org.home.gg.application;


import org.home.gg.domain.garage.LotLocation;
import org.home.gg.domain.vehicle.VehicleId;

public class VehicleIsAlreadyParkedException extends RuntimeException {
    public VehicleIsAlreadyParkedException(VehicleId vehicleId, LotLocation location) {
       super(String.format("%s already parked on %s", vehicleId, location));
    }
}
