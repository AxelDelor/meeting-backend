package com.ad.meeting.repository;

import com.ad.meeting.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByRoomIdAndEndTimeGreaterThanAndStartTimeLessThan(Long RoomId, LocalDateTime newStart, LocalDateTime newEnd);

    List<Booking> findByRoomId(Long roomId);


}
