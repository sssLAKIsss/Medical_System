package ru.vtb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.vtb.controller.api.ContactApi;
import ru.vtb.dto.createInput.ContactCreateInputDto;
import ru.vtb.dto.getOrUpdate.ContactDto;
import ru.vtb.service.IContactService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContactController implements ContactApi {
    private final IContactService contactService;

    @Override
    public ResponseEntity<ContactDto> findContactById(Long id, Boolean visibility) {
        return ResponseEntity.ok(contactService.findById(id, visibility));
    }

    @Override
    public ResponseEntity<List<ContactDto>> findAllContacts(Boolean visibility) {
        return ResponseEntity.ok(contactService.findAllContacts(visibility));
    }

    @Override
    public ResponseEntity<List<ContactDto>> findContactsByPersonsId(List<Long> personsId, Boolean visibility) {
        return ResponseEntity.ok(contactService.findAllContactsByPersonsId(personsId, visibility));
    }

    @Override
    public ResponseEntity<List<ContactDto>> createContacts(List<ContactCreateInputDto> contact) {
        return ResponseEntity.ok(contactService.createListOfContacts(contact));
    }

    @Override
    public ResponseEntity<List<ContactDto>> updateContacts(List<ContactDto> contacts) {
        return ResponseEntity.ok(contactService.updateListOfContacts(contacts));
    }

    @Override
    public ResponseEntity<HttpStatus> setVisibilityByContacts(List<Long> contactsId, Boolean visibility) {
        contactService.setContactsVisibility(contactsId, visibility);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteContactsById(List<Long> contactsId) {
        contactService.deleteContactsFromDB(contactsId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteAllContacts() {
        contactService.deleteAllContacts();
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
