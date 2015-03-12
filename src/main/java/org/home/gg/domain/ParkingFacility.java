package org.home.gg.domain;


import java.util.Optional;

public interface ParkingFacility {

    AvailableLots getAvailableLots();

    Optional<ParkingLot> findSuitableLotFor(VehicleType vehicle);

    Optional<ParkingLot> findParkedVehicle(VehicleId vehicleId);

    void save(ParkingLot parkingLot);

}
