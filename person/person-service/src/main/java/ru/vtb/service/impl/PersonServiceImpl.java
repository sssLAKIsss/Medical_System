package ru.vtb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.dto.createInput.PersonCreateInputDto;
import ru.vtb.dto.getOrUpdate.PersonDto;
import ru.vtb.exception.PersonNotFoundException;
import ru.vtb.mapper.IModelMapper;
import ru.vtb.model.Person;
import ru.vtb.repository.PersonRepository;
import ru.vtb.service.IPersonService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;
import static ru.vtb.util.PersonServiceUtil.setPersonsAbilitiesVisibility;
import static ru.vtb.util.PersonServiceUtil.setVisibility;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements IPersonService {
    private final PersonRepository personRepository;
    private final IModelMapper<Person, PersonCreateInputDto, PersonDto> personMapper;

    @Override
    @Transactional(readOnly = true)
    public PersonDto findById(Long id, Boolean visibility) {
        Person person = personRepository.findByIdAndVisibility(id, visibility)
                .orElseThrow(PersonNotFoundException::new);
        setPersonsAbilitiesVisibility(person, visibility);
        return personMapper.convertToOutputDto(person);
    }

    @Override
    @Transactional(readOnly = true)
    public PersonDto findByPassportNumber(String passportNumber, Boolean visibility) {
        Person person = personRepository.findByPassportNumber(passportNumber, visibility)
                .orElseThrow(PersonNotFoundException::new);
        setPersonsAbilitiesVisibility(person, visibility);
        return personMapper.convertToOutputDto(person);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonDto> findAllPersons(Boolean visibility, String filterRegion, Pageable pageable) {
        List<Person> persons = personRepository.findAllPersonsWithPaginationByVisibility(visibility, filterRegion, pageable);
        persons.forEach(person -> setPersonsAbilitiesVisibility(person, visibility));
        return persons.stream()
                .map(personMapper::convertToOutputDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long save(PersonCreateInputDto personCreateInputDto) {
        return personRepository
                .save(personMapper.convertFromCreateDto(personCreateInputDto))
                .getId();
    }

    @Override
    @Transactional
    public List<Long> saveAll(List<PersonCreateInputDto> personCreateInputDtos) {
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
        return personRepository
                .save(personMapper.convertFromUpdateDto(personDto))
                .getId();
    }

    @Override
    @Transactional
    public List<Long> updateAll(List<PersonDto> personDtos) {
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
        Optional<Person> optionalPerson = personRepository.findByPassportNumber(passportNumber, visibility);
        if (optionalPerson.isEmpty()) return false;
        Person person = optionalPerson.get();

        return requireNonNull(person.getLastName()).concat(" ")
                .concat(requireNonNull(person.getFirstName()))
                .concat(nonNull(person.getPatronymic())
                        ? " ".concat(person.getPatronymic())
                        : "")
                .equals(personFullName);
    }

    @Override
    @Transactional
    public void setPersonsVisibility(Boolean visibility, Set<Long> personsId) {
        List<Person> persons = personRepository.findAllById(personsId);
        setVisibility(new HashSet<>(persons), visibility);
        persons.forEach(p -> p.setVisibility(visibility));
        persons.forEach(p -> setPersonsAbilitiesVisibility(p, visibility));
        personRepository.saveAll(persons);
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
