package com.juit.hostelmanagement.controllers;

import com.juit.hostelmanagement.models.Room;
import com.juit.hostelmanagement.dto.RoomAllocationDto;
import com.juit.hostelmanagement.dto.RoomDto;
import com.juit.hostelmanagement.services.AllocationService;
import com.juit.hostelmanagement.services.RoomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@PreAuthorize("hasRole('ROLE_WARDEN') or hasRole('ROLE_ADMIN')")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private AllocationService allocationService;

    @GetMapping("/vacant")
    public List<Room> getVacantRooms(){
        return roomService.getVacantRooms();
    }

    @PostMapping
    public ResponseEntity<Room> addRoom(@Valid @RequestBody RoomDto room){
        Room newRoom = roomService.addRoom(room);

        return ResponseEntity.ok(newRoom);
    }

    @GetMapping()
    public List<Room> getAllRooms(){
        return roomService.findAllRooms();
    }

    @PostMapping("/allocate")
    public ResponseEntity<Room> allocateRooms(@Valid @RequestBody RoomAllocationDto roomAllocationDto) {
        Room room = allocationService.allocateRoomManually(roomAllocationDto.getRollNumber(), roomAllocationDto.getRoomNo());

        return ResponseEntity.ok(room);
    }

    @PostMapping("/vacate")
    public ResponseEntity<Room> vacateRoom(@Valid @RequestParam Long rollNo) {
        Room room = allocationService.vacateRoom(rollNo);

        return ResponseEntity.ok(room);
    }

    @GetMapping("/rooms/byHostel")
    @ResponseBody
    public List<Room> getRoomsByHostel(@Valid @RequestParam String hostel) {
        return roomService.getRoomsByHostel(hostel);
    }

}
