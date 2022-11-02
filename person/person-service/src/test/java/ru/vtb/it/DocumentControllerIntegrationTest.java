package ru.vtb.it;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ru.vtb.IntegrationAbstractTest;
import ru.vtb.exception.AbstractLocalizedException;
import ru.vtb.repository.DocumentRepository;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = "sql/document/prepare-document-data.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "sql/document/drop-document-data.sql", executionPhase = AFTER_TEST_METHOD)
class DocumentControllerIntegrationTest extends IntegrationAbstractTest {

    private static final String GET_ALL_DOCUMENTS_DATA_PATH = "src/test/resources/ru/vtb/json/document/documents-data.json";
    private static final String GET_DOCUMENT_BY_ID_PATH = "src/test/resources/ru/vtb/json/document/document-data.json";
    private static final String GET_DOCUMENTS_BY_PERSON_ID_DATA_PATH = "src/test/resources/ru/vtb/json/document/documents-data-with-personId.json";
    private static final String SAVE_DOCUMENTS_DATA_PATH = "src/test/resources/ru/vtb/json/document/documents-data-save.json";
    private static final String UPDATE_DOCUMENTS_DATA_PATH = "src/test/resources/ru/vtb/json/document/document-data-update.json";

    @Autowired
    protected DocumentRepository documentRepository;

    @Test
    void findDocumentById_shouldReturnCDocument() throws Exception {
        mockMvc.perform(get("/api/v1/documents/1").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonDataByFilePath(GET_DOCUMENT_BY_ID_PATH)));
    }

    @Test
    void findDocumentById_shouldReturnNotFoundException() throws Exception {
        mockMvc.perform(get("/api/v1/documents/11").contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AbstractLocalizedException))
                .andExpect(content().string(containsString("Такой документ не найден")));
    }

    @Test
    void findAllDocuments_shouldReturnListOfDocuments() throws Exception {
        mockMvc.perform(get("/api/v1/documents").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonDataByFilePath(GET_ALL_DOCUMENTS_DATA_PATH)));
    }

    @Test
    void findAllDocuments_shouldReturnEmptyList() throws Exception {
        documentRepository.deleteAllDocuments();
        mockMvc.perform(get("/api/v1/documents").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void findDocumentsByPersonsId_shouldReturnListOfDocuments() throws Exception {
        mockMvc.perform(get("/api/v1/documents/personsId?personsId=1").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonDataByFilePath(GET_DOCUMENTS_BY_PERSON_ID_DATA_PATH)));
    }

    @Test
    void findDocumentsByPersonsId_shouldReturnEmptyList() throws Exception {
        documentRepository.deleteAllDocuments();
        mockMvc.perform(get("/api/v1/documents/personsId?personsId=1").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"1\":[]}"));
    }

    @Test
    void createDocuments_shouldReturnListOfDocumentsId() throws Exception {
        mockMvc.perform(post("/api/v1/documents")
                        .contentType(APPLICATION_JSON)
                        .content(getJsonDataByFilePath(SAVE_DOCUMENTS_DATA_PATH)))
                .andExpect(status().isOk())
                .andExpect(content().json("[3,4]"));
        mockMvc.perform(get("/api/v1/documents/3").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.type", is("PASSPORT")))
                .andExpect(jsonPath("$.number", is("4545001006")));
    }

    @Test
    void updateDocuments_shouldReturnListOfDocumentsId() throws Exception {
        mockMvc.perform(put("/api/v1/documents")
                        .contentType(APPLICATION_JSON)
                        .content(getJsonDataByFilePath(UPDATE_DOCUMENTS_DATA_PATH)))
                .andExpect(status().isOk())
                .andExpect(content().json("[1,2]"));
        mockMvc.perform(get("/api/v1/documents/1").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.type", is("SNILS")));
    }

    @Test
    void setVisibilityByDocuments_shouldReturn200Status() throws Exception {
        mockMvc.perform(put("/api/v1/documents/visibility?documentsId=1&visibility=false")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/documents/1").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.visibility", is(false)));
    }

    @Test
    void deleteDocumentsById_shouldReturn200Status() throws Exception {
        mockMvc.perform(delete("/api/v1/documents/documentsId?documentsId=1")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/documents/1").contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AbstractLocalizedException));
    }

    @Test
    void deleteAllDocuments_shouldReturn200Status() throws Exception {
        mockMvc.perform(delete("/api/v1/documents").contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/documents").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
