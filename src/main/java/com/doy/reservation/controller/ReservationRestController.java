package com.doy.reservation.controller;

import com.doy.reservation.dto.ReservationDTO;
import com.doy.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/reservations")
public class ReservationRestController {
    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity reserve(@RequestBody ReservationDTO reservationDTO) {
        reservationService.save(reservationDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }
}
