package ru.vtb.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import ru.vtb.IntegrationAbstractTest;
import ru.vtb.model.Person;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.*;

@Sql(scripts = "sql/init-person-data-to-db.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "sql/drop-person-data-in-db.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
class
PersonRepositoryTest extends IntegrationAbstractTest {
    @Autowired
    protected PersonRepository personRepository;

    @Test
    void findByIdAndVisibility_falseVisibility() {
        Person person = personRepository.findByIdAndVisibility(1L, true).orElse(null);
        assertNull(person);
    }

    @Test
    void findByIdAndVisibility() {
        Person person = personRepository.findByIdAndVisibility(1L, false).orElse(null);
        assertNotNull(person);
    }

    @Test
    void findByPassportNumber_ignoreVisibility() {
        Person person = personRepository.findByPassportNumber("4545001001", null).orElse(null);
        assertNotNull(person);
    }

    @Test
    void findByPassportNumber_falseVisibility() {
        Person person = personRepository.findByPassportNumber("4545001001", true).orElse(null);
        assertNull(person);
    }

    @Test
    void findAllPersonsWithPaginationByVisibility() {
        List<Person> persons = personRepository.findAllPersonsWithPaginationByVisibility(null, null, Pageable.ofSize(10));
        assertNotNull(persons);
        assertFalse(persons.isEmpty());
    }
}