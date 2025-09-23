package com.moviehub.moviehub.service;

import com.moviehub.moviehub.dto.request.ChangePasswordRequest;
import com.moviehub.moviehub.dto.request.LoginRequest;
import com.moviehub.moviehub.dto.request.RegisterRequest;
import com.moviehub.moviehub.dto.response.JwtResponse;
import com.moviehub.moviehub.dto.response.UserInfoResponse;

public interface AuthService {

    JwtResponse login(LoginRequest loginRequest);

    UserInfoResponse register(RegisterRequest registerRequest);

    UserInfoResponse getCurrentUserProfile(Long userId);

    void changePassword(Long userId, ChangePasswordRequest changePasswordRequest);
}
