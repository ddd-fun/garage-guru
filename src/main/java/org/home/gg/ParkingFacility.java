package org.home.gg;


public interface ParkingFacility {

    SpotLocation tryToPark(VehicleId id, VehicleType vehicleType) throws OutOfAvailablePlacesException;

}
