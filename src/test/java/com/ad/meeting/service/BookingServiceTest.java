package com.ad.meeting.service;

import com.ad.meeting.dto.BookingRequest;
import com.ad.meeting.exception.InvalidBookingException;
import com.ad.meeting.repository.BookingRepository;
import com.ad.meeting.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private BookingRepository bookingRepository;


    //    Room room01;
    BookingService service;
    Clock clock;

    @BeforeEach
    void initializingData() {
        Instant instant = Instant.parse("2026-10-29T10:00:00Z");
        ZoneId zoneId = ZoneId.of("Europe/Paris");
        clock = Clock.fixed(instant, zoneId);

        service = new BookingService(bookingRepository, roomRepository, clock);

//        Room room01 = Room.builder()
//                .name("103")
//                .capacity(12)
//                .floor("RDC")
//                .build();
    }

    @Test
    void testStartAfterEnd() {

        InvalidBookingException ex = assertThrows(InvalidBookingException.class, () -> service.createBooking(constructBookingRequest(LocalDateTime.parse("2026-12-12T11:00:00"), LocalDateTime.parse("2026-12-12T10:00:00"))));

        verifyNoInteractions(bookingRepository);
        System.out.println(ex.getMessage());
    }

    @Test
    void testStartEqualEnd() {

        InvalidBookingException ex = assertThrows(InvalidBookingException.class, () -> service.createBooking(constructBookingRequest(LocalDateTime.parse("2026-12-12T10:00:00"), LocalDateTime.parse("2026-12-12T10:00:00"))));

        verifyNoInteractions(bookingRepository, roomRepository);
        System.out.println(ex.getMessage());

    }

    @Test
    void testDurationOver4() {

        InvalidBookingException ex = assertThrows(InvalidBookingException.class, () -> service.createBooking(constructBookingRequest(LocalDateTime.parse("2026-12-12T11:00:00"), LocalDateTime.parse("2026-12-12T15:01:00"))));

        verifyNoInteractions(bookingRepository, roomRepository);
        System.out.println(ex.getMessage());

    }

    @Test
    void testBookingInThePast() {

        InvalidBookingException ex = assertThrows(InvalidBookingException.class, () -> service.createBooking(constructBookingRequest(LocalDateTime.parse("2026-10-29T08:00:00"), LocalDateTime.parse("2026-10-29T10:00:00"))));

        verifyNoInteractions(bookingRepository, roomRepository);
        System.out.println(ex.getMessage());

    }

    // Private

    private BookingRequest constructBookingRequest(LocalDateTime startTime, LocalDateTime endTime) {
        return new BookingRequest("Test", "John Doe", 1L, startTime, endTime);
    }

}
