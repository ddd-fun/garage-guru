package org.home.gg;


import java.util.Optional;

public class ParkingApplication {

    private final ParkingFacility parkingFacility;

    public ParkingApplication(ParkingFacility parkingFacility) {
        this.parkingFacility = parkingFacility;
    }

    public LotLocation tryToPark(VehicleId vehicleId, VehicleType vehicleType)
            throws OutOfAvailablePlacesException{

        Optional<LotLocation> lotLocationSearchResult = this.parkingFacility.findVehicle(vehicleId);
        if(lotLocationSearchResult.isPresent()){
          throw new VehicleIsAlreadyParkedException(vehicleId, lotLocationSearchResult.get());
        }


        Optional<LotLocation> locationSearchResult = this.parkingFacility.findSuitableLotFor(vehicleType);
        if(locationSearchResult.isPresent()){
           this.parkingFacility.tryToPark(vehicleId, vehicleType, locationSearchResult.get());
          return locationSearchResult.get();
        }else{
          throw new OutOfAvailablePlacesException(String.format("no more lot left suitable for %s", vehicleType));
        }
    }


    public Optional<LotLocation> findVehicleBy(VehicleId vehicleId){
      return this.parkingFacility.findVehicle(vehicleId);
    }


    public AvailableLots getAvailableLots(){
       return this.parkingFacility.getAvailableLots();
    }




}
