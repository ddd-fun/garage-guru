package org.home.gg;


import java.util.*;

public class BigGarage implements ParkingFacility {

    private final Set<ParkingSpot> availableSlots;
    private final Map<VehicleId, ParkingSpot> reservedSlots;

    public BigGarage(Set<ParkingSpot> availableSlots) {
       this.availableSlots = new HashSet<ParkingSpot>(availableSlots);
       this.reservedSlots = new HashMap<VehicleId, ParkingSpot>(availableSlots.size());
    }

    @Override
    public SpotLocation tryToPark(VehicleId vehicleId, VehicleType vehicleType) throws OutOfAvailablePlacesException {
        Optional<ParkingSpot> foundSpot = findSpotFor(vehicleType);
        if(foundSpot.isPresent()){
            park(vehicleId, foundSpot.get());
          return foundSpot.get().getLocation();
        } else {
           throw new OutOfAvailablePlacesException(String.format("no more place for %s", vehicleType));
        }
    }


    private void park(VehicleId id, ParkingSpot spot){
        this.availableSlots.remove(spot);
        this.reservedSlots.put(id, spot);
    }

    private Optional<ParkingSpot> findSpotFor(VehicleType vehicleType){
        for(ParkingSpot parkingSlot : this.availableSlots){
            if(parkingSlot.couldPark(vehicleType)){
                return Optional.of(parkingSlot);
            }
        }
      return Optional.empty();
    }



}
