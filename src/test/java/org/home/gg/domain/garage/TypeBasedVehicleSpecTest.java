package org.home.gg.domain.garage;

import org.home.gg.domain.vehicle.VehicleType;
import org.junit.Test;

import java.util.EnumSet;

import static org.junit.Assert.*;

public class TypeBasedVehicleSpecTest {



    @Test
    public void whenMotorbikeIsDefinedThenOnlyMotorbikeIsAccepted(){

        VehicleSpec spec = new TypeBasedVehicleSpec(EnumSet.of(VehicleType.MOTORBIKE));

        assertTrue(spec.isSatisfiedBy(VehicleType.MOTORBIKE));

        assertFalse("other vehicle should not be accepted", spec.isSatisfiedBy(VehicleType.CAR));

    }

    @Test
    public void whenCarIsDefinedThenOnlyCarIsAccepted(){

        VehicleSpec spec = new TypeBasedVehicleSpec(EnumSet.of(VehicleType.CAR));

        assertTrue(spec.isSatisfiedBy(VehicleType.CAR));

        assertFalse("other vehicle type should not be accepted", spec.isSatisfiedBy(VehicleType.MOTORBIKE));
    }



    @Test
    public void whenTwoTypesAreDefinedThenBothAreAccepted(){

        VehicleSpec spec = new TypeBasedVehicleSpec(EnumSet.of(VehicleType.CAR,VehicleType.MOTORBIKE));

        assertTrue(spec.isSatisfiedBy(VehicleType.MOTORBIKE));
        assertTrue(spec.isSatisfiedBy(VehicleType.CAR));
    }

    @Test
    public void toStringShouldContainsAllTypes(){

        VehicleSpec spec = new TypeBasedVehicleSpec(EnumSet.of(VehicleType.CAR, VehicleType.MOTORBIKE));

        String asString = spec.toString();

        assertTrue("contains CAR type", asString.contains(VehicleType.CAR.name()));

        assertTrue("contains MOTORBIKE type", asString.contains(VehicleType.MOTORBIKE.name()));
    }



}