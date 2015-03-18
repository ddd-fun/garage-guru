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

        //TODO for test maintainability relay on valid value generators
        VehicleId vehicleId = new VehicleId("AB465KL");

        havingCarParked(vehicleId, application);

        assertTrue("no available lots", application.getAvailableLots().isNoLots());
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
    public void whenParkingLotIsCleanedThenItIsFree() throws Exception {

        ParkingApplication application =
                aParkingAppWith(aGarage().with(aParkingLot().with(new LotLocation("A", "1"))
                                                            .with(vehicleSpecSuitableForAnyTypes())));

        VehicleId vehicleId = new VehicleId("AB456H");

        havingCarParked(vehicleId, application);

        NumberOfFreeLots beforeRealized = application.getAvailableLots();

        application.cleanParkingLot(vehicleId);

        assertFalse(application.findParkedVehicleBy(vehicleId).isPresent());

        NumberOfFreeLots afterRealised = application.getAvailableLots();

        //TODO introduce less than
        assertTrue(beforeRealized.totalLots() < afterRealised.totalLots());

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