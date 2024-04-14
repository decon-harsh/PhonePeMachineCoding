package com.example.machineCoding.service.impl;

import com.example.machineCoding.domain.User;
import com.example.machineCoding.dto.*;
import com.example.machineCoding.exception.BadRequestException;
import com.example.machineCoding.exception.EntityNotFoundException;
import com.example.machineCoding.exception.UnauthorizedException;
import com.example.machineCoding.mapper.UserMapper;
import com.example.machineCoding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Predicate;

import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());

            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    List<User> allUsers;
    Map<String,User> loggedInUsers = new HashMap<>();

    public UserServiceImpl() {
        allUsers = generateRandomUsers(5);
    }

    public User getUserById(Integer userId) {
        return allUsers.stream().filter(user -> userId.equals(user.getId()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("User not found with UserId : " + userId));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return allUsers.stream().map(userMapper::convert).collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserResponseById(Integer userId) {
        User userEntity = getUserById(userId);
        return userMapper.convert(userEntity);
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        userRequest.setPassword(hashPassword(userRequest.getPassword())); // hash the password
        User user = userMapper.convert(userRequest);
        user.setId(allUsers.size()+1);
        allUsers.add(user);
        return userMapper.convert(user);
    }

    @Override
    public UserLoginResponse loginUser(UserLoginRequest userLoginRequest) {
        User userEntity = getUserById(userLoginRequest.getId());
        if (userEntity.getPassword().equals(hashPassword(userLoginRequest.getPassword()))) {
            String token = UUID.randomUUID().toString();
            loggedInUsers.put(token,userEntity);
            return new UserLoginResponse(userMapper.convert(userEntity),token);
        }
        throw new UnauthorizedException("Invalid credentials for userId: " + userEntity.getId()); // Or you can throw a more specific exception
    }

    @Override
    public Boolean authenticateLoggedInUser(String token) {
        return loggedInUsers.containsKey(token);
    }

    @Override
    public Optional<UserResponse> authenticateAndReturnLoggedInUser(String token) {
        if(authenticateLoggedInUser(token)) {
            return Optional.of(userMapper.convert(loggedInUsers.get(token)));
        }
        return Optional.empty();
    }


    // Method to generate random users
    private List<User> generateRandomUsers(int count) {
        List<User> users = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setId(i+1);
            user.setFirstName("UserFirst" + (i + 1));
            user.setLastName("UserLast" + (i + 1));
            user.setEmail("UserEmail" + (i+1) + "@gmail.com");
            user.setPassword(hashPassword("Pass"+(i+1)));
            users.add(user);
        }

        return users;
    }
}
