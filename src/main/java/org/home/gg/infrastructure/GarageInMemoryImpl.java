package org.home.gg.infrastructure;


import org.home.gg.domain.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class GarageInMemoryImpl implements Garage {


    private final Set<ParkingLot> parkingLotSet;


    public GarageInMemoryImpl(Set<ParkingLot> parkingLotSet) {
      this.parkingLotSet = new HashSet<>(parkingLotSet);
    }


    private Stream<ParkingLot> streamLots(){
      return this.parkingLotSet.stream();
    }


    @Override
    public AvailableLots getAvailableLots() {
      return new AvailableLots(streamLots().filter(ParkingLot::isFree).count());
    }


    @Override
    public Optional<ParkingLot> findSuitableLotFor(VehicleType vehicle) {
       return streamLots().filter((ParkingLot lot) -> lot.supports(vehicle) && lot.isFree() ).findFirst();
    }

    @Override
    public Optional<ParkingLot> findParkedVehicle(VehicleId vehicleId) {
      return streamLots().filter((ParkingLot lot)-> !lot.isFree() && lot.getParkedVehicle().equals(vehicleId)).findFirst();
    }

}
