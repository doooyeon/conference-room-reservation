package com.doy.reservation.controller;

import com.doy.reservation.dto.ReservationDTO;
import com.doy.reservation.dto.ReservationResponseDTO;
import com.doy.reservation.exception.ExceptionErrorResponse;
import com.doy.reservation.validation.ValidationErrorsResponse;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ReservationRestAcceptanceTest extends AcceptanceTest {
    @Autowired
    private MessageSourceAccessor msa;

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
    public void reserveFailWithEmptyReservedNameTest() {
        // when
        ReservationDTO reservation = ReservationDTO.defaultReservationADto();
        reservation.setReservedName("");
        ResponseEntity<ValidationErrorsResponse> response = template().postForEntity(RESERVE_URL, reservation, ValidationErrorsResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody().getErrors().size()).isEqualTo(2);
    }

    @Test
    public void reserveFailTest() {
        // when
        ReservationDTO reservation = ReservationDTO.defaultReservationADto();
        template().postForEntity(RESERVE_URL, reservation, Void.class);
        ResponseEntity<ExceptionErrorResponse> response = template().postForEntity(RESERVE_URL, reservation, ExceptionErrorResponse.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(response.getBody().getErrorMessage()).isEqualTo(msa.getMessage("reservation.duplicate.message"));
    }

    @Test
    public void getReservationTest() {
        // when
        ReservationDTO reservation = ReservationDTO.defaultReservationADto();
        template().postForEntity(RESERVE_URL, reservation, Void.class);
        ResponseEntity<List<ReservationResponseDTO>> response =
                getForEntityWithParameterized(RESERVE_URL + "/" + reservation.getDate(), null, new ParameterizedTypeReference<List<ReservationResponseDTO>>() {
                });

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().size()).isEqualTo(1);
        assertThat(response.getBody().get(0).getRoomName()).isEqualTo(reservation.getRoomName());
        assertThat(response.getBody().get(0).getReservedName()).isEqualTo(reservation.getReservedName());
        assertThat(response.getBody().get(0).getStartTime()).isEqualTo(reservation.getStartTime());
        assertThat(response.getBody().get(0).getEndTime()).isEqualTo(reservation.getEndTime());
    }

    @Test
    public void getReservationWithRecursionTest() {
        // when
        ReservationDTO reservation = ReservationDTO.defaultReservationBDto();
        reservation.setDate(reservation.getDate().plusDays(2));
        template().postForEntity(RESERVE_URL, reservation, Void.class);
        ResponseEntity<List<ReservationResponseDTO>> response1 =
                getForEntityWithParameterized(RESERVE_URL + "/" + reservation.getDate(), null, new ParameterizedTypeReference<List<ReservationResponseDTO>>() {
                });
        ResponseEntity<List<ReservationResponseDTO>> response2 =
                getForEntityWithParameterized(RESERVE_URL + "/" + reservation.getDate().plusWeeks(1), null, new ParameterizedTypeReference<List<ReservationResponseDTO>>() {
                });

        // then
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response1.getBody().size()).isEqualTo(1);
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response2.getBody().size()).isEqualTo(1);
    }
}