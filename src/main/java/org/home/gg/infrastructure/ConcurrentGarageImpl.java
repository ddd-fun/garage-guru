package org.home.gg.infrastructure;


import org.home.gg.domain.*;


import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class ConcurrentGarageImpl implements ParkingFacility {


    private final ConcurrentHashMap<ParkingLot, Object> map;


    public ConcurrentGarageImpl(Set<ParkingLot> set) {
        this.map = new ConcurrentHashMap<>(set.size());
        set.stream().forEach((ParkingLot lot)-> map.put(lot, new Object()));
    }

    private Stream<ParkingLot> streamLots(){
      return this.map.keySet().stream();
    }

    @Override
    public AvailableLots getAvailableLots() {
       return new AvailableLots(  streamLots().filter(ParkingLot::isFree).count() );
    }

    @Override
    public Optional<ParkingLot> findSuitableLotFor(VehicleType vehicle) {
      return streamLots().filter((ParkingLot lot) -> lot.supports(vehicle) && lot.isFree()).findFirst();
    }

    @Override
    public Optional<ParkingLot> findParkedVehicle(VehicleId vehicleId) {
       return streamLots().filter((ParkingLot lot) -> !lot.isFree() && vehicleId.equals(lot.getParkedVehicle())).findFirst();
    }

    @Override
    public void save(ParkingLot parkingLot) {
      if(!this.map.containsKey(parkingLot)){
          throw new IllegalArgumentException(String.format("%s is not found"));
      }
    }
}
