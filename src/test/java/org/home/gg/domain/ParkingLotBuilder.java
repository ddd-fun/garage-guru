package org.home.gg.domain;


import java.util.EnumSet;

public class ParkingLotBuilder {


    private LotLocation lotLocation;
    private EnumSet<VehicleType> supportedVehicles = EnumSet.of(VehicleType.CAR);

    public ParkingLotBuilder with(LotLocation lotLocation){
        this.lotLocation = lotLocation;
      return this;
    }

    public ParkingLotBuilder with(EnumSet<VehicleType> supportedVehicles){
        this.supportedVehicles = supportedVehicles;
      return this;
    }

    public ParkingLotBuilder but(){
        return new ParkingLotBuilder().with(lotLocation).with(supportedVehicles);
    }

    public ParkingLot build(){
        return new ParkingLot(lotLocation, supportedVehicles);
    }

}



