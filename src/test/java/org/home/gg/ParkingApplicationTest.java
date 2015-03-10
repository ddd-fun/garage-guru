package org.home.gg;

import org.home.gg.application.ParkingApplication;
import org.home.gg.domain.*;
import org.junit.Test;

import java.util.EnumSet;

import static org.junit.Assert.*;

public class ParkingApplicationTest {


    private ParkingApplication createParkingApplicationWith(GarageBuilder garageBuilder){
      return new ParkingApplication(garageBuilder.buildGarage());
    }


    @Test
    public void whenThereIsAvailablePlaceThenParkingLotLocationIsReturned() throws Exception {

        VehicleId vehicleId = new VehicleId("AB465KL");

        ParkingApplication application = createParkingApplicationWith(
                        anOneLevelGarage("A")
                           .withParkingLot("1", suitableForCarOnly()));

        LotLocation lotLocation = application.tryToPark(vehicleId, ParkingLotSpecBuilder.forCar());

        assertEquals(lotLocation, application.findVehicleBy(vehicleId).get());

        assertTrue(application.getAvailableLots().isEmpty());
    }


    @Test(expected = VehicleIsAlreadyParkedException.class)
    public void whenTheVehicleIsAlreadyParkedThenExceptionIsThrown() throws Exception {

        String vehicleId = "AB456H";

        ParkingApplication application = createParkingApplicationWith(
                anOneLevelGarage("A")
                        .withParkingLot("1", suitableForCarOnly()));

        application.tryToPark(new VehicleId(vehicleId), ParkingLotSpecBuilder.forCar());
        application.tryToPark(new VehicleId(vehicleId), ParkingLotSpecBuilder.forCar());
    }



    @Test(expected = OutOfAvailablePlacesException.class)
    public void whenThereIsNoAvailablePlaceThenExceptionIsThrown() throws Exception {

        ParkingApplication application = createParkingApplicationWith(
                anOneLevelGarage("A")
                        .withParkingLot("1", suitableForCarOnly()));

        application.tryToPark(new VehicleId("AB456H"), ParkingLotSpecBuilder.forCar());
        application.tryToPark(new VehicleId("VB356G"), ParkingLotSpecBuilder.forCar());
    }


    private EnumSet<VehicleType> suitableForCarOnly() {
      return EnumSet.of(VehicleType.CAR);
    }



    private GarageBuilder anOneLevelGarage(String level){
       return new GarageBuilder().withLevel(level);
    }



}