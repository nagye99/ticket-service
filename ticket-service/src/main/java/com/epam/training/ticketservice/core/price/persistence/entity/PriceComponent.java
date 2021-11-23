package com.epam.training.ticketservice.core.price.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PriceComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @Column(unique = true)
    String name;
    Integer price;

    public PriceComponent(String name, Integer price) {
        this.name = name;
        this.price = price;
    }
}
