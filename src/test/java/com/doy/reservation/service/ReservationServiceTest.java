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
        reservationService.save(reservationDTO);

        List<Reservation> reservationList = reservationService.convertToReservationList(reservationDTO);
        verify(reservationRepository).saveAll(reservationList);
    }


    @Test(expected = ReservationDuplicateException.class)
    public void saveReservationFailTest() {
        ReservationDTO reservationDTO = ReservationDTO.defaultReservationADto();
        List<LocalDate> dateList = new ArrayList<>();
        for (int i = 0; i < reservationDTO.getNumOfRecursion(); i++) {
            dateList.add(reservationDTO.getDate().plusWeeks(i));
        }

        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservationDTO.toEntity());

        when(reservationRepository.findByDateInAndRoomName(dateList, reservationDTO.getRoomName())).thenReturn(reservations);
        reservationService.save(reservationDTO);
    }

    @Test
    public void getReservationDTOsByDate() {
        ReservationDTO reservationDTO1 = ReservationDTO.defaultReservationADto();
        ReservationDTO reservationDTO2 = ReservationDTO.defaultReservationBDto();

        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservationDTO1.toEntity());
        reservations.add(reservationDTO2.toEntity());

        when(reservationRepository.findByDate(reservationDTO1.getDate())).thenReturn(reservations);

        List<ReservationResponseDTO> reservationDTOs = new ArrayList<>();
        for (Reservation r : reservations) {
            reservationDTOs.add(new ReservationResponseDTO(r.getId(), r.getRoomName(), r.getReservedName(), r.getStartTime(), r.getEndTime()));
        }
        assertThat(reservationService.getReservationDTOsByDate(reservationDTO1.getDate())).isEqualTo(reservationDTOs);
    }
}