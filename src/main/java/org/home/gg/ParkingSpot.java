package org.home.gg;


public class ParkingSpot   {

    private final SpotLocation location;
    private final VehicleSpec vehicleSpec;

    public ParkingSpot(SpotLocation location, VehicleSpec vehicleSpec) {
      this.location = location;
      this.vehicleSpec = vehicleSpec;
    }


    public SpotLocation getLocation() {
      return this.location;
    }

    public boolean couldPark(VehicleType vehicleType){
      return this.vehicleSpec.isSatisfiedBy(vehicleType);
   }


}
