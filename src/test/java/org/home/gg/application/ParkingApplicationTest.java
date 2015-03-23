package org.home.gg.application;

import org.home.gg.domain.garage.*;
import org.home.gg.domain.vehicle.VehicleId;
import org.home.gg.domain.vehicle.VehicleType;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParkingApplicationTest {


    @Test
    public void whenThereIsAvailablePlaceThenVehicleCouldBeParked() throws Exception {

        ParkingApplication application =
                aParkingAppWith(aGarage().with(aParkingLot().with(new LotLocation("A", "1"))
                                                            .with(vehicleSpecSuitableForAnyTypes())));

        VehicleId vehicleId = new VehicleId("AB465KL");

        LotLocation lotLocation = application.tryToPark(vehicleId, VehicleType.CAR);

        assertTrue("vehicle is found", application.findParkedVehicleBy(vehicleId).isPresent());

        assertEquals("location is correct", lotLocation, application.findParkedVehicleBy(vehicleId).get());
    }

    @Test
    public void whenVehicleIsParkedThenCorrectNumberOfFreeLotsIsReturned() throws Exception {

        ParkingApplication application =
                aParkingAppWith(aGarage().with(aParkingLot().with(new LotLocation("A", "1"))
                                                            .with((vehicleSpecSuitableForAnyTypes()))));

        NumberOfFreeLots beforeCarWasParked = application.getAvailableLots();

        havingCarParked(new VehicleId("A123"), application);

        NumberOfFreeLots afterCarWasParked = application.getAvailableLots();

        assertTrue("number of free lots is decreased", beforeCarWasParked.totalLots() > afterCarWasParked.totalLots());

        assertEquals("number of free lots is decreased by 1", 1, beforeCarWasParked.totalLots() - afterCarWasParked.totalLots());
    }


    @Test(expected = VehicleIsAlreadyParkedException.class)
    public void whenTheVehicleIsAlreadyParkedThenSecondParkingAttemptCausesException() throws Exception {

        ParkingApplication application =
                aParkingAppWith(aGarage().with(aParkingLot().with(new LotLocation("A", "1"))
                                                            .with(vehicleSpecSuitableForAnyTypes())));


        String vehicleId = "AB456H";

        havingCarParked(new VehicleId(vehicleId), application);

        application.tryToPark(new VehicleId(vehicleId), VehicleType.CAR);
    }



    @Test(expected = ParkingLotNotFoundException.class)
    public void whenThereIsNoAvailableLotThenParkingAttemptCausesException() throws Exception {

        ParkingApplication application =
                aParkingAppWith(aGarage().with(aParkingLot().with(new LotLocation("A", "1"))
                                                            .with(vehicleSpecSuitableForAnyTypes())));

        havingCarParked(new VehicleId("AB456H"), application);

        application.tryToPark(new VehicleId("VB356G"), VehicleType.CAR);
    }


    @Test(expected = ParkingLotNotFoundException.class)
    public void whenThereIsNoSuitableParkingLotThenParkingAttemptCausesException() throws Exception {

        ParkingApplication application =
                aParkingAppWith(aGarage().with(aParkingLot().with(new LotLocation("A", "1"))
                                                            .with((vehicleSpecRejectingAnyTypes()))));

        application.tryToPark(new VehicleId("VB356G"), VehicleType.CAR);
    }


    @Test
    public void whenParkingLotIsCleanedThenVehicleIsNotParkedAnymore() throws Exception {

        ParkingApplication application =
                aParkingAppWith(aGarage().with(aParkingLot().with(new LotLocation("A", "1"))
                                                            .with(vehicleSpecSuitableForAnyTypes())));

        VehicleId vehicleId = new VehicleId("AB456H");

        havingCarParked(vehicleId, application);

        application.cleanParkingLot(vehicleId);

        assertFalse(application.findParkedVehicleBy(vehicleId).isPresent());

    }



    private void havingCarParked(VehicleId id, ParkingApplication application){
        LotLocation lotLocation  = application.tryToPark(id, VehicleType.CAR);
        assertEquals("vehicle could be found by id", lotLocation, application.findParkedVehicleBy(id).get());
    }


    private ParkingApplication aParkingAppWith(GarageBuilder garageBuilder){
        return new ParkingApplication(garageBuilder.buildGarage());
    }


    private VehicleSpec vehicleSpecSuitableForAnyTypes() {
      return VehicleSpecBuilder.anyVehicleSpec();
    }

    private VehicleSpec vehicleSpecRejectingAnyTypes() {
        return VehicleSpecBuilder.neitherVehicleSpec();
    }


    private GarageBuilder aGarage(){
       return new GarageBuilder();
    }


    private ParkingLotBuilder aParkingLot(){
      return new ParkingLotBuilder();
    }


}