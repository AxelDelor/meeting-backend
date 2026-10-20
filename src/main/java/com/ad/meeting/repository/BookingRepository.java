package com.ad.meeting.repository;

import com.ad.meeting.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByStartTime(LocalDateTime startTime);

}
