package com.moviehub.moviehub.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import com.moviehub.moviehub.dto.request.RegisterRequest;
import com.moviehub.moviehub.dto.response.UserInfoResponse;
import com.moviehub.moviehub.model.User;
import com.moviehub.moviehub.model.enums.ERole;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "role", expression = "java(user.getRole().name())")
    UserInfoResponse toUserInfoResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", expression = "java(com.moviehub.moviehub.model.enums.ERole.USER)")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toUser(RegisterRequest registerRequest);

    @Mapping(target = "role", source = "role")
    UserInfoResponse toUserInfoResponseWithRole(User user, String role);

    default String mapRole(ERole role) {
        return role != null ? role.name() : null;
    }
}
