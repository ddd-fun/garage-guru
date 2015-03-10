package org.home.gg;


import java.util.*;

public class BigGarage implements ParkingFacility {

    private final Set<ParkingLot> availableLots;
    private final Map<VehicleId, ParkingLot> reservedLots;

    public BigGarage(Set<ParkingLot> availableLots) {
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
    public Optional<ParkingLot> findSuitableLotFor(ParkingLotSpec parkingLotSpec) {
        for(ParkingLot aLot : this.availableLots){
            if(parkingLotSpec.isSatisfiedBy(aLot)){
              return Optional.of(aLot);
            }
        }
       return Optional.empty();
    }

    @Override
    public void tryToPark(VehicleId vehicleId, ParkingLotSpec parkingLotSpec, ParkingLot parkingLot)
             throws OutOfAvailablePlacesException, VehicleIsAlreadyParkedException {

        if(!parkingLotSpec.isSatisfiedBy(parkingLot)){
          throw new IllegalArgumentException(String.format("%s is not satisfied by %s", parkingLot, parkingLotSpec));
        }

        Optional<ParkingLot> lotLocationSearchResult = findVehicle(vehicleId);
        if(lotLocationSearchResult.isPresent()){
          throw new VehicleIsAlreadyParkedException(vehicleId, lotLocationSearchResult.get().getLocation());
        }

        park(vehicleId, parkingLot);

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
