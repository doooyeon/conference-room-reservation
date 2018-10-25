package com.doy.reservation.service;

import com.doy.reservation.domain.Reservation;
import com.doy.reservation.dto.ReservationDTO;
import com.doy.reservation.dto.ReservationResponseDTO;
import com.doy.reservation.exception.ReservationDuplicateException;
import com.doy.reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private MessageSourceAccessor msa;

    synchronized public void createReservation(ReservationDTO reservationDTO) {
        if (checkDuplicate(reservationDTO)) {
            throw new ReservationDuplicateException(msa.getMessage("reservation.duplicate.message"));
        }
        saveReservationList(convertToReservationList(reservationDTO));
    }

    public boolean checkDuplicate(ReservationDTO reservationDTO) {
        List<Reservation> reservations = reservationRepository.findByDateInAndRoomName(getReservationDateList(reservationDTO), reservationDTO.getRoomName());
        for (Reservation reservation : reservations) {
            if (reservation.isDuplicate(reservationDTO.getStartTime(), reservationDTO.getEndTime())) {
                return true;
            }
        }
        return false;
    }

    public List<LocalDate> getReservationDateList(ReservationDTO reservationDTO) {
        List<LocalDate> dateList = new ArrayList<>();
        for (int i = 0; i < reservationDTO.getNumOfRecursion(); i++) {
            dateList.add(reservationDTO.getDate().plusWeeks(i));
        }
        return dateList;
    }

    public List<Reservation> convertToReservationList(ReservationDTO reservationDTO) {
        List<Reservation> reservationList = new ArrayList<>();
        for (int i = 0; i < reservationDTO.getNumOfRecursion(); i++) {
            reservationList.add(reservationDTO.toEntity(reservationDTO.getDate().plusWeeks(i)));
        }
        return reservationList;
    }

    @Transactional
    public void saveReservationList(List<Reservation> reservationList) {
        reservationRepository.saveAll(reservationList);
    }

    public List<ReservationResponseDTO> getReservationListByDate(LocalDate date) {
        List<Reservation> reservationList = reservationRepository.findByDate(date);
        return convertToReservationDTOList(reservationList);
    }

    public List<ReservationResponseDTO> convertToReservationDTOList(List<Reservation> reservations) {
        List<ReservationResponseDTO> reservationDTOList = new ArrayList<>();
        for (Reservation r : reservations) {
            reservationDTOList.add(new ReservationResponseDTO(r.getId(), r.getRoomName(), r.getReservedName(), r.getStartTime(), r.getEndTime()));
        }
        return reservationDTOList;
    }
}
