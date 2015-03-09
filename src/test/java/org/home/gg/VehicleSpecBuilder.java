package org.home.gg;


import java.util.EnumSet;

public class VehicleSpecBuilder {

    public VehicleSpec aCar(){
      return new TypesBasedVehicleSpec(EnumSet.of(VehicleType.CAR));
    }

    public VehicleSpec aMatorbike(){
        return new TypesBasedVehicleSpec(EnumSet.of(VehicleType.MOTORBYKE));
    }

    public VehicleSpec aCarAndMotorbike(){
      return new TypesBasedVehicleSpec(EnumSet.of(VehicleType.CAR, VehicleType.MOTORBYKE));
    }


}
