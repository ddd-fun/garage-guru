package org.home.gg;


import java.util.Optional;

public interface ParkingFacility {

    AvailableLots getAvailableLots();

    Optional<LotLocation> findSuitableLotFor(VehicleType vehicleType);

    Optional<LotLocation> findVehicle(VehicleId vehicleId);

    void tryToPark(VehicleId id, VehicleType vehicleType, LotLocation location) throws OutOfAvailablePlacesException, VehicleIsAlreadyParkedException;

}
