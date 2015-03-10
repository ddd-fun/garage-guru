package org.home.gg;


public class AvailableLots {

    private final int size;

    public AvailableLots(int size) {
        this.size = size;
    }

    public boolean isEmpty(){
      return size < 1;
    }



}
