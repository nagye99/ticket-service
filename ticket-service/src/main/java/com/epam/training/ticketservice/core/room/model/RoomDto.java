package com.epam.training.ticketservice.core.room.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomDto {
    private final String name;
    private final Integer rows;
    private final Integer columns;

    private Integer getChairsNumber() {
        return rows * columns;
    }

    public String toString() {
        return "Room " + name + " with " + getChairsNumber() + " seats, " + rows + " rows and " + columns + " columns";
    }
}
