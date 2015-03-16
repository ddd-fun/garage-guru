package org.home.gg.domain.garage;


public class ParkingLotBuilder {


    private LotLocation lotLocation;
    private VehicleSpec supportedVehicles;

    public ParkingLotBuilder with(LotLocation lotLocation){
        this.lotLocation = lotLocation;
      return this;
    }

    public ParkingLotBuilder with(VehicleSpec supportedVehicles){
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



