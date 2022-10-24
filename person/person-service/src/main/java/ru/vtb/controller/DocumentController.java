package ru.vtb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.vtb.controller.api.DocumentApi;
import ru.vtb.dto.createInput.DocumentCreateInputDto;
import ru.vtb.dto.getOrUpdate.DocumentDto;
import ru.vtb.service.IDocumentService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class DocumentController implements DocumentApi {
    private final IDocumentService documentService;

    @Override
    public ResponseEntity<DocumentDto> findDocumentById(Long id, Boolean visibility) {
        return ResponseEntity.ok(documentService.findById(id, visibility));
    }

    @Override
    public ResponseEntity<List<DocumentDto>> findAllDocuments(Boolean visibility) {
        return ResponseEntity.ok(documentService.findAllDocuments(visibility));
    }

    @Override
    public ResponseEntity<Map<Long, List<DocumentDto>>> findDocumentsByPersonsId(List<Long> personsId, Boolean visibility) {
        return ResponseEntity.ok(documentService.findAllDocumentsByPersonId(personsId, visibility));
    }

    @Override
    public ResponseEntity<List<DocumentDto>> createDocuments(List<DocumentCreateInputDto> documents) {
        return ResponseEntity.ok(documentService.createListOfDocuments(documents));
    }

    @Override
    public ResponseEntity<List<DocumentDto>> updateDocuments(List<DocumentDto> documents) {
        return ResponseEntity.ok(documentService.updateListOfDocuments(documents));
    }

    @Override
    public ResponseEntity<HttpStatus> setVisibilityByDocuments(List<Long> documentsId, Boolean visibility) {
        documentService.setDocumentsVisibility(documentsId, visibility);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteDocumentsById(List<Long> documentsId) {
        documentService.deleteDocumentsFromDB(documentsId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteAllDocuments() {
        documentService.deleteAllDocuments();
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
