package com.ad.meeting.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rooms")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private String floor;

    public void update(String name, Integer capacity, String floor) {
        this.name = name;
        this.capacity = capacity;
        this.floor = floor;
    }

}
