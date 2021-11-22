package com.epam.training.ticketservice.core.movie.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieDto {
    private final String title;
    private final String genre;
    private final Integer length;

    @Override
    public String toString() {
        return title + " (" + genre + ", " + length + " minutes)";
    }
}
