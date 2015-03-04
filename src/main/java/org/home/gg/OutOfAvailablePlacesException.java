package org.home.gg;


public class OutOfAvailablePlacesException extends RuntimeException {

    public OutOfAvailablePlacesException(String message) {
        super(message);
    }
}
