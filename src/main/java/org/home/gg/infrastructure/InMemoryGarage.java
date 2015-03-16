package org.home.gg.infrastructure;


import org.home.gg.domain.*;

import java.util.*;

public class InMemoryGarage implements Garage, ParkingLotRepo {

    private final Set<ParkingLot> availableLots;
    private final Map<VehicleId, ParkingLot> reservedLots;

    public InMemoryGarage(Set<ParkingLot> availableLots) {
       this.availableLots = new HashSet<ParkingLot>(availableLots);
       this.reservedLots = new HashMap<VehicleId, ParkingLot>(availableLots.size());
    }

    @Override
    public Optional<ParkingLot> findParkedVehicle(VehicleId vehicleId) {
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
    public void update(ParkingLot parkingLot){

        if(!parkingLot.isFree()){
           park(parkingLot.getParkedVehicle(), parkingLot);
        } else {
           release(parkingLot);
        }

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


    private void release(ParkingLot parkingLot){
        if(!this.reservedLots.containsValue(parkingLot.getParkedVehicle())){
            this.availableLots.add(parkingLot);
            removeFromReserved(parkingLot);
        }else {
           throw new IllegalStateException(String.format("%s is not in reserved set", parkingLot));
        }

    }

    private void removeFromReserved(ParkingLot parkingLot){

        VehicleId key = null;
        for(Map.Entry<VehicleId, ParkingLot> entry : this.reservedLots.entrySet() ){
            if(parkingLot.equals(entry.getValue())){
                key = entry.getKey();
                break;
            }
        }
        this.reservedLots.remove(key);
    }


    @Override
    public AvailableLots getAvailableLots() {
        return new AvailableLots(this.availableLots.size());
    }
}
