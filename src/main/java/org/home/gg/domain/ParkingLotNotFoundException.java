package org.home.gg.domain;


public class ParkingLotNotFoundException extends RuntimeException {

    public ParkingLotNotFoundException(String message) {
        super(message);
    }
}
