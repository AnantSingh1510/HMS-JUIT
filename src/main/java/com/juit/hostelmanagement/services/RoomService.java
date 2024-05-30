package com.juit.hostelmanagement.services;

import com.juit.hostelmanagement.models.Room;
import com.juit.hostelmanagement.dto.RoomDto;
import com.juit.hostelmanagement.repositories.RoomRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ModelMapper modelMapper;

    boolean isVacant(String roomNo){
        Room room = roomRepository.findByRoomNumber(roomNo).orElse(null);

        assert room != null;
        return room.getVacancy() > 0;
    }

    public List<Room> getVacantRooms(){
        return roomRepository.findVacantRooms();
    }

    public Room findRoomById(Long roomId){
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid room id: " + roomId));
    }

    public Room addRoom(RoomDto roomDto){
        Room room = new Room();

        room.setHostelName(roomDto.getHostelName());
        room.setRoomNumber(roomDto.getRoomNumber());
        room.setCapacity(roomDto.getCapacity());
        room.setVacancy(roomDto.getCapacity());

//        modelMapper.map(roomDto, room);

        room.setStudents(new ArrayList<>());

        return roomRepository.save(room);
    }

    public List<Room> findByHostelName(String hostelName){
        return roomRepository.findByHostelName(hostelName);
    }

    public Room findByHostelAndRoomNumber(String hostel, String roomNumber){
        return roomRepository.findByHostelNameAndRoomNumber(hostel, roomNumber)
                .orElseThrow(() -> new RuntimeException("Room not found!"));
    }

    public List<Room> findAvailableByHostel(String hostel){
        return roomRepository.findVacantRoomsByHostel(hostel);
    }

    public List<Room> findAllRooms(){
        return roomRepository.findAll();
    }

    public List<Room> getRoomsByHostel(String hostel){
        return roomRepository.findByHostelName(hostel);
    }
}
