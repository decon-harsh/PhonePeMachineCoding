package com.example.machineCoding.service;

import com.example.machineCoding.domain.document.Document;
import com.example.machineCoding.domain.document.DocumentRequest;
import com.example.machineCoding.domain.document.DocumentSnapshot;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface DocumentService {
    Set<Document> getAllDocumentByALoggedInUser(String token);

    Document createNewDocument(DocumentRequest documentRequest);

    Document getDocumentById(String token, Integer documentId);

    DocumentSnapshot getDocumentSnapshotBySnapshotId(String token, Integer documentId, UUID snapshotId);

    Document updateDocumentById(String token, Integer documentId, DocumentRequest documentRequest);
}
