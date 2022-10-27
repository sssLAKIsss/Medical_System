package ru.vtb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.dto.createInput.ContactCreateInputDto;
import ru.vtb.dto.getOrUpdate.ContactDto;
import ru.vtb.exception.ContactNotFoundException;
import ru.vtb.mapper.IModelMapper;
import ru.vtb.model.Contact;
import ru.vtb.repository.ContactRepository;
import ru.vtb.service.IContactService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements IContactService {
    private final IModelMapper<Contact, ContactCreateInputDto, ContactDto> contactMapper;
    private final ContactRepository contactRepository;

    @Override
    @Transactional(readOnly = true)
    public ContactDto findById(Long id, Boolean visibility) {
        return contactMapper.convertToOutputDto(
                contactRepository.findContactById(id, visibility)
                        .orElseThrow(ContactNotFoundException::new)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactDto> findAllContacts(Boolean visibility) {
        return contactRepository.findAllContactsByVisibility(visibility)
                .stream()
                .map(contactMapper::convertToOutputDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, List<ContactDto>> findAllContactsByPersonsId(List<Long> personsId, Boolean visibility) {
        return personsId.stream()
                .map(personId ->
                        Map.entry(
                                personId,
                                contactRepository.findAllContactsByPersonIdAndVisibility(personId, visibility)
                                        .stream()
                                        .map(contactMapper::convertToOutputDto)
                                        .collect(Collectors.toList())
                        ))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    @Transactional
    public List<Long> createListOfContacts(List<ContactCreateInputDto> contacts) {
        return contactRepository.saveAll(
                contacts.stream()
                        .map(contactMapper::convertFromCreateDto)
                        .filter(contact -> !contactRepository.existsContactByPhoneNumber(contact.getPhoneNumber()))
                        .collect(Collectors.toList()))
                .stream()
                .map(Contact::getId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<Long> updateListOfContacts(List<ContactDto> contacts) {
        return contactRepository.saveAll(
                        contacts.stream()
                                .map(contactMapper::convertFromUpdateDto)
                                .collect(Collectors.toList()))
                .stream()
                .map(Contact::getId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void setContactsVisibility(List<Long> contactsId, boolean visibility) {
        contactRepository.setVisibilityToContacts(visibility, contactsId);
    }

    @Override
    @Transactional
    public void deleteContactsFromDB(List<Long> contactsId) {
        contactRepository.deleteAllById(contactsId);
    }

    @Override
    @Transactional
    public void deleteAllContacts() {
        contactRepository.deleteAll();
    }
}
