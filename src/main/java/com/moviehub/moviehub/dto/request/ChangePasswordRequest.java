package com.moviehub.moviehub.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    @NotBlank(message = "Mật khẩu hiện tại không được để trống")
    @Size(min = 6, max = 16, message = "Mật khẩu hiện tại phải từ 6-16 ký tự")
    @Schema(description = "Mật khẩu hiện tại của người dùng", example = "passrd123")
    private String currentPassword;

    @NotBlank(message = "Mật khẩu mới không được để trống")
    @Size(min = 6, max = 16, message = "Mật khẩu mới phải từ 6-16 ký tự")
    @Schema(description = "Mật khẩu mới của người dùng", example = "newpassword123")
    private String newPassword;
}
