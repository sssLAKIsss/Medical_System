package ru.vtb.it;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ru.vtb.IntegrationAbstractTest;
import ru.vtb.exception.AbstractLocalizedException;
import ru.vtb.repository.AddressRepository;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
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

@Sql(scripts = "sql/address/prepare-address-data.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "sql/address/drop-address-data.sql", executionPhase = AFTER_TEST_METHOD)
class AddressControllerIntegrationTest extends IntegrationAbstractTest {

    private static final String GET_ALL_ADDRESSES_DATA_PATH = "src/test/resources/ru/vtb/json/address/addresses-data.json";
    private static final String GET_BY_ID_ADDRESS_DATA_PATH = "src/test/resources/ru/vtb/json/address/address-data.json";
    private static final String GET_BY_PERSON_ID_ADDRESSES_DATA_PATH = "src/test/resources/ru/vtb/json/address/addresses-data-with-person-id.json";
    private static final String SAVE_ADDRESSES_DATA_PATH = "src/test/resources/ru/vtb/json/address/address-data-save.json";
    private static final String UPDATE_ADDRESSES_DATA_PATH = "src/test/resources/ru/vtb/json/address/address-data-update.json";

    @Autowired
    protected AddressRepository addressRepository;

    @Test
    void findAllAddresses_shouldReturnListOfAddresses() throws Exception {
        mockMvc.perform(get("/api/v1/addresses").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonDataByFilePath(GET_ALL_ADDRESSES_DATA_PATH)));
    }

    @Test
    void findAddressById_shouldReturnNotFoundException() throws Exception {
        mockMvc.perform(
                        get("/api/v1/addresses/10").contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("Такой адрес не найден")));
    }

    @Test
    void findAddressById_shouldReturnAddress() throws Exception {
        mockMvc.perform(
                get("/api/v1/addresses/1").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonDataByFilePath(GET_BY_ID_ADDRESS_DATA_PATH)));
    }

    @Test
    void findAllAddressesByPersonsId_shouldReturnListOfAddresses() throws Exception {
        mockMvc.perform(
                get("/api/v1/addresses/byPersonsId?personsId=1").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(getJsonDataByFilePath(GET_BY_PERSON_ID_ADDRESSES_DATA_PATH)));
    }

    @Test
    void saveAddresses_shouldReturnEmptyList() throws Exception {
        mockMvc.perform(
                post("/api/v1/addresses")
                        .contentType(APPLICATION_JSON)
                        .content(getJsonDataByFilePath(GET_ALL_ADDRESSES_DATA_PATH)))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void saveAddresses_shouldReturnListOfAddressesId() throws Exception {
        mockMvc.perform(
                post("/api/v1/addresses")
                        .contentType(APPLICATION_JSON)
                        .content(getJsonDataByFilePath(SAVE_ADDRESSES_DATA_PATH)))
                .andExpect(status().isOk())
                .andExpect(content().json("[3]"));

        mockMvc.perform(
                        get("/api/v1/addresses/3").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.type", is("REGISTRATION")))
                .andExpect(jsonPath("$.country", is("Russia")))
                .andExpect(jsonPath("$.region", is("RB")))
                .andExpect(jsonPath("$.city", is("UFA")))
                .andExpect(jsonPath("$.street", is("GOGOl9")))
                .andExpect(jsonPath("$.home", is(123)))
                .andExpect(jsonPath("$.flat", is(123)));
    }

    @Test
    void saveAddresses_shouldReturnBadRequestError() throws Exception {
        mockMvc.perform(
                post("/api/v1/addresses")
                        .contentType(APPLICATION_JSON)
                        .content(""))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void updateAddresses_shouldReturnIdsOfUpdatedAddresses() throws Exception {
        mockMvc.perform(
                put("/api/v1/addresses")
                        .contentType(APPLICATION_JSON)
                        .content(getJsonDataByFilePath(UPDATE_ADDRESSES_DATA_PATH)))
                .andExpect(status().isOk())
                .andExpect(content().json("[1,2]"));

        mockMvc.perform(
                        get("/api/v1/addresses/1").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.type", is("REGISTRATION")))
                .andExpect(jsonPath("$.country", is("Russia")))
                .andExpect(jsonPath("$.region", is("RB")))
                .andExpect(jsonPath("$.city", is("UFA")))
                .andExpect(jsonPath("$.street", is("PANTELKINA")))
                .andExpect(jsonPath("$.home", is(106)))
                .andExpect(jsonPath("$.flat", nullValue()));
    }

    @Test
    void updateAddresses_shouldReturnBadRequestError() throws Exception {
        mockMvc.perform(
                put("/api/v1/addresses")
                        .contentType(APPLICATION_JSON)
                        .content(""))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void setVisibilityToAddresses_shouldReturn200Status() throws Exception {
        mockMvc.perform(
                put("/api/v1/addresses/visibility?addresses=1&visibility=true")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(
                        get("/api/v1/addresses/1").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.visibility", is(true)));
    }

    @Test
    void deleteAddressesById_shouldReturn200Status() throws Exception {
        mockMvc.perform(
                delete("/api/v1/addresses/addressesId?addressesId=1").contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(
                        get("/api/v1/addresses/1").contentType(APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof AbstractLocalizedException))
                .andExpect(content().string(containsString("Такой адрес не найден")));
    }

    @Test
    void deleteAllAddresses_shouldReturn200Status() throws Exception {
        mockMvc.perform(
                delete("/api/v1/addresses").contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(
                        get("/api/v1/addresses").contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[]")));
    }
}