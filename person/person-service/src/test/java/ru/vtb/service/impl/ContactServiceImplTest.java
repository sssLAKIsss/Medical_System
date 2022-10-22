package ru.vtb.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.vtb.dto.createInput.ContactCreateInputDto;
import ru.vtb.dto.getOrUpdate.ContactDto;
import ru.vtb.model.Contact;
import ru.vtb.repository.ContactRepository;
import ru.vtb.AbstractTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ContactServiceImplTest extends AbstractTest {

    @MockBean
    private ContactRepository contactRepository;

    @Autowired
    private ContactServiceImpl contactService;

    @Test
    void findById() {
        //when
        when(contactRepository.findContactById(any(), any()))
                .thenReturn(Optional.ofNullable(Contact.builder().build()));
        //then
        ContactDto contactDtoList = contactService.findById(1L, true);

        assertNotNull(contactDtoList);
        verify(contactRepository, times(1))
                .findContactById(any(), any());
    }

    @Test
    void findAllContacts() {
        //when
        when(contactRepository.findAllContactsByVisibility(any()))
                .thenReturn(List.of(Contact.builder().build()));
        //then
        List<ContactDto> contactDtos = contactService.findAllContacts(true);

        assertNotNull(contactDtos);
        assertFalse(contactDtos.isEmpty());
        verify(contactRepository, times(1))
                .findAllContactsByVisibility(any());
    }

    @Test
    void findAllContactsByPersonsId() {
        //when
        when(contactRepository.findAllContactsByPersonIdIsInAndVisibilityIsLike(any(), any()))
                .thenReturn(List.of(Contact.builder().build()));
        //then
        List<ContactDto> contactDtos = contactService.findAllContactsByPersonsId(List.of(1L), true);

        assertNotNull(contactDtos);
        assertFalse(contactDtos.isEmpty());
        verify(contactRepository, times(1))
                .findAllContactsByPersonIdIsInAndVisibilityIsLike(any(), any());
    }

    @Test
    void createListOfContacts() {
        //when
        when(contactRepository.saveAll(any()))
                .thenReturn(List.of(Contact.builder().build()));
        //then
        List<ContactDto> contactDtos = contactService.createListOfContacts(List.of(ContactCreateInputDto.builder().build()));

        assertNotNull(contactDtos);
        assertFalse(contactDtos.isEmpty());
        verify(contactRepository, times(1))
                .saveAll(any());
    }

    @Test
    void updateListOfContacts() {
        //when
        when(contactRepository.saveAll(any()))
                .thenReturn(List.of(Contact.builder().build()));
        //then
        List<ContactDto> contactDtos = contactService.updateListOfContacts(List.of(ContactDto.builder().build()));

        assertNotNull(contactDtos);
        assertFalse(contactDtos.isEmpty());
        verify(contactRepository, times(1))
                .saveAll(any());
    }
}