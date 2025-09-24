package com.moviehub.moviehub.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    @Size(max = 50, message = "Email không được vượt quá 50 ký tự")
    @Schema(description = "Địa chỉ email của người dùng", example = "user@example.com")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, max = 16, message = "Mật khẩu phải từ 6-16 ký tự")
    @Schema(description = "Mật khẩu của người dùng", example = "password123")
    private String password;

    @NotBlank(message = "Họ tên không được để trống")
    @Size(max = 100, message = "Họ tên không được vượt quá 100 ký tự")
    @Schema(description = "Họ tên của người dùng", example = "Nguyễn Văn A")
    private String fullName;

    @Size(max = 10, message = "Số điện thoại không được vượt quá 10 ký tự")
    @Schema(description = "Số điện thoại của người dùng", example = "0123456789")
    private String phone;
}
