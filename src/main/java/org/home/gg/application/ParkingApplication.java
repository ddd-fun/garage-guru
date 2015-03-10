package org.home.gg.application;


import org.home.gg.domain.*;

import java.util.Optional;

public class ParkingApplication {

    private final ParkingFacility parkingFacility;

    public ParkingApplication(ParkingFacility parkingFacility) {
        this.parkingFacility = parkingFacility;
    }

    public LotLocation tryToPark(VehicleId vehicleId, ParkingLotSpec parkingLotSpec)
            throws OutOfAvailablePlacesException {

        Optional<ParkingLot> lotLocationSearchResult = this.parkingFacility.findVehicle(vehicleId);
        if(lotLocationSearchResult.isPresent()){
          throw new VehicleIsAlreadyParkedException(vehicleId, lotLocationSearchResult.get().getLocation());
        }

        Optional<ParkingLot> locationSearchResult = this.parkingFacility.findSuitableLotFor(parkingLotSpec);
        if(locationSearchResult.isPresent()){
            ParkingLot parkingLot = locationSearchResult.get();
            parkingLot.parkVehicle(vehicleId, parkingLotSpec);
           this.parkingFacility.save(parkingLot);
          return parkingLot.getLocation();
        }else{
          throw new OutOfAvailablePlacesException(String.format("no more lot suitable for %s", parkingLotSpec));
        }
    }


    public Optional<LotLocation> findVehicleBy(VehicleId vehicleId){
        Optional<ParkingLot> lotOptional = this.parkingFacility.findVehicle(vehicleId);
        if(lotOptional.isPresent()){
            return Optional.of(lotOptional.get().getLocation());
        }else{
          return Optional.empty();
        }
    }


    public AvailableLots getAvailableLots(){
       return this.parkingFacility.getAvailableLots();
    }




}
