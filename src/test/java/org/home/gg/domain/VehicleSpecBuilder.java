package org.home.gg.domain;


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
