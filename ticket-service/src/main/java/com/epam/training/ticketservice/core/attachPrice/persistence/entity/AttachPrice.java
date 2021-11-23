package com.epam.training.ticketservice.core.attachPrice.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AttachPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String priceComponent;
    private Integer objectId;
    @Enumerated(EnumType.STRING)
    private ObjectType type;

    public AttachPrice(String priceComponent, Integer objectId, ObjectType type) {
        this.priceComponent = priceComponent;
        this.objectId = objectId;
        this.type = type;
    }

    public enum ObjectType {
        MOVIE,
        ROOM,
        SCREENING
    }
}
