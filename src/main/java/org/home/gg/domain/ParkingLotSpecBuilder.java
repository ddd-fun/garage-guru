package org.home.gg.domain;


public class ParkingLotSpecBuilder {

    public static ParkingLotSpec forCar(){
      return new TypeBasedParkingLotSpec(VehicleType.CAR);
    }

    public  static ParkingLotSpec forMotorbike(){
      return new TypeBasedParkingLotSpec(VehicleType.MOTORBIKE);
    }

}
