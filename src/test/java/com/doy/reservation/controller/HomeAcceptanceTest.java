package com.doy.reservation.controller;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class HomeAcceptanceTest extends AcceptanceTest {

    @Test
    public void doReserve() {
        ResponseEntity<String> response = template().getForEntity("/", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Conference Room Reservation");
    }

    @Test
    public void getReserve() {
        ResponseEntity<String> response = template().getForEntity("/reservation", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("Conference Room Reservation Status");
    }
}