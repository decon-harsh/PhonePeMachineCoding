package com.example.machineCoding.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserLoginRequest {
    @NotNull
    private Integer id;
    @NotNull
    private String password;
}
