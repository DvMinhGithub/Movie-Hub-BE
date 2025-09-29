package com.moviehub.moviehub.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.moviehub.moviehub.dto.request.CreateMovieRequest;
import com.moviehub.moviehub.dto.request.UpdateMovieRequest;
import com.moviehub.moviehub.dto.response.MovieResponse;
import com.moviehub.moviehub.model.Movie;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MovieMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rating", constant = "0.0")
    Movie toEntity(CreateMovieRequest request);

    MovieResponse toResponse(Movie movie);

    @Mapping(target = "id", ignore = true)
    void updateMovieFromRequest(UpdateMovieRequest updateRequest, @MappingTarget Movie existingMovie);
}
