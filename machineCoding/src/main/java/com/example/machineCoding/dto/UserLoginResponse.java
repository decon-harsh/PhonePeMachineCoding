package com.example.machineCoding.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginResponse {
    private UserResponse user;
    private String token;
}
