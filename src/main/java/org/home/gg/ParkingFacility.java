package org.home.gg;


public interface ParkingFacility {

    void park(VehicleId id) throws OutOfAvailablePlacesException;

    public boolean couldPark();

}
