package org.home.gg.domain.garage;


import org.home.gg.domain.common.Reject;
import org.home.gg.domain.vehicle.VehicleType;

import java.util.EnumSet;
import java.util.stream.Collectors;

public class TypeBasedVehicleSpec implements VehicleSpec {

    private final EnumSet<VehicleType> supportedVehicleTypes;
    private final String asString;

    public TypeBasedVehicleSpec(EnumSet<VehicleType> supportedVehicleTypes) {
       Reject.ifNull(supportedVehicleTypes);
       this.supportedVehicleTypes = EnumSet.copyOf(supportedVehicleTypes);
       this.asString = asCsv();
    }

    private String asCsv(){
      return this.supportedVehicleTypes.stream().map(VehicleType::name).collect(Collectors.joining(", "));
    }

    @Override
    public boolean isSatisfiedBy(VehicleType type) {
       return this.supportedVehicleTypes.contains(type);
    }


    @Override
    public String toString() {
       return asString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof TypeBasedVehicleSpec)) return false;

        TypeBasedVehicleSpec that = (TypeBasedVehicleSpec) o;

        if(supportedVehicleTypes.size() != that.supportedVehicleTypes.size()){
          return false;
        }


        for(VehicleType aType: this.supportedVehicleTypes){
           if(!that.supportedVehicleTypes.contains(aType)){
              return false;
           }
        }

        return true;
    }

    @Override
    public int hashCode() {
        return supportedVehicleTypes.hashCode();
    }
}
