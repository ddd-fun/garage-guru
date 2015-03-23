package org.home.gg.domain.garage;

import org.home.gg.domain.vehicle.VehicleId;
import org.home.gg.domain.vehicle.VehicleType;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParkingLotTest {


    @Test
    public void testEquality(){

        ParkingLotBuilder parkingLotExample = aParkingLot().with(new LotLocation("A", "1"))
                                                           .with(anyVehicleSpec());

        ParkingLot aParkingLot = parkingLotExample.build();
        assertTrue("the same instances are equal", aParkingLot.equals(aParkingLot));

        ParkingLot theSameParkingLot = parkingLotExample.build();
        assertTrue("parking lots with the same lot locations are equal", aParkingLot.equals(theSameParkingLot));
        assertTrue("equality is symmetric", theSameParkingLot.equals(aParkingLot));

        ParkingLot otherParkingLot = parkingLotExample.but().with(new LotLocation("B", "2")).build();
        assertFalse("parking lots with different lot locations are  not equal", aParkingLot.equals(otherParkingLot));
    }


    @Test
    public void whenThereIsNoVehicleParkedThenParkingLotIsFree(){

        ParkingLot parkingLot = aFreeParkingLot().build();

        assertTrue(parkingLot.isFree());
    }


    @Test
    public void whenVehicleIsParkedThenParkingLotIsTaken(){

        ParkingLot parkingLot = aFreeParkingLot().build();

        VehicleId vehicleId = new VehicleId("AB213H");

        parkingLot.parkVehicle(vehicleId, VehicleType.CAR);

        assertTrue(parkingLot.isTakenBy(vehicleId));

        assertFalse("vehicle should be properly identified", parkingLot.isTakenBy(new VehicleId("A789JK")));
    }

    @Test
    public void whenParkingLotIsCleanedThenItIsFree(){

        ParkingLot parkingLot = aFreeParkingLot().build();

        VehicleId vehicleId = new VehicleId("AB123H");
        parkingLot.parkVehicle(vehicleId, VehicleType.CAR);

        parkingLot.clean(vehicleId);

        assertTrue("lot is free", parkingLot.isFree());
        assertFalse("lot is not taken by vehicle", parkingLot.isTakenBy(vehicleId));
    }


    @Test
    public void whenParkingLotDoesntSupportVehicleTypeThenVehicleIsRejected(){

        ParkingLot parkingLot = aFreeParkingLot().but().with(neitherVehicleSpec()).build();
        try{
            parkingLot.parkVehicle(new VehicleId("AB213H"), VehicleType.CAR);
            fail("vehicle should be rejected");
        }catch (RuntimeException ex){
            assertTrue(parkingLot.isFree());
        }

    }


    public ParkingLotBuilder aParkingLot(){
      return new ParkingLotBuilder();
    }


    public ParkingLotBuilder aFreeParkingLot(){
        return new ParkingLotBuilder()
                       .with(new LotLocation("A", "1"))
                       .with(anyVehicleSpec());
    }



    public VehicleSpec anyVehicleSpec(){
        return new VehicleSpec() {
            @Override
            public boolean isSatisfiedBy(VehicleType parkingLot) {
                return true;
            }
        };
    }

    public VehicleSpec neitherVehicleSpec(){
        return new VehicleSpec() {
            @Override
            public boolean isSatisfiedBy(VehicleType parkingLot) {
                return false;
            }
        };
    }


}