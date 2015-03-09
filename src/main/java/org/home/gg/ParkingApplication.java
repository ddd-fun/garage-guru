package org.home.gg;


import java.util.Optional;

public class ParkingApplication {

    private final ParkingFacility parkingFacility;

    public ParkingApplication(ParkingFacility parkingFacility) {
        this.parkingFacility = parkingFacility;
    }

    public ParkingTicket tryToPark(VehicleId vehicleId, VehicleType vehicleType)
            throws OutOfAvailablePlacesException{
        Optional<LotLocation> locationSearchResult = this.parkingFacility.findSuitableLotFor(vehicleType);
        if(locationSearchResult.isPresent()){
           this.parkingFacility.tryToPark(vehicleId, vehicleType, locationSearchResult.get());
          return new ParkingTicket(vehicleId, locationSearchResult.get());
        }else{
          throw new OutOfAvailablePlacesException(String.format("no more lot left suitable for %s", vehicleType));
        }
    }


}
