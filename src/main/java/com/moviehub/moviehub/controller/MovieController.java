package com.moviehub.moviehub.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moviehub.moviehub.dto.request.CreateMovieRequest;
import com.moviehub.moviehub.dto.request.UpdateMovieRequest;
import com.moviehub.moviehub.dto.response.ApiResponse;
import com.moviehub.moviehub.dto.response.MovieResponse;
import com.moviehub.moviehub.service.MovieService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
@Tag(name = "Movies", description = "API quản lý phim")
public class MovieController extends BaseController {

    private final MovieService movieService;

    @Operation(summary = "Lấy danh sách phim", description = "API lấy danh sách phim có phân trang")
    @GetMapping
    public ResponseEntity<ApiResponse<List<MovieResponse>>> getAllMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            HttpServletRequest request) {
        List<MovieResponse> movies = movieService.getAllMoviesResponse(page, size, sortBy);
        return createSuccessResponse("Lấy danh sách phim thành công", movies, request);
    }

    @Operation(summary = "Lấy thông tin phim theo ID", description = "API lấy thông tin chi tiết của một phim")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MovieResponse>> getMovieById(@PathVariable Long id, HttpServletRequest request) {
        MovieResponse movie = movieService.getMovieResponseById(id);
        return createSuccessResponse("Lấy thông tin phim thành công", movie, request);
    }

    @Operation(summary = "Tìm kiếm phim", description = "API tìm kiếm phim theo tiêu đề và/hoặc thể loại")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<MovieResponse>>> searchMovies(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        List<MovieResponse> movies = movieService.searchMoviesResponse(title, genre, page, size);
        return createSuccessResponse("Tìm kiếm phim thành công", movies, request);
    }

    @Operation(summary = "Lấy danh sách phim đang chiếu", description = "API lấy danh sách phim đang chiếu")
    @GetMapping("/now-showing")
    public ResponseEntity<ApiResponse<List<MovieResponse>>> getCurrentlyShowingMovies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request) {
        List<MovieResponse> movies = movieService.getCurrentlyShowingMoviesResponse(page, size);
        return createSuccessResponse("Lấy danh sách phim đang chiếu thành công", movies, request);
    }

    @Operation(summary = "Lấy danh sách phim đánh giá cao", description = "API lấy danh sách phim có đánh giá cao nhất")
    @GetMapping("/top-rated")
    public ResponseEntity<ApiResponse<List<MovieResponse>>> getTopRatedMovies(
            @RequestParam(defaultValue = "5") int limit, HttpServletRequest request) {
        List<MovieResponse> movies = movieService.getTopRatedMoviesResponse(limit);
        return createSuccessResponse("Lấy danh sách phim đánh giá cao thành công", movies, request);
    }

    @Operation(summary = "Thêm phim mới", description = "API thêm phim mới vào hệ thống")
    @PostMapping
    public ResponseEntity<ApiResponse<MovieResponse>> createMovie(
            @Valid @RequestBody CreateMovieRequest movieRequest, HttpServletRequest request) {
        MovieResponse movie = movieService.createMovie(movieRequest);
        return createCreatedResponse("Thêm phim mới thành công", movie, request);
    }

    @Operation(summary = "Cập nhật thông tin phim", description = "API cập nhật thông tin của một phim")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<MovieResponse>> updateMovie(
            @PathVariable Long id, @Valid @RequestBody UpdateMovieRequest movieRequest, HttpServletRequest request) {

        MovieResponse movie = movieService.updateMovie(id, movieRequest);
        return createSuccessResponse("Cập nhật thông tin phim thành công", movie, request);
    }

    @Operation(summary = "Xóa phim", description = "API xóa một phim khỏi hệ thống")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteMovie(@PathVariable Long id, HttpServletRequest request) {
        movieService.deleteMovie(id);
        return createSuccessResponse("Xóa phim thành công", request);
    }
}
