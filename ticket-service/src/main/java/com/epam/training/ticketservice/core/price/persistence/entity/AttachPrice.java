package com.epam.training.ticketservice.core.price.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AttachPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String priceComponentName;
    private Integer objectId;
    @Enumerated(EnumType.STRING)
    private ObjectType type;

    public AttachPrice(String priceComponentName, Integer objectId, ObjectType type) {
        this.priceComponentName = priceComponentName;
        this.objectId = objectId;
        this.type = type;
    }

    public enum ObjectType {
        MOVIE,
        ROOM,
        SCREENING
    }
}
