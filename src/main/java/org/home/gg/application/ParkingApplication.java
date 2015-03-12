package org.home.gg.application;


import org.home.gg.domain.*;

import java.util.Optional;

public class ParkingApplication {

    private final ParkingFacility parkingFacility;

    public ParkingApplication(ParkingFacility parkingFacility) {
        this.parkingFacility = parkingFacility;
    }

    public LotLocation tryToPark(VehicleId vehicleId, VehicleType vehicleType)
            throws ParkingLotNotFoundException, VehicleIsAlreadyParkedException {

        checkIfVehicleAlreadyParked(vehicleId);

        ParkingLot parkingLot = getSuitableParkingLot(vehicleType);

        parkingLot.parkVehicle(vehicleId, vehicleType);

        this.parkingFacility.save(parkingLot);

      return parkingLot.getLocation();
    }

    public void releaseParkingLot(VehicleId vehicleId){

        Optional<ParkingLot> maybeParkingLot = this.parkingFacility.findParkedVehicle(vehicleId);

        if(maybeParkingLot.isPresent()){
            ParkingLot lot = maybeParkingLot.get();
            lot.release(vehicleId);
           this.parkingFacility.save(lot);
        }else{
           throw new ParkingLotNotFoundException(String.format("parking lot occupied by %s is not found", vehicleId));
        }
    }



    private ParkingLot getSuitableParkingLot(VehicleType vehicle){
        Optional<ParkingLot> maybeParkingLot = this.parkingFacility.findSuitableLotFor(vehicle);
        if(maybeParkingLot.isPresent()){
           return maybeParkingLot.get();
        }else{
          throw new ParkingLotNotFoundException(String.format("parking lot suitable for %s is not found", vehicle));
        }
    }

    private void checkIfVehicleAlreadyParked(VehicleId vehicleId){
        Optional<ParkingLot> maybeParkingLot = this.parkingFacility.findParkedVehicle(vehicleId);
        if(maybeParkingLot.isPresent()){
           throw new VehicleIsAlreadyParkedException(vehicleId, maybeParkingLot.get().getLocation());
        }
    }


    public Optional<LotLocation> findParkedVehicleBy(VehicleId vehicleId){
        Optional<ParkingLot> lotOptional = this.parkingFacility.findParkedVehicle(vehicleId);
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
