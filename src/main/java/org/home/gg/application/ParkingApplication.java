package org.home.gg.application;


import org.home.gg.domain.garage.NumberOfFreeLots;
import org.home.gg.domain.garage.Garage;
import org.home.gg.domain.garage.LotLocation;
import org.home.gg.domain.garage.ParkingLot;
import org.home.gg.domain.vehicle.VehicleId;
import org.home.gg.domain.vehicle.VehicleType;

import java.util.Optional;

public class ParkingApplication {

    private final Garage garage;

    public ParkingApplication(Garage garage) {
        this.garage = garage;
    }

    public LotLocation tryToPark(VehicleId vehicleId, VehicleType vehicleType)
            throws ParkingLotNotFoundException, VehicleIsAlreadyParkedException {

        checkIfVehicleAlreadyParked(vehicleId);

        ParkingLot parkingLot = getSuitableParkingLot(vehicleType);

        parkingLot.parkVehicle(vehicleId, vehicleType);

      return parkingLot.getLocation();
    }

    public void cleanParkingLot(VehicleId vehicleId){

        Optional<ParkingLot> maybeParkingLot = this.garage.findParkedVehicle(vehicleId);

        if(maybeParkingLot.isPresent()){
            ParkingLot lot = maybeParkingLot.get();
            lot.clean(vehicleId);
        }else{
           throw new ParkingLotNotFoundException(String.format("parking lot taken by %s is not found", vehicleId));
        }
    }



    private ParkingLot getSuitableParkingLot(VehicleType vehicle){
        Optional<ParkingLot> maybeParkingLot = this.garage.findSuitableLotFor(vehicle);
        if(maybeParkingLot.isPresent()){
           return maybeParkingLot.get();
        }else{
          throw new ParkingLotNotFoundException(String.format("parking lot suitable for %s is not found", vehicle));
        }
    }

    private void checkIfVehicleAlreadyParked(VehicleId vehicleId){
        Optional<ParkingLot> maybeParkingLot = this.garage.findParkedVehicle(vehicleId);
        if(maybeParkingLot.isPresent()){
           throw new VehicleIsAlreadyParkedException(vehicleId, maybeParkingLot.get().getLocation());
        }
    }


    public Optional<LotLocation> findParkedVehicleBy(VehicleId vehicleId){
        Optional<ParkingLot> lotOptional = this.garage.findParkedVehicle(vehicleId);
        if(lotOptional.isPresent()){
            return Optional.of(lotOptional.get().getLocation());
        }else{
          return Optional.empty();
        }
    }


    public NumberOfFreeLots getAvailableLots(){
       return this.garage.getNumberOfFreeLots();
    }




}
