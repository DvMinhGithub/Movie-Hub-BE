package com.moviehub.moviehub.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviehub.moviehub.dto.request.ChangePasswordRequest;
import com.moviehub.moviehub.dto.request.LoginRequest;
import com.moviehub.moviehub.dto.request.RegisterRequest;
import com.moviehub.moviehub.dto.response.ApiResponse;
import com.moviehub.moviehub.dto.response.JwtResponse;
import com.moviehub.moviehub.dto.response.UserInfoResponse;
import com.moviehub.moviehub.security.services.UserDetailsImpl;
import com.moviehub.moviehub.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController extends BaseController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> authenticateUser(
            @Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request) {

        JwtResponse jwtResponse = authService.login(loginRequest);
        return createSuccessResponse("Đăng nhập thành công!", jwtResponse, request);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserInfoResponse>> registerUser(
            @Valid @RequestBody RegisterRequest registerRequest, HttpServletRequest request) {

        UserInfoResponse userInfo = authService.register(registerRequest);
        return createCreatedResponse("Đăng ký tài khoản thành công!", userInfo, request);
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserInfoResponse>> getUserProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails, HttpServletRequest request) {

        UserInfoResponse userInfo = authService.getCurrentUserProfile(userDetails.getId());
        return createSuccessResponse("Lấy thông tin người dùng thành công", userInfo, request);
    }

    @PutMapping("/change-password")
    public ResponseEntity<ApiResponse<Object>> changePassword(
            @Valid @RequestBody ChangePasswordRequest changePasswordRequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            HttpServletRequest request) {

        authService.changePassword(userDetails.getId(), changePasswordRequest);
        return createSuccessResponse("Đổi mật khẩu thành công!", null, request);
    }
}
