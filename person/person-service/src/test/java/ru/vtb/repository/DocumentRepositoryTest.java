package ru.vtb.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ru.vtb.TestContainerSetup;
import ru.vtb.model.Document;
import ru.vtb.model.superclass.BaseDateVersionEntity;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.*;

@Sql(scripts = "sql/init-document-data-to-db.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "sql/drop-document-data-in-db.sql", executionPhase = AFTER_TEST_METHOD)
class DocumentRepositoryTest extends TestContainerSetup {
    @Autowired
    protected DocumentRepository documentRepository;

    @Test
    void findDocumentById_trueVisibility() {
        Document document = documentRepository.findDocumentById(1L, true).orElse(null);
        assertNotNull(document);
    }

    @Test
    void findDocumentByNumber_falseVisibility() {
        Document document = documentRepository.findDocumentById(1L, false).orElse(null);
        assertThat(document).isNull();
    }

    @Test
    void findAllDocumentsByVisibility_ignoreVisibility() {
        List<Document> documents = documentRepository.findAllDocumentsByVisibility(null);
        assertThat(documents).hasSize(3);
    }

    @Test
    void findAllDocumentsByVisibility_trueVisibility() {
        List<Document> documents = documentRepository.findAllDocumentsByVisibility(true);
        assertThat(documents).hasSize(2);
        assertTrue(documents.stream().allMatch(BaseDateVersionEntity::isVisibility));
    }

    @Test
    void findAllDocumentsByPersonsId_ignoreVisibility() {
        List<Document> documents = documentRepository.findAllDocumentsByPersonsId(List.of(1L, 2L), null);
        assertThat(documents).hasSize(2);
    }

    @Test
    void findAllDocumentsByPersonsId_trueVisibility() {
        List<Document> documents = documentRepository.findAllDocumentsByPersonsId(List.of(1L, 2L), null);
        assertThat(documents).hasSize(2);
        assertTrue(documents.stream().allMatch(BaseDateVersionEntity::isVisibility));
    }

    @Test
    void setVisibilityToDocuments() {
        documentRepository.setVisibilityToDocuments(false, List.of(1L));
        assertFalse(documentRepository.findDocumentById(1L, null).orElseThrow().isVisibility());
    }

    @Test
    void findAllDocumentsByPersonIdAAndVisibility() {
        List<Document> documents = documentRepository.findAllDocumentsByPersonIdAAndVisibility(1L, null);
        assertNotNull(documents);
        assertFalse(documents.isEmpty());
        assertThat(documents).hasSize(2);
    }

    @Test
    void deleteDocumentsById() {
        List<Document> documents = documentRepository.findAllDocumentsByVisibility(null);
        assertThat(documents).hasSize(3);

        documentRepository.deleteDocumentsById(Set.of(1L, 2L));

        documents = documentRepository.findAllDocumentsByVisibility(null);
        assertThat(documents).hasSize(1);
    }

    @Test
    void deleteAllDocuments() {
        documentRepository.deleteAllDocuments();
        assertThat(documentRepository.findAllDocumentsByVisibility(null)).hasSize(0);
    }
}