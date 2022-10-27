package ru.vtb.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.vtb.dto.createInput.DocumentCreateInputDto;
import ru.vtb.dto.getOrUpdate.DocumentDto;
import ru.vtb.model.Document;
import ru.vtb.repository.DocumentRepository;
import ru.vtb.AbstractTest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DocumentServiceImplTest extends AbstractTest {
    @MockBean
    private DocumentRepository documentRepository;

    @Autowired
    private DocumentServiceImpl documentService;

    @Test
    void findById() {
        //when
        when(documentRepository.findDocumentById(any(), any()))
                .thenReturn(Optional.ofNullable(Document.builder().build()));
        //then
        DocumentDto documentDto = documentService.findById(1L, true);

        assertNotNull(documentDto);
        verify(documentRepository, times(1))
                .findDocumentById(any(), any());
    }

    @Test
    void findAllDocuments() {
        //when
        when(documentRepository.findAllDocumentsByVisibility(any()))
                .thenReturn(List.of(Document.builder().build()));
        //then
        List<DocumentDto> documentDtos = documentService.findAllDocuments(null);

        assertNotNull(documentDtos);
        assertFalse(documentDtos.isEmpty());
        verify(documentRepository, times(1))
                .findAllDocumentsByVisibility(any());
    }

    @Test
    void findAllDocumentsByPersonsId() {
        //when
        when(documentRepository.findAllDocumentsByPersonIdAAndVisibility(any(), any()))
                .thenReturn(List.of(Document.builder().build()));
        //then
        Map<Long, List<DocumentDto>> resultMap = documentService.findAllDocumentsByPersonId(List.of(1L, 2L, 3L), true);

        assertNotNull(resultMap);
        assertFalse(resultMap.isEmpty());
        verify(documentRepository, times(3))
                .findAllDocumentsByPersonIdAAndVisibility(any(), any());
    }

    @Test
    void createListOfDocuments() {
        //when
        when(documentRepository.saveAll(any()))
                .thenReturn(List.of(Document.builder().build()));
        //then
        List<Long> documentDtos = documentService.createListOfDocuments(List.of(DocumentCreateInputDto.builder().build()));

        assertNotNull(documentDtos);
        assertFalse(documentDtos.isEmpty());
        verify(documentRepository, times(1))
                .saveAll(any());
    }

    @Test
    void updateListOfDocuments() {
        //when
        when(documentRepository.saveAll(any()))
                .thenReturn(List.of(Document.builder().build()));
        //then
        List<Long> documentDtos = documentService.updateListOfDocuments(List.of(DocumentDto.builder().build()));

        assertNotNull(documentDtos);
        assertFalse(documentDtos.isEmpty());
        verify(documentRepository, times(1))
                .saveAll(any());
    }
}