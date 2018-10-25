package com.doy.reservation.domain;

import com.doy.reservation.dto.ReservationDTO;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ReservationTest {
    private static final String roomName = "A";
    private static final LocalDate date = LocalDate.of(2018, 1, 1);
    private Reservation reservation;

    @Before
    public void setUp() throws Exception {
        reservation = new Reservation(roomName, date, LocalTime.of(12, 0), LocalTime.of(14, 0));
    }

    @Test
    public void isDuplicateTest() {
        ReservationDTO reservationDTO = new ReservationDTO(roomName, date, LocalTime.of(13, 30), LocalTime.of(14, 30));
        assertThat(reservation.isDuplicate(reservationDTO.getStartTime(), reservationDTO.getEndTime())).isTrue();
    }

    @Test
    public void isDuplicateTest2() {
        ReservationDTO reservationDTO = new ReservationDTO(roomName, date, LocalTime.of(11, 0), LocalTime.of(13, 0));
        assertThat(reservation.isDuplicate(reservationDTO.getStartTime(), reservationDTO.getEndTime())).isTrue();
    }

    @Test
    public void isDuplicateTest3() {
        ReservationDTO reservationDTO = new ReservationDTO(roomName, date, LocalTime.of(12, 30), LocalTime.of(13, 30));
        assertThat(reservation.isDuplicate(reservationDTO.getStartTime(), reservationDTO.getEndTime())).isTrue();
    }

    @Test
    public void isNotDuplicateTest() {
        ReservationDTO reservationDTO = new ReservationDTO(roomName, date, LocalTime.of(14, 0), LocalTime.of(16, 0));
        assertThat(reservation.isDuplicate(reservationDTO.getStartTime(), reservationDTO.getEndTime())).isFalse();
    }

    @Test
    public void isNotDuplicateTest2() {
        ReservationDTO reservationDTO = new ReservationDTO(roomName, date, LocalTime.of(9, 0), LocalTime.of(12, 0));
        assertThat(reservation.isDuplicate(reservationDTO.getStartTime(), reservationDTO.getEndTime())).isFalse();
    }
}