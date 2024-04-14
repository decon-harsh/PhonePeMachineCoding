package com.example.machineCoding.service;

import com.example.machineCoding.domain.User;
import com.example.machineCoding.dto.*;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserResponse> getAllUsers();

    UserResponse getUserResponseById(Integer userId);

    UserResponse createUser(UserRequest userRequest);

    UserLoginResponse loginUser(UserLoginRequest userLoginRequest);

    Boolean authenticateLoggedInUser(String token);

    Optional<UserResponse> authenticateAndReturnLoggedInUser(String token);
}
