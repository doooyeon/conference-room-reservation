package com.doy.reservation.service;

import com.doy.reservation.domain.Reservation;
import com.doy.reservation.dto.ReservationDTO;
import com.doy.reservation.dto.ReservationResponseDTO;
import com.doy.reservation.exception.ReservationDuplicateException;
import com.doy.reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public void save(ReservationDTO reservationDTO) {
        checkDuplicate(reservationDTO);

        for (int i = 0; i <= reservationDTO.getNumOfRecursion(); i++) {
            reservationDTO.setDate(reservationDTO.getDate().plusWeeks(i));
            reservationRepository.save(reservationDTO.toEntity());
        }
    }

    public void checkDuplicate(ReservationDTO reservationDTO) {
        for (int i = 0; i <= reservationDTO.getNumOfRecursion(); i++) {
            ReservationDTO dto = new ReservationDTO(reservationDTO.getRoomName(), reservationDTO.getReservedName(), reservationDTO.getDate().plusWeeks(i), reservationDTO.getStartTime(), reservationDTO.getEndTime(), reservationDTO.getNumOfRecursion());
            List<Reservation> reservations = reservationRepository.findByDateAndRoomName(reservationDTO.getDate(), reservationDTO.getRoomName());

            for (Reservation reservation : reservations) {
                if (reservation.isDuplicate(dto)) {
                    throw new ReservationDuplicateException("이미 예약된 시간입니다.");
                }
            }
        }
    }

    public List<ReservationResponseDTO> getReservationDTOsByDate(LocalDate date) {
        List<Reservation> reservations = reservationRepository.findByDate(date);
        List<ReservationResponseDTO> reservationDTOs = new ArrayList<>();
        for (Reservation r : reservations) {
            reservationDTOs.add(new ReservationResponseDTO(r.getId(), r.getRoomName(), r.getReservedName(), r.getStartTime(), r.getEndTime()));
        }
        return reservationDTOs;
    }
}
