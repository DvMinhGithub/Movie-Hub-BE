package com.moviehub.moviehub.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moviehub.moviehub.dto.response.UserInfoResponse;
import com.moviehub.moviehub.exception.ResourceNotFoundException;
import com.moviehub.moviehub.mapper.UserMapper;
import com.moviehub.moviehub.model.User;
import com.moviehub.moviehub.repository.UserRepository;
import com.moviehub.moviehub.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserInfoResponse convertToUserInfoResponse(User user) {
        return userMapper.toUserInfoResponse(user);
    }

    @Override
    public User updateUser(Long userId, User updatedUser) {
        User existingUser = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không tồn tại với ID: " + userId));

        existingUser.setFullName(updatedUser.getFullName());
        existingUser.setPhone(updatedUser.getPhone());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Người dùng không tồn tại với ID: " + userId);
        }
        userRepository.deleteById(userId);
    }
}
