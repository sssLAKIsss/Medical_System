package ru.vtb.mapper.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import ru.vtb.AbstractTest;
import ru.vtb.dto.createInput.DocumentCreateInputDto;
import ru.vtb.dto.getOrUpdate.DocumentDto;
import ru.vtb.model.Document;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DocumentMapperTest extends AbstractTest {

    private static final String DOCUMENT_PATH = "src/test/resources/ru/vtb/test-document-data.json";

    @Autowired
    protected DocumentMapper documentMapper;

    @Test
    void convertFromCreateDto() throws IOException {
        DocumentCreateInputDto documentCreateInputDto = objectMapper.readValue(
                ResourceUtils.getFile(DOCUMENT_PATH),
                DocumentCreateInputDto.class
        );
        Document document = documentMapper.convertFromCreateDto(documentCreateInputDto);

        assertEquals(documentCreateInputDto.getNumber(), document.getNumber());
        assertEquals(documentCreateInputDto.getType(), document.getType());
        assertTrue(document.isVisibility());
    }

    @Test
    void convertFromUpdateDto() throws IOException {
        DocumentDto documentDto = objectMapper.readValue(
                ResourceUtils.getFile(DOCUMENT_PATH),
                DocumentDto.class
        );
        Document document = documentMapper.convertFromUpdateDto(documentDto);

        assertEquals(documentDto.getNumber(), document.getNumber());
        assertEquals(documentDto.getType(), document.getType());
        assertEquals(documentDto.getId(), document.getId());
        assertEquals(documentDto.getVersion(), document.getVersion());
        assertTrue(document.isVisibility());
    }

    @Test
    void convertToOutputDto() throws IOException {
        Document document = objectMapper.readValue(
                ResourceUtils.getFile(DOCUMENT_PATH),
                Document.class
        );
        DocumentDto documentDto = documentMapper.convertToOutputDto(document);

        assertEquals(document.getNumber(), documentDto.getNumber());
        assertEquals(document.getType(), documentDto.getType());
        assertEquals(document.getId(), documentDto.getId());
        assertEquals(document.getVersion(), documentDto.getVersion());
        assertTrue(documentDto.isVisibility());
    }
}