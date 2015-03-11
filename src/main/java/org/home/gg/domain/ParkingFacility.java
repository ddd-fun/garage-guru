package org.home.gg.domain;


import java.util.Optional;

public interface ParkingFacility {

    AvailableLots getAvailableLots();

    Optional<ParkingLot> findSuitableLotFor(VehicleType vehicle);

    Optional<ParkingLot> findVehicle(VehicleId vehicleId);

    void save(ParkingLot parkingLot);

}
