package com.doy.reservation.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ExceptionControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @ExceptionHandler(ReservationDuplicateException.class)
    public ResponseEntity reservationDuplicated(ReservationDuplicateException exception) {
        log.debug("ReservationDuplicateException is happened!");
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDetails);
    }
}
