package org.home.gg;

import org.junit.Test;


import java.util.EnumSet;

import static org.junit.Assert.*;

public class ParkingApplicationTest {


    @Test
    public void whenThereIsAvailablePlaceThenTicketIsReturned() throws Exception {

        BigGarageBuilder garageBuilder = new BigGarageBuilder();

        garageBuilder.addLevel("A")
                      .addPlace("1", new TypesBasedVehicleSpec(EnumSet.of(VehicleType.CAR)));

        ParkingApplication application = new ParkingApplication(garageBuilder.buildGarage());

        ParkingTicket ticket = application.tryToPark(new VehicleId("AB456H"), VehicleType.CAR);

        System.out.println(ticket.toString());

        assertNotNull(ticket);
    }

    @Test(expected = OutOfAvailablePlacesException.class)
    public void whenThereIsNoAvailablePlaceThenExceptionIsThrown() throws Exception {

        BigGarageBuilder garageBuilder = new BigGarageBuilder();

        garageBuilder.addLevel("A")
                .addPlace("1", new TypesBasedVehicleSpec(EnumSet.of(VehicleType.CAR)));

        ParkingApplication application = new ParkingApplication(garageBuilder.buildGarage());

        application.tryToPark(new VehicleId("AB456H"), VehicleType.CAR);

        application.tryToPark(new VehicleId("VB356G"), VehicleType.CAR);
    }


}