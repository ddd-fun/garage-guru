package org.home.gg.domain;


public class AvailableLots {

    private final int size;

    public AvailableLots(int size) {
        this.size = size;
    }

    public boolean isEmpty(){
      return size < 1;
    }


    public int totalLots(){
      return this.size;
    }


}
