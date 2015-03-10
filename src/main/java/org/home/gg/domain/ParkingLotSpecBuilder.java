package org.home.gg.domain;


public class ParkingLotSpecBuilder {


    public static ParkingLotSpec forCar(){
       return new TypeBasedParkingLotSpec(VehicleType.CAR);
    }

    public  static ParkingLotSpec forMotorbike(){
       return new TypeBasedParkingLotSpec(VehicleType.MOTORBIKE);
    }



    public static class TypeBasedParkingLotSpec implements ParkingLotSpec {

        private VehicleType vehicleType;

        public TypeBasedParkingLotSpec(VehicleType vehicleType) {
          this.vehicleType = vehicleType;
        }

        @Override
        public boolean isSatisfiedBy(ParkingLot parkingLot) {
          return parkingLot.getSuitableVehicleTypes().contains(this.vehicleType);
        }


    }
}
