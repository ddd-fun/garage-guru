package org.home.gg;


import org.home.gg.domain.*;
import org.home.gg.infrastructure.InMemoryGarage;
import java.util.HashSet;
import java.util.Set;


public class GarageBuilder {

    private Set<ParkingLot> lotsSet = new HashSet<ParkingLot>();

    public GarageBuilder with(ParkingLotBuilder parkingLotBuilder){
        ParkingLot parkingLot = parkingLotBuilder.build();
        if(this.lotsSet.contains(parkingLot)){
           throw new IllegalArgumentException(String.format("already contains %s", parkingLot));
        }else{
           this.lotsSet.add(parkingLot);
        }
      return this;
    }

    public ParkingLotRepo buildGarage(){
        return new InMemoryGarage(lotsSet);
    }


}
