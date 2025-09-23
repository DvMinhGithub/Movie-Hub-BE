package com.moviehub.moviehub.service;

import java.util.Optional;

import com.moviehub.moviehub.dto.response.UserInfoResponse;
import com.moviehub.moviehub.model.User;

public interface UserService {

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    User save(User user);

    UserInfoResponse convertToUserInfoResponse(User user);

    User updateUser(Long userId, User updatedUser);

    void deleteUser(Long userId);
}
