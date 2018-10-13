package com.doy.reservation.controller;

import com.doy.reservation.dto.ReservationDTO;
import com.doy.reservation.exception.ErrorDetails;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class ReservationRestAcceptanceTest extends AcceptanceTest{
    private static final String RESERVE_URL = "/reservations";

    @Test
    public void reserveSuccessTest() {
        // when
        ReservationDTO reservation = ReservationDTO.defaultReservationADto();
        ResponseEntity response = template().postForEntity(RESERVE_URL, reservation, Void.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void reserveSuccessWithRecursionTest() {
        // when
        ReservationDTO reservation = ReservationDTO.defaultReservationBDto();
        ResponseEntity response = template().postForEntity(RESERVE_URL, reservation, Void.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void reserveFailTest() {
        // when
        ReservationDTO reservation = ReservationDTO.defaultReservationADto();
        template().postForEntity(RESERVE_URL, reservation, Void.class);
        ResponseEntity<ErrorDetails> response = template().postForEntity(RESERVE_URL, reservation, ErrorDetails.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody().getMessage()).isEqualTo("이미 예약된 시간입니다.");
    }
}