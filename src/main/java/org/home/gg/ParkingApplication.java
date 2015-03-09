package org.home.gg;


public class ParkingApplication {

    private final ParkingFacility parkingFacility;

    public ParkingApplication(ParkingFacility parkingFacility) {
        this.parkingFacility = parkingFacility;
    }

    public ParkingTicket tryToPark(VehicleId vehicleId, VehicleType vehicleType) throws OutOfAvailablePlacesException{
        SpotLocation location = parkingFacility.tryToPark(vehicleId, vehicleType);
       return new ParkingTicket(vehicleId, location);
    }


}
