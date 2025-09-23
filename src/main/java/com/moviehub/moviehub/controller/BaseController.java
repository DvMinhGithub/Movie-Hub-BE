package com.moviehub.moviehub.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.moviehub.moviehub.dto.response.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;

public abstract class BaseController {

    protected <T> ResponseEntity<ApiResponse<T>> createSuccessResponse(
            String message, T data, HttpServletRequest request) {
        ApiResponse<T> response = ApiResponse.success(message, data, request.getServletPath());
        return ResponseEntity.ok(response);
    }

    protected ResponseEntity<ApiResponse<Object>> createSuccessResponse(String message, HttpServletRequest request) {
        return createSuccessResponse(message, null, request);
    }

    protected <T> ResponseEntity<ApiResponse<T>> createCreatedResponse(
            String message, T data, HttpServletRequest request) {
        ApiResponse<T> response = ApiResponse.success(message, data, request.getServletPath());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    protected ResponseEntity<Void> createNoContentResponse() {
        return ResponseEntity.noContent().build();
    }

    protected ResponseEntity<ApiResponse<Object>> createAcceptedResponse(String message, HttpServletRequest request) {
        ApiResponse<Object> response = ApiResponse.success(message, null, request.getServletPath());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
