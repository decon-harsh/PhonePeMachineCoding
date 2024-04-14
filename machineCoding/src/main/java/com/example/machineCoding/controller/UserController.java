package com.example.machineCoding.controller;

import com.example.machineCoding.domain.User;
import com.example.machineCoding.dto.*;
import com.example.machineCoding.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public UserResponse getById(@PathVariable Integer userId) {
        return userService.getUserResponseById(userId);
    }

    @PostMapping("/create")
    public UserResponse createUser( @Valid @RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @PostMapping("/login")
    public UserLoginResponse loginUser(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        return userService.loginUser(userLoginRequest);
    }

    @PostMapping("/authenticate")
    public Boolean authenticateLoggedInUser(@RequestParam String token) {
        return userService.authenticateLoggedInUser(token);
    }
}
