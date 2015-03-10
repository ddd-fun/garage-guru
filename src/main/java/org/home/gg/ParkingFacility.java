package org.home.gg;


import java.util.Optional;

public interface ParkingFacility {

    AvailableLots getAvailableLots();

    Optional<ParkingLot> findSuitableLotFor(ParkingLotSpec parkingLotSpec);

    Optional<ParkingLot> findVehicle(VehicleId vehicleId);

    void tryToPark(VehicleId id, ParkingLotSpec parkingLotSpec, ParkingLot parkingLot) throws OutOfAvailablePlacesException, VehicleIsAlreadyParkedException;

}
