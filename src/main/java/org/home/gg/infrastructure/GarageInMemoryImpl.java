package org.home.gg.infrastructure;


import org.home.gg.domain.common.Reject;
import org.home.gg.domain.garage.NumberOfFreeLots;
import org.home.gg.domain.garage.Garage;
import org.home.gg.domain.garage.ParkingLot;
import org.home.gg.domain.vehicle.VehicleId;
import org.home.gg.domain.vehicle.VehicleType;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.Map;
import java.util.stream.Stream;
import static java.util.stream.Collectors.*;

public class GarageInMemoryImpl implements Garage {


    private final Set<ParkingLot> parkingLotSet;


    public GarageInMemoryImpl(Set<ParkingLot> parkingLotSet) {
      Reject.ifNull(parkingLotSet);
      this.parkingLotSet = new HashSet<>(parkingLotSet);
    }


    private Stream<ParkingLot> streamLots(){
      return this.parkingLotSet.stream();
    }


    @Override
    public NumberOfFreeLots getNumberOfFreeLots() {
        Map<String, Long> map = streamLots().filter(ParkingLot::isFree)
                .collect(groupingBy((ParkingLot aLot) -> aLot.getVehiclesSpec().toString(), counting()));
      return new NumberOfFreeLots(map);
    }


    @Override
    public Optional<ParkingLot> findSuitableLotFor(VehicleType vehicle) {
       return streamLots().filter((ParkingLot lot) -> lot.couldParkVehicle(vehicle) ).findFirst();
    }

    @Override
    public Optional<ParkingLot> findParkedVehicle(VehicleId vehicleId) {
      return streamLots().filter((ParkingLot lot)-> lot.isTakenBy(vehicleId)).findFirst();
    }

}
