package org.home.gg;


import java.util.HashSet;
import java.util.Set;

public class SimpleGarage implements ParkingFacility {

    private final int maxPlaces;
    private final Set<VehicleId> parkedVehicles;


    public SimpleGarage(int maxPlaces) {
        if(maxPlaces < 1){
          throw new IllegalArgumentException(String.format("%s is illegal number of max places", maxPlaces));
        }
        this.maxPlaces = maxPlaces;
        this.parkedVehicles = new HashSet<VehicleId>(maxPlaces);
    }

    @Override
    public void park(VehicleId id) {
        if(hasAvailablePlace()){
            parkedVehicles.add(id);
        } else {
           throw new OutOfAvailablePlacesException("garage is full");
        }
    }

    private boolean hasAvailablePlace(){
      return this.parkedVehicles.size() < maxPlaces;
    }

    @Override
    public boolean couldPark() {
      return hasAvailablePlace();
    }


}
