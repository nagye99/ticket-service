package com.epam.training.ticketservice.core.booking.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String username;
    private Integer screeningId;
    private String seats;
    private Integer price;

    public Booking(String username, Integer screeningId, String seats, Integer price) {
        this.username = username;
        this.screeningId = screeningId;
        this.seats = seats;
        this.price = price;
    }
}
