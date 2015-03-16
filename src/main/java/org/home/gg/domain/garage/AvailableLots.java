package org.home.gg.domain.garage;


public class AvailableLots {

    private final long size;

    public AvailableLots(long size) {
        this.size = size;
    }

    public boolean isEmpty(){
      return size < 1;
    }


    public long totalLots(){
      return this.size;
    }


}
