package org.home.gg;

import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleGarageTest {

    @Test( expected = IllegalArgumentException.class)
    public void zeroIsNotAccepted(){
      new SimpleGarage(0);
    }

    @Test
    public void whenThereIsAFreePlaceThenVehicleCouldBeParked(){
       SimpleGarage garage = new SimpleGarage(1);
       assertTrue(garage.couldPark());
       garage.park(VehicleIdDataBuilder.genVehicleId());
    }


    @Test(expected = OutOfAvailablePlacesException.class)
    public void whenThereIsNoFreePlaceThenVehicleCouldNotBeParked(){
        SimpleGarage garage = new SimpleGarage(1);
        garage.park(VehicleIdDataBuilder.genVehicleId());
        assertFalse(garage.couldPark());
        garage.park(VehicleIdDataBuilder.genVehicleId());
    }





}