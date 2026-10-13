package com.ad.meeting.controller;

import com.ad.meeting.dto.RoomRequest;
import com.ad.meeting.dto.RoomResponse;
import com.ad.meeting.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.Location;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public List<RoomResponse> getRooms() {
        return roomService.getRooms();
    }

    @GetMapping("/{roomId}")
    public RoomResponse getRoom(@PathVariable Long roomId) {
        return roomService.getRoom(roomId);
    }

    @PostMapping
    public RoomResponse addRoom(@Valid @ResponseBody RoomRequest roomRequest) {
        ResponseEntity(HttpStatus.CREATED.value(), Location, "The room was successfully created");
        return roomService.addRoom(roomRequest);
    }

}
