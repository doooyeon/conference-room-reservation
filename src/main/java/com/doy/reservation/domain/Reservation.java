package com.doy.reservation.domain;

import com.doy.reservation.dto.ReservationDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

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

    public boolean isDuplicate(LocalTime startTime, LocalTime endTime) {
        if (this.startTime.isAfter(endTime) || this.startTime.compareTo(endTime) == 0)
            return false;
        if (this.endTime.isBefore(startTime) || this.endTime.compareTo(startTime) == 0)
            return false;
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(roomName, that.roomName) &&
                Objects.equals(reservedName, that.reservedName) &&
                Objects.equals(date, that.date) &&
                Objects.equals(startTime, that.startTime) &&
                Objects.equals(endTime, that.endTime);
    }
}
