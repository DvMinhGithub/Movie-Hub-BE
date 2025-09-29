package com.moviehub.moviehub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviehub.moviehub.dto.request.UpdateProfileRequest;
import com.moviehub.moviehub.dto.response.ApiResponse;
import com.moviehub.moviehub.dto.response.UserInfoResponse;
import com.moviehub.moviehub.security.services.UserDetailsImpl;
import com.moviehub.moviehub.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController extends BaseController {
    private final UserService userService;

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<UserInfoResponse>> updateProfile(
            @Valid @RequestBody UpdateProfileRequest updateProfileRequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            HttpServletRequest request) {

        UserInfoResponse userInfo = userService.updateUser(userDetails.getId(), updateProfileRequest);
        return createSuccessResponse("Cập nhật thông tin cá nhân thành công!", userInfo, request);
    }
}
