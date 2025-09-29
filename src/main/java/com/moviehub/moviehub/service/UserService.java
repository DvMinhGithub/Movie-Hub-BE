package com.moviehub.moviehub.service;

import java.util.Optional;

import com.moviehub.moviehub.dto.request.UpdateProfileRequest;
import com.moviehub.moviehub.dto.response.UserInfoResponse;
import com.moviehub.moviehub.model.User;

public interface UserService {

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    User save(User user);

    UserInfoResponse convertToUserInfoResponse(User user);

    UserInfoResponse updateUser(Long userId, UpdateProfileRequest request);

    void deleteUser(Long userId);
}
