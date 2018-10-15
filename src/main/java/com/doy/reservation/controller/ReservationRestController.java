package com.doy.reservation.controller;

import com.doy.reservation.dto.ReservationDTO;
import com.doy.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;


@RestController
@RequestMapping("/reservations")
public class ReservationRestController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity reserve(@Valid @RequestBody ReservationDTO reservationDTO) {
        reservationService.save(reservationDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/{date}")
    public ResponseEntity getReservation(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        return ResponseEntity.ok().body(reservationService.getReservationDTOsByDate(date));
    }
}
