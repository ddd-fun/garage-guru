package org.home.gg.application;


import org.home.gg.domain.garage.Garage;
import org.home.gg.domain.garage.ParkingLot;
import org.home.gg.domain.garage.ParkingLotBuilder;
import org.home.gg.infrastructure.GarageInMemoryImpl;
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

    public Garage buildGarage(){
        return new GarageInMemoryImpl(lotsSet);
    }


}
