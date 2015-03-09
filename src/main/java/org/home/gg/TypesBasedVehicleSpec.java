package org.home.gg;


import java.util.EnumSet;

public class TypesBasedVehicleSpec  implements VehicleSpec{

    private EnumSet<VehicleType> possibleTypes;

    public TypesBasedVehicleSpec(EnumSet<VehicleType> possibleTypes) {
      this.possibleTypes = EnumSet.copyOf(possibleTypes);
    }

    @Override
    public boolean isSatisfiedBy(VehicleType vehicleType) {
      return this.possibleTypes.contains(vehicleType);
    }


}
