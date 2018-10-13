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
    }

    public Reservation toEntity() {
        return new Reservation(roomName, reservedName, date, startTime, endTime);
    }
}
