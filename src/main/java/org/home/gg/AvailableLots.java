package org.home.gg;


import java.util.HashMap;
import java.util.Map;

public class AvailableLots {

    private final int size;

    public AvailableLots(int size) {
        this.size = size;
    }

    public boolean isEmpty(){
      return size < 1;
    }



}
