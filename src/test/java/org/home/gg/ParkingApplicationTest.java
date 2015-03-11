package org.home.gg;

import org.home.gg.application.ParkingApplication;
import org.home.gg.application.ParkingLotNotFoundException;
import org.home.gg.application.VehicleIsAlreadyParkedException;
import org.home.gg.domain.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParkingApplicationTest {


    @Test
    public void whenThereIsAvailablePlaceThenVehicleCouldBeParked() throws Exception {

        ParkingApplication application =
                aParkingAppWith(aGarage().with(aParkingLot().with(new LotLocation("A", "1"))
                                                            .with(vehicleSpecSuitableForAnyTypes())));

        VehicleId vehicleId = new VehicleId("AB465KL");

        havingCarParked(vehicleId, application);

        assertTrue("no available lots", application.getAvailableLots().isEmpty());
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


    private void havingCarParked(VehicleId id, ParkingApplication application){
        LotLocation lotLocation  = application.tryToPark(id, VehicleType.CAR);
        assertEquals("vehicle could be found by id", lotLocation, application.findVehicleBy(id).get());
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