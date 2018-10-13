package com.doy.reservation.repository;

import com.doy.reservation.domain.Reservation;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    List<Reservation> findByDateAndRoomName(LocalDate date, String roomName);
}
