package org.home.gg;


public class ParkingLot {

    private final LotLocation location;
    private final VehicleSpec vehicleSpec;

    public ParkingLot(LotLocation location, VehicleSpec vehicleSpec) {
      this.location = location;
      this.vehicleSpec = vehicleSpec;
    }


    public LotLocation getLocation() {
      return this.location;
    }

    public boolean couldPark(VehicleType vehicleType){
      return this.vehicleSpec.isSatisfiedBy(vehicleType);
   }


}
