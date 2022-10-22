package ru.vtb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.dto.createInput.DocumentCreateInputDto;
import ru.vtb.dto.getOrUpdate.DocumentDto;
import ru.vtb.exception.DocumentNotFoundException;
import ru.vtb.mapper.IModelMapper;
import ru.vtb.model.Document;
import ru.vtb.repository.DocumentRepository;
import ru.vtb.service.IDocumentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements IDocumentService {
    private final DocumentRepository documentRepository;
    private final IModelMapper<Document, DocumentCreateInputDto, DocumentDto> documentMapper;

    @Override
    @Transactional(readOnly = true)
    public DocumentDto findById(Long id, Boolean visibility) {
        return documentMapper.convertToOutputDto(
                documentRepository.findDocumentById(id, visibility)
                        .orElseThrow(DocumentNotFoundException::new)
        );
    }

    @Override
    @Transactional
    public List<DocumentDto> findAllDocuments(Boolean visibility) {
        return documentRepository.findAllDocumentsByVisibility(visibility)
                .stream()
                .map(documentMapper::convertToOutputDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<DocumentDto> findAllDocumentsByPersonsId(List<Long> personsId, Boolean visibility) {
        return documentRepository.findAllDocumentsByPersonsId(personsId, visibility)
                .stream()
                .map(documentMapper::convertToOutputDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<DocumentDto> createListOfDocuments(List<DocumentCreateInputDto> documents) {
        return documentRepository.saveAll(
                        documents.stream()
                                .map(documentMapper::convertFromCreateDto).collect(Collectors.toList()))
                .stream()
                .map(documentMapper::convertToOutputDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<DocumentDto> updateListOfDocuments(List<DocumentDto> documents) {
        return documentRepository.saveAll(
                        documents.stream().map(documentMapper::convertFromUpdateDto).collect(Collectors.toList())
                )
                .stream()
                .map(documentMapper::convertToOutputDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void setDocumentsVisibility(List<Long> documentsId, boolean visibility) {
        documentRepository.setVisibilityToDocuments(visibility, documentsId);
    }

    @Override
    @Transactional
    public void deleteDocumentsFromDB(List<Long> documentsId) {
        documentRepository.deleteAllById(documentsId);
    }

    @Override
    @Transactional
    public void deleteAllDocuments() {
        documentRepository.deleteAll();
    }
}
