package org.home.gg.domain.garage;


import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;


public class NumberOfFreeLotsTest {


    @Test
    public void whenThereIsAvailableThenIsNotEmpty(){
        Map aMap = new HashMap();
        aMap.put("CAR", 21L);

        NumberOfFreeLots numberOfFreeLots = new NumberOfFreeLots(aMap);

        assertFalse(numberOfFreeLots.isNoLots());
    }


    @Test
    public void totalIsCalculated(){
        Map aMap = new HashMap();
        aMap.put("CAR", 21L);
        aMap.put("MOTORCYCLE", 10L);

        NumberOfFreeLots numberOfFreeLots = new NumberOfFreeLots(aMap);

        assertEquals(21L + 10L, numberOfFreeLots.totalLots());

    }


}