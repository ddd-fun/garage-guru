package org.home.gg.domain;

public class TypeBasedParkingLotSpec implements ParkingLotSpec {


    private VehicleType vehicleType;

    public TypeBasedParkingLotSpec(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Override
    public boolean isSatisfiedBy(ParkingLot parkingLot) {
      return parkingLot.getSuitableVehicleTypes().contains(this.vehicleType);
    }



}
