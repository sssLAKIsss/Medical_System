package ru.vtb.mapper.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import ru.vtb.AbstractTest;
import ru.vtb.dto.createInput.PersonCreateInputDto;
import ru.vtb.dto.getOrUpdate.PersonDto;
import ru.vtb.model.Person;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PersonMapperTest extends AbstractTest {

    private static final String PERSON_DATA_PATH = "src/test/resources/ru/vtb/json/mapper/test-person-data.json";

    @Autowired
    protected PersonMapper personMapper;

    @Test
    void convertFromCreateDto() throws IOException {
        PersonCreateInputDto personCreateInputDto = objectMapper.readValue(
                ResourceUtils.getFile(PERSON_DATA_PATH),
                PersonCreateInputDto.class
        );
        Person person = personMapper.convertFromCreateDto(personCreateInputDto);

        assertEquals(personCreateInputDto.getFirstName(), person.getFirstName());
        assertEquals(personCreateInputDto.getLastName(), person.getLastName());
        assertEquals(personCreateInputDto.getPatronymic(), person.getPatronymic());
        assertTrue(person.isVisibility());
        assertThat(person.getAddresses()).hasSize(1);
        assertThat(person.getContacts()).hasSize(1);
        assertThat(person.getDocuments()).hasSize(1);
    }

    @Test
    void convertFromUpdateDto() throws IOException {
        PersonDto personDto = objectMapper.readValue(
                ResourceUtils.getFile(PERSON_DATA_PATH),
                PersonDto.class
        );
        Person person = personMapper.convertFromUpdateDto(personDto);

        assertEquals(personDto.getFirstName(), person.getFirstName());
        assertEquals(personDto.getLastName(), person.getLastName());
        assertEquals(personDto.getPatronymic(), person.getPatronymic());
        assertTrue(person.isVisibility());
        assertThat(person.getAddresses()).hasSize(1);
        assertThat(person.getContacts()).hasSize(1);
        assertThat(person.getDocuments()).hasSize(1);
    }

    @Test
    void convertToOutputDto() throws IOException {
        Person person = objectMapper.readValue(
                ResourceUtils.getFile(PERSON_DATA_PATH),
                Person.class
        );
        PersonDto personDto = personMapper.convertToOutputDto(person);

        assertEquals(person.getFirstName(), personDto.getFirstName());
        assertEquals(person.getLastName(), personDto.getLastName());
        assertEquals(person.getPatronymic(), personDto.getPatronymic());
        assertTrue(personDto.isVisibility());
        assertThat(personDto.getAddresses()).hasSize(1);
        assertThat(personDto.getContacts()).hasSize(1);
        assertThat(personDto.getDocuments()).hasSize(1);
    }
}