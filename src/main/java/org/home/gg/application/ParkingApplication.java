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

        checkIfVehicleAlreadyParked(vehicleId);

        ParkingLot parkingLot = getSuitableParkingLot(parkingLotSpec);

        parkingLot.parkVehicle(vehicleId, parkingLotSpec);

        this.parkingFacility.save(parkingLot);

      return parkingLot.getLocation();
    }

    private ParkingLot getSuitableParkingLot(ParkingLotSpec parkingLotSpec){
        Optional<ParkingLot> maybeParkingLot = this.parkingFacility.findSuitableLotFor(parkingLotSpec);
        if(maybeParkingLot.isPresent()){
           return maybeParkingLot.get();
        }else{
          throw new OutOfAvailablePlacesException(String.format("no more lot suitable for %s", parkingLotSpec));
        }
    }

    private void checkIfVehicleAlreadyParked(VehicleId vehicleId){
        Optional<ParkingLot> maybeParkingLot = this.parkingFacility.findVehicle(vehicleId);
        if(maybeParkingLot.isPresent()){
           throw new VehicleIsAlreadyParkedException(vehicleId, maybeParkingLot.get().getLocation());
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
