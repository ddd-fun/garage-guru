package org.home.gg;

import org.home.gg.application.ParkingApplication;
import org.home.gg.domain.*;
import org.junit.Test;

import java.util.EnumSet;

import static org.junit.Assert.*;

public class ParkingApplicationTest {



    @Test
    public void whenThereIsAvailablePlaceThenVehicleCouldBeParked() throws Exception {

        VehicleId vehicleId = new VehicleId("AB465KL");

        ParkingApplication application = createParkingApplicationWith(
                        anOneLevelGarage("A")
                           .withParkingLot("1", suitableForCarOnly()));

        havingCarParked(vehicleId, application);

        assertTrue("no available lots", application.getAvailableLots().isEmpty());
    }


    @Test(expected = VehicleIsAlreadyParkedException.class)
    public void whenTheVehicleIsAlreadyParkedThenSecondParkingAttemptCausesException() throws Exception {

        String vehicleId = "AB456H";

        ParkingApplication application = createParkingApplicationWith(
                anOneLevelGarage("A")
                        .withParkingLot("1", suitableForCarOnly()));

        havingCarParked(new VehicleId(vehicleId), application);

        application.tryToPark(new VehicleId(vehicleId), ParkingLotSpecBuilder.forCar());
    }



    @Test(expected = OutOfAvailablePlacesException.class)
    public void whenThereIsNoAvailableLotThenParkingAttemptCausesException() throws Exception {

        ParkingApplication application = createParkingApplicationWith(
                anOneLevelGarage("A")
                        .withParkingLot("1", suitableForCarOnly()));

        havingCarParked(new VehicleId("AB456H"), application);

        application.tryToPark(new VehicleId("VB356G"), ParkingLotSpecBuilder.forCar());
    }


    private void havingCarParked(VehicleId id, ParkingApplication application){
        LotLocation lotLocation  = application.tryToPark(id, ParkingLotSpecBuilder.forCar());
        assertEquals("vehicle could be found by id", lotLocation, application.findVehicleBy(id).get());
    }


    private ParkingApplication createParkingApplicationWith(GarageBuilder garageBuilder){
        return new ParkingApplication(garageBuilder.buildGarage());
    }


    private EnumSet<VehicleType> suitableForCarOnly() {
      return EnumSet.of(VehicleType.CAR);
    }



    private GarageBuilder anOneLevelGarage(String level){
       return new GarageBuilder().withLevel(level);
    }



}