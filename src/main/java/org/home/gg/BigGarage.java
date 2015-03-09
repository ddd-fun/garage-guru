package org.home.gg;


import java.util.*;

public class BigGarage implements ParkingFacility {

    private final Set<ParkingLot> availableSlots;
    private final Map<VehicleId, ParkingLot> reservedSlots;

    public BigGarage(Set<ParkingLot> availableSlots) {
       this.availableSlots = new HashSet<ParkingLot>(availableSlots);
       this.reservedSlots = new HashMap<VehicleId, ParkingLot>(availableSlots.size());
    }

    @Override
    public Optional<LotLocation> findVehicle(VehicleId vehicleId) {
       if(this.reservedSlots.containsKey(vehicleId)){
          return Optional.of(this.reservedSlots.get(vehicleId).getLocation());
       }else{
         return Optional.empty();
       }
    }

    @Override
    public Optional<LotLocation> findSuitableLotFor(VehicleType vehicleType) {
        for(ParkingLot aLot : this.availableSlots){
            if(aLot.couldPark(vehicleType)){
              return Optional.of(aLot.getLocation());
            }
        }
       return Optional.empty();
    }

    @Override
    public void tryToPark(VehicleId vehicleId, VehicleType vehicleType, LotLocation location)
             throws OutOfAvailablePlacesException, VehicleIsAlreadyParkedException {

        Optional<LotLocation> lotLocationSearchResult = findVehicle(vehicleId);
        if(lotLocationSearchResult.isPresent()){
          throw new VehicleIsAlreadyParkedException(vehicleId, lotLocationSearchResult.get());
        }

        Optional<ParkingLot> parkingLotSearchResult = findSpotFor(vehicleType);
        if(parkingLotSearchResult.isPresent()){
            park(vehicleId, parkingLotSearchResult.get());
        } else {
           throw new OutOfAvailablePlacesException(String.format("no more place for %s", vehicleType));
        }
    }


    private void park(VehicleId id, ParkingLot spot){
        this.availableSlots.remove(spot);
        this.reservedSlots.put(id, spot);
    }

    private Optional<ParkingLot> findSpotFor(VehicleType vehicleType){
        for(ParkingLot parkingSlot : this.availableSlots){
            if(parkingSlot.couldPark(vehicleType)){
                return Optional.of(parkingSlot);
            }
        }
      return Optional.empty();
    }


    @Override
    public AvailableLots getAvailableLots() {
        return new AvailableLots(this.availableSlots.size());
    }
}
