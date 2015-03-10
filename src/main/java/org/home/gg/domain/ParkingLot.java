package org.home.gg.domain;


import java.util.EnumSet;

public class ParkingLot {

    private final LotLocation location;
    private final EnumSet<VehicleType>  suitableVehicleTypes;

    public ParkingLot(LotLocation location, EnumSet<VehicleType> suitableVehicleTypes) {
      this.location = location;
      this.suitableVehicleTypes = EnumSet.copyOf(suitableVehicleTypes);
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
