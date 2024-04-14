package com.example.machineCoding.domain.document;

import com.example.machineCoding.domain.BaseEntity;
import com.example.machineCoding.dto.UserResponse;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@AllArgsConstructor
public class DocumentSnapshot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID documentSnapshotId;

    private String content;
    private Integer version;
    private UserResponse author;
}
