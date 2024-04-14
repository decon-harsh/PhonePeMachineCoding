package com.example.machineCoding.service.impl;

import com.example.machineCoding.domain.document.Document;
import com.example.machineCoding.domain.document.DocumentRequest;
import com.example.machineCoding.domain.document.DocumentSnapshot;
import com.example.machineCoding.dto.UserResponse;
import com.example.machineCoding.exception.EntityNotFoundException;
import com.example.machineCoding.exception.UnauthorizedException;
import com.example.machineCoding.service.DocumentService;
import com.example.machineCoding.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private UserService userService;

    List<Document> allDocuments = new ArrayList<>();


    private UserResponse authenticateLoggedInUser(String token) {
        //check for logged in User
        return userService.authenticateAndReturnLoggedInUser(token)
                .orElseThrow(() -> new EntityNotFoundException("User not logged in for token: " + token));
    }

    @Override
    public Set<Document> getAllDocumentByALoggedInUser(String token) {
        UserResponse userResponse = authenticateLoggedInUser(token);

        return allDocuments.stream()
                .filter(document -> document.getAuthor().getId() == userResponse.getId())
                .collect(Collectors.toSet());
    }

    @Override
    public Document createNewDocument(DocumentRequest documentRequest) {
        UserResponse userResponse = authenticateLoggedInUser(documentRequest.getToken());

        try {
            Document document = new Document();
            DocumentSnapshot documentSnapshot = new DocumentSnapshot(UUID.randomUUID(),documentRequest.getContent(), 0, userResponse);
            Set<DocumentSnapshot> history = Collections.singleton(documentSnapshot);
            document.setId(allDocuments.size() + 1);
            document.setContent(documentRequest.getContent());
            document.setAuthor(userResponse);
            document.setVersion(0);
            document.setHistory(history);
            document.setCreatedAt(Instant.now());
            document.setUpdatedAt(Instant.now());
            allDocuments.add(document);

            return document;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Document getDocumentById(String token, Integer documentId) {
        UserResponse userResponse = authenticateLoggedInUser(token);
        Document documentEntity = allDocuments.stream().filter(document -> document.getId().equals(documentId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Document not found with DocumentId : " + documentId));

        return documentEntity;
//        if(documentEntity.getAuthor().equals(userResponse)) {
//            return documentEntity;
//        }
//        throw new UnauthorizedException("User not permitted to see the document with DocumentId : " + documentId);
    }

    @Override
    public DocumentSnapshot getDocumentSnapshotBySnapshotId(String token, Integer documentId, UUID snapshotId) {
        Document document = getDocumentById(token,documentId);
        return document.getHistory().stream().filter(snapshot ->
            snapshot.getDocumentSnapshotId().equals(snapshotId)
        ).findFirst().orElseThrow(()->new EntityNotFoundException("Document History not found with SnapshotId: " + snapshotId));
    }

    @Override
    public Document updateDocumentById(String token, Integer documentId, DocumentRequest documentRequest) {
        UserResponse userResponse = authenticateLoggedInUser(token);
        Document document = getDocumentById(token,documentId);
        if(documentRequest.getContent() != null && !document.getContent().equals(documentRequest.getContent())) {
            DocumentSnapshot documentSnapshot = new DocumentSnapshot(UUID.randomUUID(),documentRequest.getContent(),document.getVersion()+1,userResponse);
            Set<DocumentSnapshot> updatedHistory = new HashSet<>(document.getHistory());
            updatedHistory.add(documentSnapshot);
            document.setHistory(updatedHistory);
            document.setVersion(documentSnapshot.getVersion());
            document.setUpdatedAt(Instant.now());
            document.setContent(documentRequest.getContent());
        }

        return document;
    }
}
