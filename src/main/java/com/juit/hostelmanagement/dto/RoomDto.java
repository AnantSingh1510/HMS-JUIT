package com.juit.hostelmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RoomDto {
    @NotBlank(message = "Hostel name must not be blank")
    private String hostelName;

    @NotBlank(message = "Room number must not be blank")
    private String roomNumber;

    @NotNull(message = "Capacity must not be null")
    @Positive(message = "Capacity must be a positive number")
    private Integer capacity;
}
