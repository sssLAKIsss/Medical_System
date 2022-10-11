package ru.vtb.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.dto.createInput.PersonCreateInputDto;
import ru.vtb.dto.getOrUpdate.PersonDto;
import ru.vtb.exception.PersonNotFoundException;
import ru.vtb.mapper.IModelMapper;
import ru.vtb.model.Person;
import ru.vtb.repository.AddressRepository;
import ru.vtb.repository.ContactRepository;
import ru.vtb.repository.DocumentRepository;
import ru.vtb.repository.PersonRepository;
import ru.vtb.service.IPersonService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements IPersonService {
    private final PersonRepository personRepository;
    private final DocumentRepository documentRepository;
    private final ContactRepository contactRepository;
    private final AddressRepository addressRepository;
    private final IModelMapper<Person, PersonCreateInputDto, PersonDto> personMapper;

    @Override
    @Transactional(readOnly = true)
    public PersonDto findById(Long id, Boolean visibility) {
        return personMapper.convertToOutputDto(
                personRepository.findByIdAndVisibility(id, visibility)
                        .orElseThrow(PersonNotFoundException::new)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PersonDto findByPassportNumber(String passportNumber, Boolean visibility) {
        return personMapper.convertToOutputDto(
                personRepository.findByPassportNumber(passportNumber, visibility)
                        .orElseThrow(PersonNotFoundException::new)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonDto> findAllPersons(Boolean visibility, String filterRegion, Pageable pageable) {
        return personRepository.findAllPersonsWithPaginationByVisibility(visibility, filterRegion, pageable)
                .stream()
                .map(personMapper::convertToOutputDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long save(PersonCreateInputDto personCreateInputDto) {
        if (isNull(personCreateInputDto)) throw new PersonNotFoundException();
        return personRepository
                .save(personMapper.convertFromCreateDto(personCreateInputDto))
                .getId();
    }

    @Override
    @Transactional
    public List<Long> saveAll(List<PersonCreateInputDto> personCreateInputDtos) {
        if (personCreateInputDtos.isEmpty()) throw new PersonNotFoundException();
        return personRepository
                .saveAll(personCreateInputDtos
                        .stream()
                        .map(personMapper::convertFromCreateDto)
                        .collect(Collectors.toList()))
                .stream()
                .map(Person::getId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long update(PersonDto personDto) {
        if (isNull(personDto)) throw new PersonNotFoundException();
        return personRepository
                .save(personMapper.convertFromUpdateDto(personDto))
                .getId();
    }

    @Override
    @Transactional
    public List<Long> updateAll(List<PersonDto> personDtos) {
        if (personDtos.isEmpty()) throw new PersonNotFoundException();
        return personRepository
                .saveAll(personDtos
                        .stream()
                        .map(personMapper::convertFromUpdateDto)
                        .collect(Collectors.toList()))
                .stream()
                .map(Person::getId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isValidPassportForPerson(String personFullName, String passportNumber, Boolean visibility) {
        if (StringUtils.isBlank(personFullName) || StringUtils.isBlank(passportNumber))
            throw new RuntimeException();

        Optional<Person> optionalPerson = personRepository.findByPassportNumber(passportNumber, visibility);
        if (optionalPerson.isEmpty()) return false;
        Person person = optionalPerson.get();

        return requireNonNull(person.getLastName()).concat(" ")
                .concat(requireNonNull(person.getFirstName()))
                .concat(
                        nonNull(person.getPatronymic()) ?
                                " ".concat(person.getPatronymic()) :
                                "")
                .equals(personFullName);
    }

    @Override
    @Transactional
    public void setPersonsVisibility(Boolean visibility, List<Long> personsId) {
        personRepository.setVisibilityToDocuments(visibility, personsId);
        documentRepository.setVisibilityToDocumentsByPersonsId(visibility, personsId);
        contactRepository.setVisibilityToContactsByPersonsId(visibility, personsId);
        addressRepository.setVisibilityToAddressesByPersonsId(visibility, personsId);
    }

    @Override
    @Transactional
    public void deletePersonsById(List<Long> personsId) {
        personRepository.deleteAllById(personsId);
    }

    @Override
    @Transactional
    public void deleteAllPersons() {
        personRepository.deleteAll();
    }
}
