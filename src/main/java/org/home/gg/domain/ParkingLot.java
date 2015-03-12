package org.home.gg.domain;


import org.home.gg.domain.common.Validators;

public class ParkingLot {

    private final LotLocation location;
    private final VehicleSpec supportedVehiclesSpec;
    private VehicleId parkedVehicle;

    public ParkingLot(LotLocation location, VehicleSpec supportedVehiclesSpec) {
      Validators.assertArgIsNotNull(location);
      Validators.assertArgIsNotNull(supportedVehiclesSpec);
      this.location = location;
      this.supportedVehiclesSpec = supportedVehiclesSpec;
    }


    public void parkVehicle(VehicleId vehicleId, VehicleType vehicleType){
        if(!this.supportedVehiclesSpec.isSatisfiedBy(vehicleType)){
           //TODO consider exception for this case
           throw new IllegalArgumentException(String.format("%s is not supported by %s", vehicleType, supportedVehiclesSpec));
        }

        if(isFree()){
          this.parkedVehicle = vehicleId;
        } else {
           //TODO consider exception for this case
           throw new IllegalStateException(String.format("%s is already reserved by vehicle %s", this, vehicleId));
        }
    }

    public boolean supports(VehicleType vehicleType){
      return this.supportedVehiclesSpec.isSatisfiedBy(vehicleType);
    }

    public boolean isFree(){
      return parkedVehicle == null;
    }

    public VehicleId getParkedVehicle() {
      return parkedVehicle;
    }

    public void release(VehicleId vehicleId) {
        if(!isFree()){
          if( this.parkedVehicle.equals(vehicleId) ){
              this.parkedVehicle = null;
          } else {
              throw new IllegalArgumentException(String.format("%s is not parked on %s", vehicleId, location));
          }
        }

    }

    public LotLocation getLocation() {
      return this.location;
    }

    @Override
    public String toString() {
        return String.format("ParkingLot: %s, suitable vehicle: %s", location, this.supportedVehiclesSpec);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParkingLot)) return false;

        ParkingLot that = (ParkingLot) o;

        if (!location.equals(that.location)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return location.hashCode();
    }
}
