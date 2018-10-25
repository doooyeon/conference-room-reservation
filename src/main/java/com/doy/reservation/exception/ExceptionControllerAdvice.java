package com.doy.reservation.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Date;

@RestControllerAdvice
public class ExceptionControllerAdvice {
    private static final Logger log = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @Autowired
    private MessageSourceAccessor msa;

    @ExceptionHandler(ReservationDuplicateException.class)
    public ResponseEntity reservationDuplicated(ReservationDuplicateException exception) {
        log.debug("ReservationDuplicateException is happened!");
        ExceptionErrorResponse exceptionErrorResponse = new ExceptionErrorResponse(new Date(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exceptionErrorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity methodArgumentTypeMismatch() {
        log.debug("MethodArgumentTypeMismatchException is happened!");
        ExceptionErrorResponse exceptionErrorResponse = new ExceptionErrorResponse(new Date(), msa.getMessage("date.type.mismatch.message"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionErrorResponse);
    }
}
