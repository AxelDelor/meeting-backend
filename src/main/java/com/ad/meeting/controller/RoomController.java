package com.ad.meeting.controller;

import com.ad.meeting.dto.RoomRequest;
import com.ad.meeting.dto.RoomResponse;
import com.ad.meeting.service.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;

    @GetMapping
    public ResponseEntity<List<RoomResponse>> getRooms() {
        List<RoomResponse> roomsRead = roomService.getRooms();
        return ResponseEntity.ok(roomsRead);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<RoomResponse> getRoom(@PathVariable Long roomId) {
        RoomResponse roomRead = roomService.getRoom(roomId);
        return ResponseEntity.ok(roomRead);
    }

    @PostMapping
    public ResponseEntity<RoomResponse> addRoom(@Valid @RequestBody RoomRequest roomRequest) {
        RoomResponse roomCreated = roomService.addRoom(roomRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{roomId}")
                .buildAndExpand(roomCreated.id())
                .toUri();

        return ResponseEntity.created(location).body(roomCreated);
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long roomId, @Valid @RequestBody RoomRequest roomRequest) {
        RoomResponse roomUpdated = roomService.updateRoom(roomId, roomRequest);
        return ResponseEntity.ok(roomUpdated);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
        return ResponseEntity.noContent().build();
    }

}
