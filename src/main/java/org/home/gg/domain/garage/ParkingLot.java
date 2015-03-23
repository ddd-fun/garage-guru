package org.home.gg.domain.garage;


import org.home.gg.domain.common.Reject;
import org.home.gg.domain.vehicle.VehicleId;
import org.home.gg.domain.vehicle.VehicleType;


/**
 * This entity and aggregate represents parking lot concept which uniquely identified by its LotLocation.
 * The lot could be in two states: free and taken, when lot is taken then vehicle id is kept.
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
           throw new IllegalArgumentException(String.format("%s not acceptable vehicle", vehicleType));
        }

        if(isFree()){
          this.parkedVehicle = vehicleId;
        } else {
          throw new IllegalStateException(String.format("%s is already taken by vehicle %s", this, vehicleId));
        }
    }

    public boolean couldParkVehicle(VehicleType vehicleType){
      return isFree() && this.supportedVehiclesSpec.isSatisfiedBy(vehicleType);
    }

    public boolean isFree(){
      return parkedVehicle == null;
    }

    private boolean isTaken(){
     return !isFree();
    }


    public boolean isTakenBy(VehicleId vehicleId){
       return isTaken() && this.parkedVehicle.equals(vehicleId);
    }


    public void clean(VehicleId vehicleId) {
        if(isTakenBy(vehicleId)){
           this.parkedVehicle = null;
        }else{
          throw new IllegalArgumentException(String.format("%s is not parked on %s", vehicleId, location));
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
