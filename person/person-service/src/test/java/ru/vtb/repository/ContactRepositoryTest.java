package ru.vtb.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ru.vtb.IntegrationAbstractTest;
import ru.vtb.model.Contact;
import ru.vtb.model.superclass.BaseDateVersionEntity;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@Sql(scripts = "sql/init-contact-data-to-db.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "sql/drop-contact-data-in-db.sql", executionPhase = AFTER_TEST_METHOD)
class ContactRepositoryTest extends IntegrationAbstractTest {
    @Autowired
    protected ContactRepository contactRepository;

    @Test
    void findContactById_trueVisibility() {
        Contact contact = contactRepository.findContactById(1L, true).orElse(null);
        assertNotNull(contact);
    }

    @Test
    void findContactById_falseVisibility() {
        Contact contact = contactRepository.findContactById(2L, true).orElse(null);
        assertThat(contact).isNull();
    }

    @Test
    void findAllContactsByVisibility() {
        List<Contact> contacts = contactRepository.findAllContactsByVisibility(true);
        assertNotNull(contacts);
        assertFalse(contacts.isEmpty());
        assertTrue(contacts.stream().allMatch(BaseDateVersionEntity::isVisibility));
    }

    @Test
    void findAllContactsByPersonIdIsInAndVisibilityIsLike_withPersonId() {
        List<Contact> contacts = contactRepository.findAllContactsByPersonIdIsInAndVisibilityIsLike(List.of(2L), true);
        assertNotNull(contacts);
        assertTrue(contacts.isEmpty());
    }

    @Test
    void findAllContactsByPersonIdIsInAndVisibilityIsLike_withoutPersonId() {
        List<Contact> contacts = contactRepository.findAllContactsByPersonIdIsInAndVisibilityIsLike(List.of(1L), true);
        assertTrue(contacts.isEmpty());
    }

    @Test
    void setVisibilityToContacts() {
        contactRepository.setVisibilityToContacts(false, Collections.singletonList(1L));
        assertThat(contactRepository.findContactById(1L, false)).isNotNull();
        assertFalse(contactRepository.findContactById(1L, false).orElseThrow().isVisibility());
    }

    @Test
    void findAllContactsByPersonIdAndVisibility() {
        List<Contact> contacts = contactRepository.findAllContactsByPersonIdAndVisibility(1L, true);
        assertNotNull(contacts);
        assertFalse(contacts.isEmpty());
        assertThat(contacts).hasSize(1);
    }
}