package org.home.gg.domain;


public class OutOfAvailablePlacesException extends RuntimeException {

    public OutOfAvailablePlacesException(String message) {
        super(message);
    }
}
