package com.epam.training.ticketservice.core.room.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomDto {
    private final String name;
    private final Integer rows;
    private final Integer columns;
}
