package org.home.gg.domain.garage;


import org.home.gg.domain.common.Reject;
import org.home.gg.domain.vehicle.VehicleId;
import org.home.gg.domain.vehicle.VehicleType;


/**
 * This entity and aggregate represents parking lot concept which uniquely identified by its LotLocation.
 * The lot could be in two states: free and occupied, when lot is occupied then vehicle id is kept.
 * The vehicle specification is responsible for matching vehicles types which could be parked on this lot.
 */
public class ParkingLot {

    private final LotLocation location;
    private final VehicleSpec supportedVehiclesSpec;
    private VehicleId parkedVehicle;


    public ParkingLot(LotLocation location, VehicleSpec supportedVehiclesSpec) {
      Reject.ifNull(location);
      Reject.ifNull(supportedVehiclesSpec);
      this.location = location;
      this.supportedVehiclesSpec = supportedVehiclesSpec;
    }


    public void parkVehicle(VehicleId vehicleId, VehicleType vehicleType){
        if(!this.supportedVehiclesSpec.isSatisfiedBy(vehicleType)){
           throw new IllegalArgumentException(String.format("%s is not supported by %s", vehicleType, supportedVehiclesSpec));
        }

        if(isFree()){
          this.parkedVehicle = vehicleId;
        } else {
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

    public VehicleSpec getVehiclesSpec() {
      return this.supportedVehiclesSpec;
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
