package ru.vtb.service;



import ru.vtb.dto.createInput.DocumentCreateInputDto;
import ru.vtb.dto.getOrUpdate.DocumentDto;

import java.util.List;

public interface IDocumentService {
    DocumentDto findById(Long id, Boolean visibility);
    List<DocumentDto> findAllDocuments(Boolean visibility);
    List<DocumentDto> findAllDocumentsByPersonsId(List<Long> personsId, Boolean visibility);

    List<DocumentDto> createListOfDocuments(List<DocumentCreateInputDto> documents);
    List<DocumentDto> updateListOfDocuments(List<DocumentDto> documents);

    void setDocumentsVisibility(List<Long> documentsId, boolean visibility);
    void deleteDocumentsFromDB(List<Long> documentsId);
    void deleteAllDocuments();
}
