package org.home.gg.application;


import org.home.gg.domain.LotLocation;
import org.home.gg.domain.VehicleId;

public class VehicleIsAlreadyParkedException extends RuntimeException {
    public VehicleIsAlreadyParkedException(VehicleId vehicleId, LotLocation location) {
       super(String.format("%s already parked on %s", vehicleId, location));
    }
}
