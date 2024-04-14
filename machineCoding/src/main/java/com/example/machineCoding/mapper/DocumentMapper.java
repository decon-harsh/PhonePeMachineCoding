package com.example.machineCoding.mapper;

import com.example.machineCoding.domain.document.Document;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocumentMapper {
    Document clone(Document source);
}
