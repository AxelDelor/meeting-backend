package com.ad.meeting.repository;

import com.ad.meeting.model.Booking;
import com.ad.meeting.model.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class BookingRepositoryTest {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingRepository bookingRepository;

    private Room room01;
    private Room room02;

    @BeforeEach
    void initializingData() {
        room01 = roomRepository.save(Room.builder()
                .name("103")
                .capacity(12)
                .floor("RDC")
                .build()
        );

        room02 = roomRepository.save(Room.builder()
                .name("108")
                .capacity(19)
                .floor("RDC")
                .build()
        );


        bookingRepository.save(Booking.builder()
                .room(room01)
                .title("test booking")
                .organizer("John Doe")
                .startTime(LocalDateTime.parse("2026-12-12T10:00:00"))
                .endTime(LocalDateTime.parse("2026-12-12T12:00:00"))
                .build());
    }

    @Test
    void testBookingFollowing() {
        assertFalse(bookingRepository.existsByRoomIdAndEndTimeGreaterThanAndStartTimeLessThan(room01.getId(), LocalDateTime.parse("2026-12-12T12:00:00"), LocalDateTime.parse("2026-12-12T14:00:00")));
    }

    @Test
    void testBookingBefore() {
        assertFalse(bookingRepository.existsByRoomIdAndEndTimeGreaterThanAndStartTimeLessThan(room01.getId(), LocalDateTime.parse("2026-12-12T08:00:00"), LocalDateTime.parse("2026-12-12T10:00:00")));
    }

    @Test
    void testBookingSame() {
        assertTrue(bookingRepository.existsByRoomIdAndEndTimeGreaterThanAndStartTimeLessThan(room01.getId(), LocalDateTime.parse("2026-12-12T10:00:00"), LocalDateTime.parse("2026-12-12T12:00:00")));
    }

    @Test
    void testBookingWithin() {
        assertTrue(bookingRepository.existsByRoomIdAndEndTimeGreaterThanAndStartTimeLessThan(room01.getId(), LocalDateTime.parse("2026-12-12T11:00:00"), LocalDateTime.parse("2026-12-12T11:30:00")));
    }

    @Test
    void testBookingAround() {
        assertTrue(bookingRepository.existsByRoomIdAndEndTimeGreaterThanAndStartTimeLessThan(room01.getId(), LocalDateTime.parse("2026-12-12T09:00:00"), LocalDateTime.parse("2026-12-12T13:00:00")));
    }

    @Test
    void testBookingOverBeginning() {
        assertTrue(bookingRepository.existsByRoomIdAndEndTimeGreaterThanAndStartTimeLessThan(room01.getId(), LocalDateTime.parse("2026-12-12T09:00:00"), LocalDateTime.parse("2026-12-12T10:05:00")));
    }

    @Test
    void testBookingOverEnd() {
        assertTrue(bookingRepository.existsByRoomIdAndEndTimeGreaterThanAndStartTimeLessThan(room01.getId(), LocalDateTime.parse("2026-12-12T11:55:00"), LocalDateTime.parse("2026-12-12T14:00:00")));
    }

    @Test
    void testBookingOtherRoom() {
        assertFalse(bookingRepository.existsByRoomIdAndEndTimeGreaterThanAndStartTimeLessThan(room02.getId(), LocalDateTime.parse("2026-12-12T10:00:00"), LocalDateTime.parse("2026-12-12T12:00:00")));
    }

}
