package com.example.machineCoding.domain.document;

import com.example.machineCoding.domain.BaseEntity;
import com.example.machineCoding.domain.User;
import com.example.machineCoding.dto.UserResponse;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class Document  extends BaseEntity {

    private String content;

    private Integer version;

    private UserResponse author;

    Set<DocumentSnapshot> history;
}
