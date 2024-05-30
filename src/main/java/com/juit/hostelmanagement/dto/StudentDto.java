package com.juit.hostelmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudentDto {
    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotNull(message = "Roll number must not be null")
    private Long rollNumber;

    @NotNull(message = "Phone number must not be null")
    @Positive(message = "Phone number must be a positive number")
    private Long phoneNo;

    @NotNull(message = "Parent's phone number must not be null")
    @Positive(message = "Parent's phone number must be a positive number")
    private Long parentNo;

    @NotNull(message = "CGPA must not be null")
    @Positive(message = "CGPA must be a positive number")
    private Double cgpa;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
}
