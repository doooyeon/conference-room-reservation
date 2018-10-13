package com.doy.reservation.domain;

import com.doy.reservation.dto.ReservationDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String roomName;

    @Column
    private String reservedName;

    @Column
    private LocalDate date;

    @Column
    private LocalTime startTime;

    @Column
    private LocalTime endTime;

    public Reservation(String roomName, String reservedName, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.roomName = roomName;
        this.reservedName = reservedName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Reservation(String roomName, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this(roomName, "doy", date, startTime, endTime);
    }

    public boolean isDuplicate(ReservationDTO reservationDTO) {
        if (startTime.isAfter(reservationDTO.getEndTime()) || startTime.compareTo(reservationDTO.getEndTime()) == 0)
            return false;
        if (endTime.isBefore(reservationDTO.getStartTime()) || endTime.compareTo(reservationDTO.getStartTime()) == 0)
            return false;
        return true;
    }
}
