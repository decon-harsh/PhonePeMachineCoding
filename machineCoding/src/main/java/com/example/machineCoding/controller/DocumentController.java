package com.example.machineCoding.controller;

import com.example.machineCoding.domain.document.Document;
import com.example.machineCoding.domain.document.DocumentRequest;
import com.example.machineCoding.domain.document.DocumentSnapshot;
import com.example.machineCoding.service.DocumentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @GetMapping()
    public Set<Document> getAllDocumentByALoggedInUser(@RequestParam String token) {
        return documentService.getAllDocumentByALoggedInUser(token);
    }

    @GetMapping("/{documentId}")
    public Document getDocumentById(@RequestParam String token,@PathVariable Integer documentId) {
        return documentService.getDocumentById(token,documentId);
    }

    @PatchMapping("/{documentId}")
    public Document updateDocumentById(@PathVariable Integer documentId, @RequestBody @Valid DocumentRequest documentRequest) {
        return documentService.updateDocumentById(documentRequest.getToken(),documentId,documentRequest);
    }

    @PostMapping("/create")
    public Document createNewDocument(@RequestBody @Valid DocumentRequest documentRequest) {
        return documentService.createNewDocument(documentRequest);
    }

    @GetMapping("/{documentId}/history/{snapshotId}")
    public DocumentSnapshot getDocumentById(@RequestParam String token, @PathVariable Integer documentId, @PathVariable UUID snapshotId) {
        return documentService.getDocumentSnapshotBySnapshotId(token,documentId,snapshotId);
    }
}
