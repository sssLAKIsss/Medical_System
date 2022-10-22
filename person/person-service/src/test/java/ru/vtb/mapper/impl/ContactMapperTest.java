package ru.vtb.mapper.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import ru.vtb.AbstractTest;
import ru.vtb.dto.createInput.ContactCreateInputDto;
import ru.vtb.dto.getOrUpdate.ContactDto;
import ru.vtb.model.Contact;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContactMapperTest extends AbstractTest {

    private static final String CONTACT_DATA = "src/test/resources/ru/vtb/test-contact-data.json";

    @Autowired
    protected ContactMapper contactMapper;

    @Test
    void convertFromCreateDto() throws IOException {
        ContactCreateInputDto contactCreateInputDto = objectMapper.readValue(
                ResourceUtils.getFile(CONTACT_DATA),
                ContactCreateInputDto.class
        );
        Contact contact = contactMapper.convertFromCreateDto(contactCreateInputDto);

        assertEquals(contactCreateInputDto.getPhoneNumber(), contact.getPhoneNumber());
        assertEquals(contactCreateInputDto.getType(), contact.getType());
        assertTrue(contact.isVisibility());
    }

    @Test
    void convertFromUpdateDto() throws IOException {
        ContactDto contactDto = objectMapper.readValue(
                ResourceUtils.getFile(CONTACT_DATA),
                ContactDto.class
        );
        Contact contact = contactMapper.convertFromUpdateDto(contactDto);

        assertEquals(contactDto.getPhoneNumber(), contact.getPhoneNumber());
        assertEquals(contactDto.getType(), contact.getType());
        assertEquals(contactDto.getId(), contact.getId());
        assertEquals(contactDto.getVersion(), contact.getVersion());
        assertTrue(contact.isVisibility());
    }

    @Test
    void convertToOutputDto() throws IOException {
        Contact contact = objectMapper.readValue(
                ResourceUtils.getFile(CONTACT_DATA),
                Contact.class
        );
        ContactDto contactDto = contactMapper.convertToOutputDto(contact);

        assertEquals(contact.getPhoneNumber(), contactDto.getPhoneNumber());
        assertEquals(contact.getType(), contactDto.getType());
        assertEquals(contact.getId(), contactDto.getId());
        assertEquals(contact.getVersion(), contactDto.getVersion());
        assertTrue(contactDto.isVisibility());
    }
}