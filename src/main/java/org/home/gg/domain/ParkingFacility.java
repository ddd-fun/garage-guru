package org.home.gg.domain;


import java.util.Optional;

public interface ParkingFacility {

    AvailableLots getAvailableLots();

    Optional<ParkingLot> findSuitableLotFor(ParkingLotSpec parkingLotSpec);

    Optional<ParkingLot> findVehicle(VehicleId vehicleId);

    void save(ParkingLot parkingLot);

}
