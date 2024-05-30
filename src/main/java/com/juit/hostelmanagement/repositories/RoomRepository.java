package com.juit.hostelmanagement.repositories;

import com.juit.hostelmanagement.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomNumber(String roomNumber);

    @Query("SELECT r FROM Room r WHERE r.vacancy > 0")
    List<Room> findVacantRooms();

    @Query("SELECT r FROM Room r WHERE r.vacancy > 0 AND r.hostelName = :hostelName ORDER BY r.roomNumber")
    List<Room> findVacantRoomsByHostel(@Param("hostelName") String hostelName);

    @Query("SELECT r FROM Room r WHERE r.hostelName = :hostelName ORDER BY r.roomNumber")
    List<Room> findByHostelName(@Param("hostelName") String hostelName);

    @Query("SELECT r FROM Room r WHERE r.hostelName = :hostelName AND r.roomNumber = :roomNumber")
    Optional<Room> findByHostelNameAndRoomNumber(@Param("hostelName") String hostelName, @Param("roomNumber") String roomNumber);
}
