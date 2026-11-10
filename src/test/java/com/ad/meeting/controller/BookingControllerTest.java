package com.ad.meeting.controller;

import com.ad.meeting.service.BookingService;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@WebMvcTest
public class BookingControllerTest {

    @MockitoBean
    private BookingService bookingService;


    @MockMvc

}
