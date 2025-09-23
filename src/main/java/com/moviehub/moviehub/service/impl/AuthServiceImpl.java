package com.moviehub.moviehub.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moviehub.moviehub.dto.request.ChangePasswordRequest;
import com.moviehub.moviehub.dto.request.LoginRequest;
import com.moviehub.moviehub.dto.request.RegisterRequest;
import com.moviehub.moviehub.dto.response.JwtResponse;
import com.moviehub.moviehub.dto.response.UserInfoResponse;
import com.moviehub.moviehub.exception.BadRequestException;
import com.moviehub.moviehub.mapper.UserMapper;
import com.moviehub.moviehub.model.User;
import com.moviehub.moviehub.security.jwt.JwtUtils;
import com.moviehub.moviehub.service.AuthService;
import com.moviehub.moviehub.service.UserService;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserMapper userMapper;

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        String jwt = jwtUtils.generateJwtToken(authentication);

        return JwtResponse.builder()
                .accessToken(jwt)
                .expiresIn(jwtUtils.getJwtExpirationMs())
                .build();
    }

    @Override
    public UserInfoResponse register(RegisterRequest registerRequest) {
        if (userService.existsByEmail(registerRequest.getEmail())) {
            throw new BadRequestException("Email đã được sử dụng bởi tài khoản khác");
        }

        User user = userMapper.toUser(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        User savedUser = userService.save(user);

        return userMapper.toUserInfoResponse(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserInfoResponse getCurrentUserProfile(Long userId) {
        User user = userService.findById(userId).orElseThrow(() -> new BadRequestException("Người dùng không tồn tại"));

        return userMapper.toUserInfoResponse(user);
    }

    @Override
    public void changePassword(Long userId, ChangePasswordRequest changePasswordRequest) {
        User user = userService.findById(userId).orElseThrow(() -> new BadRequestException("Người dùng không tồn tại"));

        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
            throw new BadRequestException("Mật khẩu hiện tại không chính xác");
        }

        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userService.save(user);
    }
}
