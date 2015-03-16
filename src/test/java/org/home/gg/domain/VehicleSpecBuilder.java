package org.home.gg.domain;


import org.home.gg.domain.garage.VehicleSpec;
import org.home.gg.domain.vehicle.VehicleType;

public class VehicleSpecBuilder {


    public static VehicleSpec anyVehicleSpec(){
        return new VehicleSpec() {
            @Override
            public boolean isSatisfiedBy(VehicleType parkingLot) {
                return true;
            }
        };
    }

    public static VehicleSpec neitherVehicleSpec(){
        return new VehicleSpec() {
            @Override
            public boolean isSatisfiedBy(VehicleType parkingLot) {
                return false;
            }
        };
    }

}
