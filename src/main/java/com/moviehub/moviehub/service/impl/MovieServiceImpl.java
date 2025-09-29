package com.moviehub.moviehub.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moviehub.moviehub.dto.request.CreateMovieRequest;
import com.moviehub.moviehub.dto.request.UpdateMovieRequest;
import com.moviehub.moviehub.dto.response.MovieResponse;
import com.moviehub.moviehub.exception.ResourceNotFoundException;
import com.moviehub.moviehub.mapper.MovieMapper;
import com.moviehub.moviehub.model.Movie;
import com.moviehub.moviehub.repository.MovieRepository;
import com.moviehub.moviehub.service.MovieService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MovieResponse> getAllMoviesResponse(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Movie> movies = movieRepository.findAll(pageable);
        return movies.stream().map(movieMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MovieResponse getMovieResponseById(Long id) {
        Movie movie = movieRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phim với ID: " + id));
        return movieMapper.toResponse(movie);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieResponse> searchMoviesResponse(String title, String genre, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> movies;

        if (title != null && genre != null) {
            movies =
                    movieRepository.findByTitleContainingIgnoreCaseAndGenreContainingIgnoreCase(title, genre, pageable);
        } else if (title != null) {
            movies = movieRepository.findByTitleContainingIgnoreCase(title, pageable);
        } else if (genre != null) {
            movies = movieRepository.findByGenreContainingIgnoreCase(genre, pageable);
        } else {
            movies = movieRepository.findAll(pageable);
        }

        return movies.stream().map(movieMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieResponse> getCurrentlyShowingMoviesResponse(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Movie> movies = movieRepository.findByIsActiveTrue(pageable);
        return movies.stream().map(movieMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MovieResponse> getTopRatedMoviesResponse(int limit) {
        List<Movie> movies = movieRepository.findTopRatedMovies(PageRequest.of(0, limit));
        return movies.stream().map(movieMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public MovieResponse createMovie(CreateMovieRequest createRequest) {
        Movie movie = movieMapper.toEntity(createRequest);
        Movie savedMovie = movieRepository.save(movie);
        return movieMapper.toResponse(savedMovie);
    }

    @Override
    public MovieResponse updateMovie(Long id, UpdateMovieRequest updateRequest) {
        Movie existingMovie = movieRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phim với ID: " + id));

        movieMapper.updateMovieFromRequest(updateRequest, existingMovie);

        Movie updatedMovie = movieRepository.save(existingMovie);
        return movieMapper.toResponse(updatedMovie);
    }

    @Override
    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy phim với ID: " + id);
        }
        movieRepository.deleteById(id);
    }
}
