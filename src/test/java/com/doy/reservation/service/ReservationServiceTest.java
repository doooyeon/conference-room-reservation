package com.doy.reservation.service;

import com.doy.reservation.domain.Reservation;
import com.doy.reservation.dto.ReservationDTO;
import com.doy.reservation.dto.ReservationResponseDTO;
import com.doy.reservation.exception.ReservationDuplicateException;
import com.doy.reservation.repository.ReservationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.support.MessageSourceAccessor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceTest {
    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private MessageSourceAccessor msa;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    public void saveReservationSuccessTest() {
        ReservationDTO reservationDTO = ReservationDTO.defaultReservationBDto();
        reservationService.createReservation(reservationDTO);

        List<Reservation> reservationList = reservationService.convertToReservationList(reservationDTO);
        verify(reservationRepository).saveAll(reservationList);
    }

    @Test(expected = ReservationDuplicateException.class)
    public void saveReservationFailTest() {
        ReservationDTO reservationDTO = ReservationDTO.defaultReservationADto();
        List<Reservation> reservations = reservationService.convertToReservationList(reservationDTO);

        when(reservationRepository.findByDateInAndRoomName(reservationService.getReservationDateList(reservationDTO), reservationDTO.getRoomName())).thenReturn(reservations);
        reservationService.createReservation(reservationDTO);
    }

    @Test
    public void reservationDuplicateTest() {
        ReservationDTO reservationDTO1 = ReservationDTO.defaultReservationADto();
        ReservationDTO reservationDTO2 = ReservationDTO.defaultReservationBDto();
        List<Reservation> reservations = reservationService.convertToReservationList(reservationDTO1);

        when(reservationRepository.findByDateInAndRoomName(reservationService.getReservationDateList(reservationDTO2), reservationDTO2.getRoomName())).thenReturn(reservations);
        assertThat(reservationService.checkDuplicate(reservationDTO2)).isFalse();
    }

    @Test
    public void reservationNotDuplicateTest() {
        ReservationDTO reservationDTO = ReservationDTO.defaultReservationADto();
        List<Reservation> reservations = reservationService.convertToReservationList(reservationDTO);

        when(reservationRepository.findByDateInAndRoomName(reservationService.getReservationDateList(reservationDTO), reservationDTO.getRoomName())).thenReturn(reservations);
        assertThat(reservationService.checkDuplicate(reservationDTO)).isTrue();
    }

    @Test
    public void getReservationListByDateTest() {
        ReservationDTO reservationDTO = ReservationDTO.defaultReservationADto();
        List<Reservation> reservations = reservationService.convertToReservationList(reservationDTO);

        when(reservationRepository.findByDate(reservationDTO.getDate())).thenReturn(reservations);

        List<ReservationResponseDTO> reservationDTOList = reservationService.convertToReservationDTOList(reservations);
        assertThat(reservationService.getReservationListByDate(reservationDTO.getDate())).isEqualTo(reservationDTOList);
    }
}