package com.example.machineCoding.domain.document;

import com.example.machineCoding.domain.User;
import lombok.Data;

import java.util.Set;

@Data
public class DocumentRequest {
    private String content;
    private String token;
}
