package ru.vtb.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import ru.vtb.dto.createInput.PersonCreateInputDto;
import ru.vtb.dto.getOrUpdate.PersonDto;
import ru.vtb.model.Person;
import ru.vtb.repository.PersonRepository;
import ru.vtb.AbstractTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PersonServiceImplTest extends AbstractTest {

    @MockBean
    private PersonRepository personRepository;

    @Autowired
    private PersonServiceImpl personService;

    @Test
    void findById() {
        //when
        when(personRepository.findByIdAndVisibility(any(), any()))
                .thenReturn(Optional.ofNullable(Person.builder().build()));
        //then
        PersonDto personDto = personService.findById(1L, true);

        assertNotNull(personDto);
        verify(personRepository, times(1))
                .findByIdAndVisibility(any(), any());
    }

    @Test
    void findByPassportNumber() {
        //when
        when(personRepository.findByPassportNumber(any(), any()))
                .thenReturn(Optional.ofNullable(Person.builder().build()));
        //then
        PersonDto personDto = personService.findByPassportNumber("", true);

        assertNotNull(personDto);
        verify(personRepository, times(1))
                .findByPassportNumber(any(), any());
    }

    @Test
    void findAllPersons() {
        //when
        when(personRepository.findAllPersonsWithPaginationByVisibility(any(), any(), any()))
                .thenReturn(List.of(Person.builder().build()));
        //then
        List<PersonDto> personDtos = personService.findAllPersons(true, "", Pageable.ofSize(1));

        assertNotNull(personDtos);
        assertFalse(personDtos.isEmpty());
        verify(personRepository, times(1))
                .findAllPersonsWithPaginationByVisibility(any(), any(), any());
    }

    @Test
    void save() {
        //when
        when(personRepository.save(any()))
                .thenReturn(Person.builder().id(1L).build());
        //then
        Long personId = personService.save(PersonCreateInputDto.builder().build());

        assertEquals(1L, personId);
        verify(personRepository, times(1))
                .save(any());
    }

    @Test
    void saveAll() {
        //when
        when(personRepository.saveAll(any()))
                .thenReturn(List.of(Person.builder().id(1L).build()));
        //then
        List<Long> personsId = personService.saveAll(List.of(PersonCreateInputDto.builder().build()));

        assertNotNull(personsId);
        assertTrue(personsId.contains(1L));
        verify(personRepository, times(1))
                .saveAll(any());
    }

    @Test
    void update() {
        //when
        when(personRepository.save(any()))
                .thenReturn(Person.builder().id(1L).build());
        //then
        Long personId = personService.update(PersonDto.builder().build());

        assertEquals(1L, personId);
        verify(personRepository, times(1))
                .save(any());
    }

    @Test
    void updateAll() {
        //when
        when(personRepository.saveAll(any()))
                .thenReturn(List.of(Person.builder().id(1L).build()));
        //then
        List<Long> personsId = personService.saveAll(List.of(PersonCreateInputDto.builder().build()));

        assertNotNull(personsId);
        assertTrue(personsId.contains(1L));
        verify(personRepository, times(1))
                .saveAll(any());
    }

    @Test
    void isValidPassportForPerson_provideException() {
        //when
        when(personRepository.findByPassportNumber(any(), any()))
                .thenReturn(Optional.of(Person.builder().build()));
        assertThrows(
                RuntimeException.class,
                () -> personService.isValidPassportForPerson("", "123", true));
        assertThrows(
                RuntimeException.class,
                () -> personService.isValidPassportForPerson("Name Name", "", true));
    }

    @Test
    void isValidPassportForPerson_personNotFound() {
        //when
        when(personRepository.findByPassportNumber(any(), any()))
                .thenReturn(Optional.empty());
        //then
        boolean isValid = personService.isValidPassportForPerson("Name name", "123", true);

        assertFalse(isValid);
        verify(personRepository, times(1))
                .findByPassportNumber(any(), any());
    }

    @Test
    void isValidPassportForPerson_personIsPresent() {
        //when
        when(personRepository.findByPassportNumber(any(), any()))
                .thenReturn(Optional.ofNullable(Person.builder().firstName("firstName").lastName("lastname").build()));
        //then
        boolean isValid = personService.isValidPassportForPerson("lastname firstName", "123", true);

        assertTrue(isValid);
        verify(personRepository, times(1))
                .findByPassportNumber(any(), any());
    }

    @Test
    void setPersonsVisibility() {

    }
}