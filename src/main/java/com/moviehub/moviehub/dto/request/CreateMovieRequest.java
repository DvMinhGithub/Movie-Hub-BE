package com.moviehub.moviehub.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateMovieRequest {
    @NotBlank(message = "Tiêu đề phim không được để trống")
    @Size(min = 1, max = 255, message = "Tiêu đề phim phải từ 1-255 ký tự")
    private String title;

    @Size(max = 2000, message = "Mô tả không được vượt quá 2000 ký tự")
    private String description;

    @NotNull(message = "Thời lượng phim không được để trống")
    @Positive(message = "Thời lượng phải là số dương (phút)")
    private Integer duration;

    @NotBlank(message = "Thể loại phim không được để trống")
    @Size(min = 1, max = 100, message = "Thể loại phim phải từ 1-100 ký tự")
    private String genre;

    @NotBlank(message = "Tên đạo diễn không được để trống")
    @Size(min = 1, max = 100, message = "Tên đạo diễn phải từ 1-100 ký tự")
    private String director;

    @NotBlank(message = "Ngôn ngữ phim không được để trống")
    @Size(min = 1, max = 50, message = "Ngôn ngữ phải từ 1-50 ký tự")
    private String language;

    @NotBlank(message = "Danh sách diễn viên chính không được để trống")
    @Size(min = 1, max = 500, message = "Danh sách diễn viên chính phải từ 1-500 ký tự")
    private String actors;

    @Size(max = 1000, message = "Danh sách cast không được vượt quá 1000 ký tự")
    private String cast;

    @PastOrPresent(message = "Ngày phát hành không được là tương lai")
    private LocalDate releaseDate;

    @Size(max = 500, message = "URL poster không được vượt quá 500 ký tự")
    private String posterUrl;

    @Builder.Default
    private Boolean isActive = true;
}
