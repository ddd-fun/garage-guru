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
    public LotLocation tryToPark(VehicleId vehicleId, VehicleType vehicleType) throws OutOfAvailablePlacesException {
        Optional<ParkingLot> foundSpot = findSpotFor(vehicleType);
        if(foundSpot.isPresent()){
            park(vehicleId, foundSpot.get());
          return foundSpot.get().getLocation();
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



}
