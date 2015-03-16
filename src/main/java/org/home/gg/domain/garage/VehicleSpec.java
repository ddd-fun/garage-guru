package org.home.gg.domain.garage;


import org.home.gg.domain.vehicle.VehicleType;

public interface VehicleSpec {
   public boolean isSatisfiedBy(VehicleType type);
}
