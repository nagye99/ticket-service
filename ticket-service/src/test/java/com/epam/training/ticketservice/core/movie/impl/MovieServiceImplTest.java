package com.epam.training.ticketservice.core.movie.impl;

import com.epam.training.ticketservice.core.movie.model.MovieDto;
import com.epam.training.ticketservice.core.movie.persistence.entity.Movie;
import com.epam.training.ticketservice.core.movie.persistence.repository.MovieRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MovieServiceImplTest {

    private static final Movie IT_ENTITY = new Movie("It", "horror", 135);
    private static final Movie RUSH_ENTITY = new Movie("Rush", "drama", 123);
    private static final MovieDto IT_DTO = MovieDto.builder()
            .title("It")
            .genre("horror")
            .length(135)
            .build();
    private static final MovieDto RUSH_DTO = MovieDto.builder()
            .title("Rush")
            .genre("drama")
            .length(123)
            .build();

    private final MovieRepository movieRepository = mock(MovieRepository.class);
    private final MovieServiceImpl underTest = new MovieServiceImpl(movieRepository);

    @Test
    void testAddMovieShouldThrowNullPointerExceptionWhenTitleIsNull() {
        // Given
        MovieDto movieDto = MovieDto.builder()
                .title(null)
                .genre("drama")
                .length(300)
                .build();

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.addMovie(movieDto));
    }

    @Test
    void testAddMovieShouldThrowNullPointerExceptionWhenGenreIsNull() {
        // Given
        MovieDto movieDto = MovieDto
                .builder()
                .title("It")
                .genre(null)
                .length(300)
                .build();

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.addMovie(movieDto));
    }

    @Test
    void testAddMovieShouldThrowNullPointerExceptionWhenLengthIsNull() {
        // Given
        MovieDto movieDto = MovieDto
                .builder()
                .title("It")
                .genre("drama")
                .length(null)
                .build();

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.addMovie(movieDto));
    }

    @Test
    public void testAddMovieShouldThrowNullPointerExceptionWhenMovieIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.addMovie(null));
    }

    @Test
    public void testAddMovieShouldCallMovieRepositoryWhenTheInputMovieIsValid() {
        // Given
        when(movieRepository.save(IT_ENTITY)).thenReturn(IT_ENTITY);

        // When
        underTest.addMovie(IT_DTO);

        // Then
        verify(movieRepository).save(new Movie("It", "horror", 135));
    }

    @Test
    void testUpdateMovieShouldThrowNullPointerExceptionWhenTitleIsNull() {
        // Given
        MovieDto movieDto = MovieDto.builder()
                .title(null)
                .genre("drama")
                .length(300)
                .build();

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.updateMovie(movieDto));
    }

    @Test
    void testUpdateMovieShouldThrowNullPointerExceptionWhenGenreIsNull() {
        // Given
        MovieDto movieDto = MovieDto
                .builder()
                .title("It")
                .genre(null)
                .length(300)
                .build();

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.addMovie(movieDto));
    }

    @Test
    void testUpdateMovieShouldThrowNullPointerExceptionWhenLengthIsNull() {
        // Given
        MovieDto movieDto = MovieDto
                .builder()
                .title("It")
                .genre("drama")
                .length(null)
                .build();

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.addMovie(movieDto));
    }

    @Test
    public void testUpdateMovieShouldThrowNullPointerExceptionWhenProductIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.addMovie(null));
    }

    @Test
    public void testUpdateMovieShouldCallMovieRepositoryAndReturnStringWhenInputIsValid() {
        // Given
        when(movieRepository.save(new Movie("It", "drama", 100))).thenReturn(new Movie("It", "drama", 100));
        when(movieRepository.findByTitle("It")).thenReturn(java.util.Optional.of(IT_ENTITY));

        MovieDto UPDATED_IT_DTO = MovieDto
                .builder()
                .title("It")
                .genre("drama")
                .length(100)
                .build();

        String expected = UPDATED_IT_DTO + " is updated.";

        // When
        String actual = underTest.updateMovie(UPDATED_IT_DTO);

        // Then
        assertEquals(expected, actual);
        verify(movieRepository).save(new Movie("It", "drama", 100));
        verify(movieRepository).findByTitle(UPDATED_IT_DTO.getTitle());
    }

    @Test
    public void testUpdateMovieShouldWCallMovieRepositoryAndReturnStringWhenInputIsNotValid() {
        // Given
        when(movieRepository.save(IT_ENTITY)).thenReturn(IT_ENTITY);
        when(movieRepository.findByTitle(IT_ENTITY.getTitle())).thenReturn(Optional.empty());

        MovieDto UPDATED_IT_DTO = MovieDto
                .builder()
                .title("Ironman")
                .genre("drama")
                .length(100)
                .build();

        String expected = "The movie doesn't exist.";

        // When
        String actual = underTest.updateMovie(UPDATED_IT_DTO);

        // Then
        assertEquals(expected, actual);
        verify(movieRepository).findByTitle(UPDATED_IT_DTO.getTitle());
    }

    @Test
    void testDeleteMovieShouldCallMovieRepositoryWhenTheInputMovieIsValid() {
        // Given - When
        underTest.deleteMovie("It");

        // Then
        verify(movieRepository).deleteByTitle("It");
    }

    @Test
    void testDeleteMovieShouldThrowNullPointerExceptionWhenMovieTitleIsNull() {
        // Given - When - Then
        assertThrows(NullPointerException.class, () -> underTest.deleteMovie(null));
    }


    @Test
    void testListMoviesShouldReturnMovieDtoList() {
        // Given
        when(movieRepository.findAll()).thenReturn(List.of(IT_ENTITY, RUSH_ENTITY));

        List<MovieDto> expected = List.of(IT_DTO, RUSH_DTO);

        // When
        List<MovieDto> actual = underTest.listMovies();

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testGetMovieByTitleShouldReturnMovieDto() {
        // Given
        when(movieRepository.findByTitle("It")).thenReturn(Optional.of(IT_ENTITY));

        MovieDto expected = IT_DTO;

        // When
        MovieDto actual = underTest.getMovieByTitle("It");

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testGetMovieByTitleShouldThrowIllegalArgumentExceptionWhenMovieNotExpect() {
        // Given
        when(movieRepository.findByTitle("Ironman")).thenReturn(Optional.empty());

        // When - Then
        assertThrows(IllegalArgumentException.class, () -> underTest.getMovieByTitle("Ironman"));
    }

    @Test
    void testGetMovieIdShouldReturnInteger() {
        // Given
        when(movieRepository.findByTitle("It")).thenReturn(Optional.of(IT_ENTITY));

        Integer expected = IT_ENTITY.getId();

        // When
        Integer actual = underTest.getMovieId("It");

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testGetMovieIdShouldThrowNullPointerExceptionWhenMovieNotExpect() {
        // Given
        when(movieRepository.findByTitle("Ironman")).thenReturn(Optional.empty());

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.getMovieId("Ironman"));
    }

    @Test
    void testGetMinutesShouldReturnLong() {
        // Given
        when(movieRepository.findByTitle("It")).thenReturn(Optional.of(IT_ENTITY));

        long expected = IT_ENTITY.getLength();

        // When
        long actual = underTest.getMinutes("It");

        // Then
        assertEquals(expected, actual);
    }

    @Test
    void testGetMinutesShouldThrowNullPointerExceptionWhenMovieNotExpect() {
        // Given
        when(movieRepository.findByTitle("Ironman")).thenReturn(Optional.empty());

        // When - Then
        assertThrows(NullPointerException.class, () -> underTest.getMinutes("Ironman"));
    }
}