package com.juit.hostelmanagement.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hostelName;

    private String roomNumber;

    private int vacancy;
    private int capacity;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Student> students;

}
