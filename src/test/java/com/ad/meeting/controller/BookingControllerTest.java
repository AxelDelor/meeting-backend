package com.ad.meeting.controller;

import com.ad.meeting.dto.BookingRequest;
import com.ad.meeting.dto.BookingResponse;
import com.ad.meeting.service.BookingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookingService bookingService;

    @Test
    void createBookingTest() throws Exception {
        when(bookingService.createBooking(any(BookingRequest.class)))
                .thenReturn(new BookingResponse(1L, 1L, "Test Booking 1", "John Doe", LocalDateTime.parse("2026-11-29T09:00:00"), LocalDateTime.parse("2026-11-29T11:00:00")));

        mockMvc.perform(post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"title": "Test Booking 1","organizer": "John Doe", "roomId": 1, "startTime": "2026-11-29T09:00:00", "endTime": "2026-11-29T11:00:00"}
                                """))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/bookings/1"))
                .andExpect(jsonPath("$.id").value(1));
    }

}
