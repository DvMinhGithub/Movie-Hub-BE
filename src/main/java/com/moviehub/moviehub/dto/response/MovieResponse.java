package com.moviehub.moviehub.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieResponse {
    private Long id;
    private String title;
    private String description;
    private Integer duration;
    private String genre;
    private String director;
    private String language;
    private String actors;
    private String cast;
    private LocalDate releaseDate;
    private String posterUrl;
    private Double rating;
    private Boolean isActive;
}
