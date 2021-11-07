package com.epam.training.ticketservice.core.movie.model;

import java.util.Objects;

public class Movie {

    private final String title;
    private final String genre;
    private final int length;

    public Movie(String title, String genre, int length) {
        this.title = title;
        this.genre = genre;
        this.length = length;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(title, movie.title) && Objects.equals(genre, movie.genre) && Objects.equals(length, movie.length);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, genre, length);
    }

    @Override
    public String toString() {
        return title + "(" + genre + ", " + length + " minutes)";
    }
}
