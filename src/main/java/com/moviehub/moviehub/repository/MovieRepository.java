package com.moviehub.moviehub.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.moviehub.moviehub.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Page<Movie> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Movie> findByGenreContainingIgnoreCase(String genre, Pageable pageable);

    Page<Movie> findByTitleContainingIgnoreCaseAndGenreContainingIgnoreCase(
            String title, String genre, Pageable pageable);

    Page<Movie> findByIsActiveTrue(Pageable pageable);

    Page<Movie> findByReleaseDateAfter(LocalDate date, Pageable pageable);

    Page<Movie> findByDirectorContainingIgnoreCase(String director, Pageable pageable);

    @Query("SELECT m FROM Movie m WHERE m.rating >= 4.0 ORDER BY m.rating DESC")
    List<Movie> findTopRatedMovies(Pageable pageable);
}
