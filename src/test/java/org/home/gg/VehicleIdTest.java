package org.home.gg;

import org.junit.Test;

import static org.junit.Assert.*;

public class VehicleIdTest {

    public static final String[] VALID_IDS = new String[]{"ab1254", "cd789"};
    public static final String[] NOT_VALID_IDS = new String[]{""};


    @Test(expected = IllegalArgumentException.class)
    public void nullIsNotAccepted(){
       new VehicleId(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void notValidIdIsNotAccepted(){
       new VehicleId(NOT_VALID_IDS[0]);
    }


    @Test
    public void twoIdsAreEqual(){
        VehicleId one = new VehicleId(new String(VALID_IDS[0]));
        VehicleId other = new VehicleId(new String(VALID_IDS[0]));
        assertTrue(one.equals(other));
        assertTrue(other.equals(one));
    }


    @Test
    public void twoDiffIdsAreNotEqual(){
        VehicleId one = new VehicleId(new String(VALID_IDS[0]));
        VehicleId other = new VehicleId(new String(VALID_IDS[1]));
        assertFalse(one.equals(other));
    }


    @Test
    public void getIdReturnsCorrectString(){
        String id =  VALID_IDS[0];
        VehicleId one = new VehicleId(id);
        assertEquals(id, one.getId());
    }


}