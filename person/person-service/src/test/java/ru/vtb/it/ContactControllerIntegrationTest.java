package ru.vtb.it;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ru.vtb.IntegrationAbstractTest;
import ru.vtb.exception.AbstractLocalizedException;
import ru.vtb.repository.ContactRepository;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
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

@Sql(scripts = "sql/contact/prepare-contact-data.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "sql/contact/drop-contact-data.sql", executionPhase = AFTER_TEST_METHOD)
class ContactControllerIntegrationTest extends IntegrationAbstractTest {

    private static final String GET_ALL_CONTACTS_DATA_PATH = "src/test/resources/ru/vtb/json/contact/contacts-data.json";
    private static final String GET_CONTACT_BY_ID_DATA_PATH = "src/test/resources/ru/vtb/json/contact/contact-data.json";
    private static final String GET_CONTACTS_BY_PERSON_ID_DATA_PATH = "src/test/resources/ru/vtb/json/contact/contacts-data-with-personId.json";
    private static final String SAVE_CONTACTS_DATA_PATH = "src/test/resources/ru/vtb/json/contact/contacts-data-save.json";
    private static final String UPDATE_CONTACTS_DATA_PATH = "src/test/resources/ru/vtb/json/contact/contacts-data-update.json";

    @Autowired
    protected ContactRepository contactRepository;

    @Test
    void findContactById_shouldReturnContact() throws Exception {
        mockMvc.perform(get("/api/v1/contacts/1").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonDataByFilePath(GET_CONTACT_BY_ID_DATA_PATH)));
    }

    @Test
    void findContactById_shouldReturnNotFoundException() throws Exception {
        mockMvc.perform(get("/api/v1/contacts/6").contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("Такой контакт не найден")))
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof AbstractLocalizedException));
    }

    @Test
    void findAllContacts_shouldReturnContactList() throws Exception {
        mockMvc.perform(get("/api/v1/contacts").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonDataByFilePath(GET_ALL_CONTACTS_DATA_PATH)));
    }

    @Test
    void findAllContacts_shouldReturnEmptyList() throws Exception {
        contactRepository.deleteAll();
        mockMvc.perform(get("/api/v1/contacts").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void findContactsByPersonsId_shouldReturnEmptyList() throws Exception {
        contactRepository.deleteAll();
        mockMvc.perform(get("/api/v1/contacts/personsId?personsId=1").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"1\":[]}"));
    }

    @Test
    void findContactsByPersonsId_shouldReturnContactList() throws Exception {
        mockMvc.perform(get("/api/v1/contacts/personsId?personsId=1").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonDataByFilePath(GET_CONTACTS_BY_PERSON_ID_DATA_PATH)));
    }

    @Test
    void createContacts_shouldReturnListOfContactsId() throws Exception {
        mockMvc.perform(
                        post("/api/v1/contacts")
                                .contentType(APPLICATION_JSON)
                                .content(getJsonDataByFilePath(SAVE_CONTACTS_DATA_PATH)))
                .andExpect(status().isOk())
                .andExpect(content().json("[3,4,5]"));
        mockMvc.perform(
                        get("/api/v1/contacts/3")
                                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.type", is("HOME")))
                .andExpect(jsonPath("$.phoneNumber", is("+79999999982")));
    }

    @Test
    void updateContacts_shouldReturnListOfContactsId() throws Exception {
        mockMvc.perform(
                        put("/api/v1/contacts")
                                .contentType(APPLICATION_JSON)
                                .content(getJsonDataByFilePath(UPDATE_CONTACTS_DATA_PATH)))
                .andExpect(status().isOk())
                .andExpect(content().json("[1,2]"));
        mockMvc.perform(
                        get("/api/v1/contacts/1")
                                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.type", is("MOBILE")));
    }

    @Test
    void setVisibilityByContacts_shouldReturn200Status() throws Exception {
        mockMvc.perform(
                        put("/api/v1/contacts/visibility?contactsId=1&visibility=false")
                                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(
                        get("/api/v1/contacts/1").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.visibility", is(false)));
    }

    @Test
    void deleteContactsById() throws Exception {
        mockMvc.perform(
                        delete("/api/v1/contacts/contactsId?contactsId=1")
                                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(
                        get("/api/v1/contacts/1").contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof AbstractLocalizedException))
                .andExpect(content().string(containsString("Такой контакт не найден")));
    }

    @Test
    void deleteAllContacts() throws Exception {
        mockMvc.perform(
                delete("/api/v1/contacts").contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(
                get("/api/v1/contacts").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[]")));
    }
}
