package com.juit.hostelmanagement.controllers;

import com.juit.hostelmanagement.dto.StudentDto;
import com.juit.hostelmanagement.models.Room;
import com.juit.hostelmanagement.models.Student;
import com.juit.hostelmanagement.repositories.RoomRepository;
import com.juit.hostelmanagement.repositories.StudentRepository;
import com.juit.hostelmanagement.services.AllocationService;
import com.juit.hostelmanagement.services.RoomService;
import com.juit.hostelmanagement.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/manage")
public class WebController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private AllocationService allocationService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping
    public String showHomePage() {
        return "home";
    }

    @GetMapping("/rooms/view")
    public String showRoomDetailsForm() {
        return "roomDetailsForm";
    }

    @PostMapping("/rooms/view/details")
    public String showRoomDetails(@RequestParam Long rollNumber, Model model) {
        Room room = studentService.getRoomByStudentRollNumber(rollNumber);
        if (room != null) {
            model.addAttribute("room", room);
            return "roomDetails"; // Return the Thymeleaf template for displaying room details
        } else {
            model.addAttribute("error", "Student with roll number " + rollNumber + " not found.");
            return "roomDetailsForm"; // Return the form again with error message
        }
    }

    @GetMapping("/rooms/available")
    public String showAvailableRooms(Model model) {
        List<Room> availableRooms = roomService.getVacantRooms();
        model.addAttribute("rooms", availableRooms);
        return "availableRooms";
    }

    @GetMapping("/rooms/all")
    public String showAllRooms(Model model) {
        List<Room> allRooms = roomService.findAllRooms();
        model.addAttribute("rooms", allRooms);
        return "allRooms";
    }

    @GetMapping("/rooms/participants")
    public String viewRoomParticipants(@RequestParam(required = false) String hostel,
                                       @RequestParam(required = false) String roomNumber,
                                       Model model) {
        if (hostel != null && roomNumber != null) {
            // If both hostel and room number are provided, fetch the room and its participants
            Room room = roomService.findByHostelAndRoomNumber(hostel, roomNumber);
            if (room != null) {
                // If room found, add room and its participants to the model
                model.addAttribute("room", room);
                model.addAttribute("students", room.getStudents());
            } else {
                // If room not found, add error message to the model
                model.addAttribute("error", "Room not found.");
            }
        }
        // Return the view name
        return "viewRoomParticipants";
    }

    @GetMapping("/rooms/assign")
    public String showAssignRoomPage(Model model) {
        return "assignRoom";
    }

    @PostMapping("/rooms/assign")
    public String assignRoom(
            @RequestParam String hostel,
            @RequestParam String roomNumber,
            @RequestParam Long studentId,
            Model model) {

        // Find the room based on hostel and room number
        Room room = roomService.findByHostelAndRoomNumber(hostel, roomNumber);
        if (room == null) {
            model.addAttribute("error", "Room not found.");
            return "assignRoom";
        }

        // Find the student by ID
        Student student = studentService.findStudent(studentId);
        if (student == null) {
            model.addAttribute("error", "Student not found.");
            return "assignRoom";
        }

        // Check room vacancy and assign student to room
        if (room.getVacancy() > 0) {
            allocationService.allocateRoomManually(studentId, room);
            model.addAttribute("message", "Room assigned successfully.");
        } else {
            model.addAttribute("error", "Room is already full.");
        }

        return "assignRoom";
    }

    @GetMapping("/students/add")
    public String showAddStudentForm(Model model) {
        model.addAttribute("studentPayload", new StudentDto());
        return "addStudent";
    }

    @PostMapping("/students/add")
    public String addStudent(@ModelAttribute StudentDto studentDto) {
        studentService.addStudent(studentDto);
        return "redirect:/students/add";
    }

    @GetMapping("/rooms/allbyhostel")
    public String viewAllRoomsByHostel(@RequestParam(required = false) String hostel, Model model) {
        List<Room> rooms = roomService.findByHostelName(hostel);
        if (!rooms.isEmpty()) {
            model.addAttribute("rooms", rooms);
            model.addAttribute("hostelName", hostel);
        } else {
            model.addAttribute("error", "No rooms found for hostel " + hostel);
        }
        return "viewRoomsByHostel";
    }

    @GetMapping("/rooms/availablebyhostel")
    public String viewRoomsByHostel(@RequestParam(required = false) String hostel, Model model) {
        List<Room> rooms = roomService.findAvailableByHostel(hostel);
        if (!rooms.isEmpty()) {
            model.addAttribute("rooms", rooms);
            model.addAttribute("hostelName", hostel);
        } else {
            model.addAttribute("error", "No rooms found for hostel " + hostel);
        }
        return "availableByHostel";
    }
}
