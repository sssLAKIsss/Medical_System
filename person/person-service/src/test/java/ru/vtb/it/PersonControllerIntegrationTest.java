package ru.vtb.it;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ru.vtb.IntegrationAbstractTest;
import ru.vtb.exception.AbstractLocalizedException;
import ru.vtb.repository.PersonRepository;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
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

@Sql(scripts = "sql/person/prepare-person-data.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "sql/person/drop-person-data.sql", executionPhase = AFTER_TEST_METHOD)
public class PersonControllerIntegrationTest extends IntegrationAbstractTest {

    private static final String GET_ALL_PERSONS_DATA_PATH = "src/test/resources/ru/vtb/json/person/persons-data.json";
    private static final String GET_PERSON_BY_ID_PATH = "src/test/resources/ru/vtb/json/person/person-data.json";
    private static final String SAVE_PERSONS_DATA_PATH = "src/test/resources/ru/vtb/json/person/persons-data-save.json";
    private static final String UPDATE_PERSONS_DATA_PATH = "src/test/resources/ru/vtb/json/person/person-data-update.json";

    @Autowired
    protected PersonRepository personRepository;

    @Test
    void findPersonById_shouldReturnNotFoundException() throws Exception {
        mockMvc.perform(get("/api/v1/persons/5").contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("Такой пользователь не найден")))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AbstractLocalizedException));
    }

    @Test
    void findPersonById_shouldReturnPerson() throws Exception {
        mockMvc.perform(get("/api/v1/persons/1").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonDataByFilePath(GET_PERSON_BY_ID_PATH)));
    }

    @Test
    void findPersonByPassportNumber_shouldReturnPerson() throws Exception {
        mockMvc.perform(get("/api/v1/persons/passportNumber/4545001001").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonDataByFilePath(GET_PERSON_BY_ID_PATH)));
    }

    @Test
    void findPersonByPassportNumber_shouldReturnNotFoundException() throws Exception {
        mockMvc.perform(get("/api/v1/persons/passportNumber/4545001010").contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("Такой пользователь не найден")))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AbstractLocalizedException));
    }

    @Test
    void findPersonByPassportNumber_shouldReturnBadRequestError() throws Exception {
        mockMvc.perform(get("/api/v1/persons/passportNumber/1").contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("Номер документа строго 10 цифр")))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException));
    }

    @Test
    void findAllPersons_shouldReturnListOfPersons() throws Exception {
        mockMvc.perform(get("/api/v1/persons").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonDataByFilePath(GET_ALL_PERSONS_DATA_PATH)));
    }

    @Test
    void findAllPersons_shouldReturnEmptyList() throws Exception {
        personRepository.deleteAll();
        mockMvc.perform(get("/api/v1/persons").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void updateAllPersons_shouldReturnListOfPersonsId() throws Exception {
        mockMvc.perform(put("/api/v1/persons/updateAll")
                        .contentType(APPLICATION_JSON)
                        .content(getJsonDataByFilePath(UPDATE_PERSONS_DATA_PATH)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[1]")));
        mockMvc.perform(get("/api/v1/persons/1").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.lastName", is("Андрей")))
                .andExpect(jsonPath("$.documents[*].number", hasItems("4545001005", "4545001004")))
                .andExpect(jsonPath("$.addresses[*].home", hasItem(4)))
                .andExpect(jsonPath("$.addresses[*].flat", hasItem(4)))
                .andExpect(jsonPath("$.contacts[*].phoneNumber", hasItem("+79999999999")));
    }

    @Test
    void saveAllPersons_shouldReturnListOfPersonsId() throws Exception {
        mockMvc.perform(post("/api/v1/persons/saveAll")
                        .contentType(APPLICATION_JSON)
                        .content(getJsonDataByFilePath(SAVE_PERSONS_DATA_PATH)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[4,5,6]")));
    }

    @Test
    void checkByValidPersonData_shouldReturnFalse() throws Exception {
        mockMvc.perform(get("/api/v1/persons/checkByValidPersonData?firstName=Васильев&lastName=Вася&patronymic=Васильевич&passportNumber=4545001001")

                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("false")));
    }

    @Test
    void checkByValidPersonData_shouldReturnTrue() throws Exception {

        mockMvc.perform(get("/api/v1/persons/checkByValidPersonData?firstName=Федор&lastName=Федоров&patronymic=Федорович&passportNumber=4545001001")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("true")));
    }

    @Test
    void checkByValidPersonData_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/persons/checkByValidPersonData?firstName=Федор&lastName=Федоров&patronymic=Федорович&passportNumber=1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ConstraintViolationException));
    }

    @Test
    void setVisibilityToPersons_shouldReturn200Status() throws Exception {
        mockMvc.perform(put("/api/v1/persons/visibility?visibility=false&personsId=1")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/persons/1").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.visibility", is(false)));
    }

    @Test
    void deleteAllById_shouldReturn200Status() throws Exception {
        mockMvc.perform(delete("/api/v1/persons/personsId?personsId=1").contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/persons/1").contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AbstractLocalizedException))
                .andExpect(content().string(containsString("Такой пользователь не найден")));
    }

    @Test
    void testDeleteAllById_shouldReturn200Status() throws Exception {
        mockMvc.perform(delete("/api/v1/persons").contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/persons").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
