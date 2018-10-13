package com.doy.reservation.dto;

import com.doy.reservation.domain.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class ReservationDTO {
    @NotBlank
    @Size(min = 1, max = 1)
    @Pattern(regexp = "[A-J]")
    private String roomName;

    @NotBlank
    @Size(min = 2, max = 10)
    private String reservedName;

    @NotBlank
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotBlank
    @DateTimeFormat(pattern = "kk:mm")
    private LocalTime startTime;

    @NotBlank
    @DateTimeFormat(pattern = "kk:mm")
    private LocalTime endTime;

    @Size(min = 1, max = 10)
    private int numOfRecursion;

    public ReservationDTO(String roomName, String reservedName, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.roomName = roomName;
        this.reservedName = reservedName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.numOfRecursion = 1;
    }

    public ReservationDTO(String roomName, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this(roomName, "doy", date, startTime, endTime);
    }

    public Reservation toEntity() {
        return new Reservation(roomName, reservedName, date, startTime, endTime);
    }

    public static ReservationDTO defaultReservationADto() {
        return new ReservationDTO("A", "doy", LocalDate.now(), LocalTime.of(10, 30), LocalTime.of(12, 0), 1);
    }

    public static ReservationDTO defaultReservationBDto() {
        return new ReservationDTO("A", "doy2", LocalDate.now(), LocalTime.of(15, 30), LocalTime.of(19, 0), 2);
    }
}
