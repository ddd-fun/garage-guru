package org.home.gg.domain;

import org.junit.Test;

import java.util.EnumSet;

import static org.junit.Assert.*;

public class ParkingLotTest {


    @Test
    public void testEquality(){

        ParkingLotBuilder parkingLotExample = new ParkingLotBuilder().with(new LotLocation("A", "1"));

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

        ParkingLot parkingLot = aFreeParkingLot();

        assertTrue(parkingLot.isFree());
    }


    @Test
    public void whenVehicleIsParkedThenParkingLotIsNotFree(){

        ParkingLot parkingLot = aFreeParkingLot();

        VehicleId vehicleId = new VehicleId("AB213H");

        //I want to test only parking lot but not specification, so mocked it here
        parkingLot.parkVehicle(vehicleId, anyParkingLotIsAcceptable());

        assertFalse(parkingLot.isFree());

        assertEquals("correct vehicle id is returned", vehicleId, parkingLot.getParkedVehicle());
    }

    @Test
    public void whenParkingLotSpecificationDoesntSatisfyThenVehicleIsRejected(){

        ParkingLot parkingLot = aFreeParkingLot();
        try{
            parkingLot.parkVehicle(new VehicleId("AB213H"), neitherParkingLotIsAcceptable());
            fail("vehicle should be rejected");
        }catch (RuntimeException ex){
            assertTrue(parkingLot.isFree());
        }

    }


    public ParkingLot aFreeParkingLot(){
        return new ParkingLotBuilder()
                       .with(new LotLocation("A", "1"))
                       .with(EnumSet.of(VehicleType.CAR))
                   .build();
    }



    public ParkingLotSpec anyParkingLotIsAcceptable(){
        return new ParkingLotSpec() {
            @Override
            public boolean isSatisfiedBy(ParkingLot parkingLot) {
                return true;
            }
        };
    }

    public ParkingLotSpec neitherParkingLotIsAcceptable(){
        return new ParkingLotSpec() {
            @Override
            public boolean isSatisfiedBy(ParkingLot parkingLot) {
               return false;
            }
        };
    }




    static class ParkingLotBuilder{

        private LotLocation lotLocation;
        private EnumSet<VehicleType> supportedVehicles = EnumSet.of(VehicleType.CAR);

        ParkingLotBuilder with(LotLocation lotLocation){
           this.lotLocation = lotLocation;
         return this;
        }

        ParkingLotBuilder with(EnumSet<VehicleType> supportedVehicles){
            this.supportedVehicles = supportedVehicles;
            return this;
        }

        ParkingLotBuilder but(){
            return new ParkingLotBuilder().with(lotLocation).with(supportedVehicles);
        }

        ParkingLot build(){
         return new ParkingLot(lotLocation, supportedVehicles);
        }

    }


}