package com.moviehub.moviehub.service;

import java.util.List;

import com.moviehub.moviehub.dto.request.CreateMovieRequest;
import com.moviehub.moviehub.dto.request.UpdateMovieRequest;
import com.moviehub.moviehub.dto.response.MovieResponse;

public interface MovieService {

    List<MovieResponse> getAllMoviesResponse(int page, int size, String sortBy);

    MovieResponse getMovieResponseById(Long id);

    List<MovieResponse> searchMoviesResponse(String title, String genre, int page, int size);

    List<MovieResponse> getCurrentlyShowingMoviesResponse(int page, int size);

    List<MovieResponse> getTopRatedMoviesResponse(int limit);

    MovieResponse createMovie(CreateMovieRequest createRequest);

    MovieResponse updateMovie(Long id, UpdateMovieRequest updateRequest);

    void deleteMovie(Long id);
}
