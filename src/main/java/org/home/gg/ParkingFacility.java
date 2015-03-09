package org.home.gg;


public interface ParkingFacility {

    LotLocation tryToPark(VehicleId id, VehicleType vehicleType) throws OutOfAvailablePlacesException;

}
