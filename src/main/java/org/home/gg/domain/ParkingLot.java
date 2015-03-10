package org.home.gg.domain;


import java.util.EnumSet;

public class ParkingLot {

    private final LotLocation location;
    private final EnumSet<VehicleType>  suitableVehicleTypes;
    private VehicleId parkedVehicle;

    public ParkingLot(LotLocation location, EnumSet<VehicleType> suitableVehicleTypes) {
      this.location = location;
      this.suitableVehicleTypes = EnumSet.copyOf(suitableVehicleTypes);
    }


    public void parkVehicle(VehicleId vehicleId, ParkingLotSpec parkingLotSpec){
        if(!parkingLotSpec.isSatisfiedBy(this)){
           //TODO consider exception for this case
           throw new IllegalArgumentException(String.format("%s is not satisfied by %s", this, parkingLotSpec));
        }

        if(isFree()){
          this.parkedVehicle = vehicleId;
        } else {
           //TODO consider exception for this case
           throw new IllegalStateException(String.format("%s is already reserved by vehicle %s", this, vehicleId));
        }
    }


    public boolean isFree(){
      return parkedVehicle == null;
    }

    public VehicleId getParkedVehicle() {
      return parkedVehicle;
    }

    public void release() {
       this.parkedVehicle = null;
    }


    public LotLocation getLocation() {
      return this.location;
    }

    public EnumSet<VehicleType> getSuitableVehicleTypes() {
        return EnumSet.copyOf(this.suitableVehicleTypes);
    }

    @Override
    public String toString() {
        return String.format("ParkingLot: %s, suitable vehicle types: %s", location, suitableVehicleTypes);
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
