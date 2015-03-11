package org.home.gg.infrastructure;


import org.home.gg.domain.*;

import java.util.*;

public class InMemoryGarage implements ParkingFacility {

    private final Set<ParkingLot> availableLots;
    private final Map<VehicleId, ParkingLot> reservedLots;

    public InMemoryGarage(Set<ParkingLot> availableLots) {
       this.availableLots = new HashSet<ParkingLot>(availableLots);
       this.reservedLots = new HashMap<VehicleId, ParkingLot>(availableLots.size());
    }

    @Override
    public Optional<ParkingLot> findVehicle(VehicleId vehicleId) {
       if(this.reservedLots.containsKey(vehicleId)){
          return Optional.of(this.reservedLots.get(vehicleId));
       }else{
         return Optional.empty();
       }
    }

    @Override
    public Optional<ParkingLot> findSuitableLotFor(VehicleType vehicle) {
        for(ParkingLot aLot : this.availableLots){
            if(aLot.supports(vehicle)){
              return Optional.of(aLot);
            }
        }
       return Optional.empty();
    }

    @Override
    public void save(ParkingLot parkingLot)
             throws ParkingLotNotFoundException, VehicleIsAlreadyParkedException {

        if(!parkingLot.isFree()){
           park(parkingLot.getParkedVehicle(), parkingLot);
        } //TODO add release logic

    }


    private void park(VehicleId id, ParkingLot parkingLot){
        if(this.availableLots.contains(parkingLot)){
            if(!this.reservedLots.containsValue(parkingLot)){
                this.availableLots.remove(parkingLot);
                this.reservedLots.put(id, parkingLot);
            }else {
                throw new IllegalStateException(String.format("%s is already reserved", parkingLot));
            }
        }else{
            throw new IllegalStateException(String.format("%s is not available", parkingLot));
        }
    }


    @Override
    public AvailableLots getAvailableLots() {
        return new AvailableLots(this.availableLots.size());
    }
}
