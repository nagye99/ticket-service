package com.epam.training.ticketservice.core.room.model;

import java.util.Objects;

public class Room {
    private final String name;
    private final int rows;
    private final int columns;

    public Room(String name, int rows, int columns) {
        this.name = name;
        this.rows = rows;
        this.columns = columns;
    }

    public int getChairsNumber(){
        return rows * columns;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return rows == room.rows && columns == room.columns && Objects.equals(name, room.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rows, columns);
    }

    @Override
    public String toString() {
        return "Room " + name + " with " + getChairsNumber() + " seats, " + rows + "rows and " + columns + "columns";
    }
}
