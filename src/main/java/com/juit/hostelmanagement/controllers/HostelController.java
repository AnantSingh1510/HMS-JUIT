package com.juit.hostelmanagement.controllers;

import com.juit.hostelmanagement.models.Room;
import com.juit.hostelmanagement.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/hostel")
public class HostelController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/{hostelName}")
    public List<Room> getHostelRooms(@PathVariable String hostelName){
        return roomService.getRoomsByHostel(hostelName);
    }
}
