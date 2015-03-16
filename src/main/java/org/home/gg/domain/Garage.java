package org.home.gg.domain;


import java.util.Optional;

public interface Garage {

    AvailableLots getAvailableLots();

    Optional<ParkingLot> findSuitableLotFor(VehicleType vehicle);

    Optional<ParkingLot> findParkedVehicle(VehicleId vehicleId);

}
