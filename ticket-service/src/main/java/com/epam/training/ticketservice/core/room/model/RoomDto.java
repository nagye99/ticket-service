package com.epam.training.ticketservice.core.room.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomDto {
    private final String name;
    private final Integer roomRows;
    private final Integer roomColumns;

    private Integer getChairsNumber() {
        return roomRows * roomColumns;
    }

    public String toString() {
        return "Room "
                + name
                + " with "
                + getChairsNumber()
                + " seats, "
                + roomRows
                + " rows and "
                + roomColumns
                + " columns";
    }
}
