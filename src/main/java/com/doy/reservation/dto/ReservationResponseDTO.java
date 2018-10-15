package com.doy.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservationResponseDTO {
    private Long id;

    private String roomName;

    private String reservedName;

    private LocalTime startTime;

    private LocalTime endTime;
}
