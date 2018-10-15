package com.doy.reservation.exception;

public class ReservationDuplicateException extends RuntimeException {
    public ReservationDuplicateException(String message) {
        super(message);
    }
}
