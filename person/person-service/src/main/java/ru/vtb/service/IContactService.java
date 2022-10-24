package ru.vtb.service;


import ru.vtb.dto.createInput.ContactCreateInputDto;
import ru.vtb.dto.getOrUpdate.ContactDto;

import java.util.List;
import java.util.Map;

public interface IContactService {
    ContactDto findById(Long id, Boolean visibility);
    List<ContactDto> findAllContacts(Boolean visibility);
    Map<Long, List<ContactDto>> findAllContactsByPersonsId(List<Long> personsId, Boolean visibility);

    List<ContactDto> createListOfContacts(List<ContactCreateInputDto> contacts);
    List<ContactDto> updateListOfContacts(List<ContactDto> contacts);

    void setContactsVisibility(List<Long> contactsId, boolean visibility);
    void deleteContactsFromDB(List<Long> contactsId);
    void deleteAllContacts();
}
