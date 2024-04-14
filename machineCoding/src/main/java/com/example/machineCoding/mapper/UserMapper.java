package com.example.machineCoding.mapper;

import com.example.machineCoding.domain.User;
import com.example.machineCoding.dto.UserRequest;
import com.example.machineCoding.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User convert(UserRequest request);

    @Mapping(source = "id",target = "id")
    UserResponse convert(User entity);
}
