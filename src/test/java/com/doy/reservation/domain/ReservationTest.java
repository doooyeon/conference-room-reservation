package com.doy.reservation.domain;

import com.doy.reservation.dto.ReservationDTO;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ReservationTest {

    @Test
    public void isDuplicateTest() {
        Reservation reservation = new Reservation("A", LocalDate.of(2018, 1, 1), LocalTime.of(12, 0), LocalTime.of(14, 0));
        ReservationDTO reservationDTO = new ReservationDTO("A", LocalDate.of(2018, 1, 1), LocalTime.of(13, 30), LocalTime.of(14, 30));
        assertThat(reservation.isDuplicate(reservationDTO.getStartTime(), reservationDTO.getEndTime())).isEqualTo(true);

        reservationDTO.setStartTime(LocalTime.of(11, 0));
        reservationDTO.setEndTime(LocalTime.of(13, 0));
        assertThat(reservation.isDuplicate(reservationDTO.getStartTime(), reservationDTO.getEndTime())).isEqualTo(true);

        reservationDTO.setStartTime(LocalTime.of(14, 0));
        reservationDTO.setEndTime(LocalTime.of(16, 0));
        assertThat(reservation.isDuplicate(reservationDTO.getStartTime(), reservationDTO.getEndTime())).isEqualTo(false);

        reservationDTO.setStartTime(LocalTime.of(9, 0));
        reservationDTO.setEndTime(LocalTime.of(12, 0));
        assertThat(reservation.isDuplicate(reservationDTO.getStartTime(), reservationDTO.getEndTime())).isEqualTo(false);
    }
}