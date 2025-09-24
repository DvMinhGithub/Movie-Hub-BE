package com.moviehub.moviehub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {

    @NotBlank(message = "Họ tên không được để trống")
    @Size(min = 2, max = 100, message = "Họ tên phải từ 2-100 ký tự")
    @Pattern(regexp = "^[\\p{L}\\s]+$", message = "Họ tên chỉ được chứa chữ cái và khoảng trắng")
    private String fullName;

    @Size(max = 15, message = "Số điện thoại không được quá 15 ký tự")
    @Pattern(regexp = "^[0-9+\\-\\s]*$", message = "Số điện thoại không đúng định dạng")
    private String phone;
}
