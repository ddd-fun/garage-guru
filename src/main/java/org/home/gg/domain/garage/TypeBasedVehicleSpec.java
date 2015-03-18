package org.home.gg.domain.garage;


import org.home.gg.domain.common.Reject;
import org.home.gg.domain.vehicle.VehicleType;

import java.util.EnumSet;

public class TypeBasedVehicleSpec implements VehicleSpec {

    private final EnumSet<VehicleType> supportedVehicleTypes;

    public TypeBasedVehicleSpec(EnumSet<VehicleType> supportedVehicleTypes) {
       Reject.ifNull(supportedVehicleTypes);
       this.supportedVehicleTypes = EnumSet.copyOf(supportedVehicleTypes);
    }

    @Override
    public boolean isSatisfiedBy(VehicleType type) {
       return this.supportedVehicleTypes.contains(type);
    }
}
