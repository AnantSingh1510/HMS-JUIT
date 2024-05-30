package com.juit.hostelmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class RoomAllocationDto {
    @NotNull(message = "Roll number cannot be null")
    @Positive(message = "Roll number must be a positive number")
    private Long rollNumber;

    @NotBlank(message = "Room number cannot be blank")
    private String roomNo;
}
