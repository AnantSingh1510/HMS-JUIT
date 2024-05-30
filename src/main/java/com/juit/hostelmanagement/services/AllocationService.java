package com.juit.hostelmanagement.services;

import com.juit.hostelmanagement.models.Room;
import com.juit.hostelmanagement.models.Student;
import com.juit.hostelmanagement.repositories.RoomRepository;
import com.juit.hostelmanagement.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AllocationService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private StudentService studentService;

    public void allocateRooms(List<Student> students) {
        List<Room> rooms = roomRepository.findAll();

        int roomIndex = 0;
        for (int i = 0; i < students.size(); i += 2) {
            Room room = rooms.get(roomIndex);
            students.get(i).setRoom(room);
            studentRepository.save(students.get(i));

            if (i + 1 < students.size()) {
                students.get(i + 1).setRoom(room);
                studentRepository.save(students.get(i + 1));
            }

            roomIndex++;
        }
    }

    @Transactional
    public Room allocateRoomManually(Long rollNumber, String roomNo){
        Student student = studentRepository.findByRollNumber(rollNumber).orElseThrow(() -> new RuntimeException("Student not found with roll number: " + rollNumber));
        Room room = roomRepository.findByRoomNumber(roomNo).orElseThrow(() -> new RuntimeException("Room not found with room number: " + roomNo));

        assert room != null;

        if (room.getVacancy() == 0){
            throw new RuntimeException("Room is already full");
        }
        if (room.getStudents().contains(student)) {
            throw new RuntimeException("Student is already allocated to this room");
        }

        Room currRoom = studentService.getRoomByStudentRollNumber(rollNumber);
        if (currRoom != null){
            currRoom.setVacancy(currRoom.getVacancy() + 1);
            ArrayList<Student> updatedResidents = new ArrayList<>();

            for(Student resident : currRoom.getStudents()){
                if (!resident.equals(student)){
                    updatedResidents.add(resident);
                }
            }

            currRoom.setStudents(updatedResidents);
            roomRepository.save(currRoom);
        }

        // Add the student to the room
        room.getStudents().add(student);
        room.setVacancy(room.getVacancy()-1);

        // Update the student's room reference
        student.setRoom(room);

        // Save the updated room and student
        roomRepository.save(room);
        studentRepository.save(student);

        return room;
    }

    @Transactional
    public Room allocateRoomManually(Long rollNumber, Room roomAllocate){
        Student student = studentRepository.findByRollNumber(rollNumber)
                .orElseThrow(() -> new RuntimeException("Student not found with roll number: " + rollNumber));

        Room room = roomRepository.findByHostelNameAndRoomNumber(roomAllocate.getHostelName(), roomAllocate.getRoomNumber())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        assert room != null;

        if (room.getVacancy() == 0){
            throw new RuntimeException("Room is already full");
        }
        if (room.getStudents().contains(student)) {
            throw new RuntimeException("Student is already allocated to this room");
        }

        Room currRoom = studentService.getRoomByStudentRollNumber(rollNumber);
        if (currRoom != null){
            currRoom.setVacancy(currRoom.getVacancy() + 1);
            ArrayList<Student> updatedResidents = new ArrayList<>();

            for(Student resident : currRoom.getStudents()){
                if (!resident.equals(student)){
                    updatedResidents.add(resident);
                }
            }

            currRoom.setStudents(updatedResidents);
            roomRepository.save(currRoom);
        }

        // Add the student to the room
        room.getStudents().add(student);
        room.setVacancy(room.getVacancy()-1);

        // Update the student's room reference
        student.setRoom(room);

        // Save the updated room and student
        roomRepository.save(room);
        studentRepository.save(student);

        return room;
    }

    @Transactional
    public Room vacateRoomByRoomNumber(String roomNo) {
        Room room = roomRepository.findByRoomNumber(roomNo)
                .orElseThrow(() -> new RuntimeException("Room not found with room number: " + roomNo));

        List<Student> students = room.getStudents();
        for (Student student : students) {
            student.setRoom(null);
            studentRepository.save(student);
        }

        room.getStudents().clear();
        roomRepository.save(room);

        return room;
    }

    @Transactional
    public Room vacateRoom(Long rollNumber) {
        Student student = studentRepository.findByRollNumber(rollNumber)
                .orElseThrow(() -> new RuntimeException("Student not found with roll number: " + rollNumber));

        Room room = student.getRoom();
        if (room == null) {
            throw new RuntimeException("Student is not allocated to any room");
        }

        room.getStudents().remove(student);
        student.setRoom(null);

        roomRepository.save(room);
        studentRepository.save(student);

        return room;
    }
}
