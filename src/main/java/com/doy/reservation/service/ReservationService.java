package com.doy.reservation.service;

import com.doy.reservation.dto.ReservationDTO;
import com.doy.reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public void save(ReservationDTO reservationDTO) {
        for (int i = 0; i <= reservationDTO.getNumOfRecursion(); i++) {
            reservationDTO.setDate(reservationDTO.getDate().plusWeeks(i));
            reservationRepository.save(reservationDTO.toEntity());
        }
    }
}
