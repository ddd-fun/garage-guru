package org.home.gg.domain.garage;


import org.home.gg.domain.vehicle.VehicleId;
import org.home.gg.domain.vehicle.VehicleType;

import java.util.Optional;

public interface Garage {

    NumberOfFreeLots getNumberOfFreeLots();

    Optional<ParkingLot> findSuitableLotFor(VehicleType vehicle);

    Optional<ParkingLot> findParkedVehicle(VehicleId vehicleId);

}
