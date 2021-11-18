package com.epam.training.ticketservice.core.room.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true)
    private String name;
    private Integer rows;

    public Room(String name, Integer rows, Integer cols) {
        this.name = name;
        this.rows = rows;
        this.cols = cols;
    }

    private Integer cols;
}
