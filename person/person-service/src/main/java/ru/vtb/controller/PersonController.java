package ru.vtb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.vtb.controller.api.PersonApi;
import ru.vtb.dto.createInput.PersonCreateInputDto;
import ru.vtb.dto.getOrUpdate.PersonDto;
import ru.vtb.service.IPersonService;

import java.util.List;
import java.util.Set;


@RestController
@RequiredArgsConstructor
public class PersonController implements PersonApi {
    private final IPersonService personService;

    @Override
    public ResponseEntity<PersonDto> findPersonById(Long id, Boolean visibility) {
        return ResponseEntity.ok(personService.findById(id, visibility));
    }

    @Override
    public ResponseEntity<PersonDto> findPersonByPassportNumber(String passportNumber, Boolean visibility) {
        return ResponseEntity.ok(personService.findByPassportNumber(passportNumber, visibility));
    }

    @Override
    public ResponseEntity<List<PersonDto>> findAllPersons(
            Integer pageNumber,
            Integer pageSize,
            String sortField,
            Boolean visibility,
            String filterParameter) {
        return ResponseEntity.ok(personService.findAllPersons(
                visibility,
                filterParameter,
                PageRequest.of(pageNumber, pageSize, Sort.by(sortField))));
    }

    @Override
    public ResponseEntity<Long> updatePerson(PersonDto personDto) {
        return ResponseEntity.ok(personService.update(personDto));
    }

    @Override
    public ResponseEntity<List<Long>> updateAllPersons(List<PersonDto> personDtos) {
        return ResponseEntity.ok(personService.updateAll(personDtos));
    }

    @Override
    public ResponseEntity<List<Long>> saveAllPersons(List<PersonCreateInputDto> personCreateInputDtos) {
        return ResponseEntity.ok(personService.saveAll(personCreateInputDtos));
    }


    @Override
    public ResponseEntity<Long> savePerson(PersonCreateInputDto personCreateInputDto) {
        return ResponseEntity.ok(personService.save(personCreateInputDto));
    }

    @Override
    public ResponseEntity<Boolean> checkByValidPersonData(String firstName,
                                                          String lastName,
                                                          String patronymic,
                                                          String passportNumber,
                                                          Boolean visibility) {
        return ResponseEntity.ok(personService.isValidPassportForPerson(firstName, lastName, patronymic, passportNumber, visibility));
    }

    @Override
    public ResponseEntity<HttpStatus> setVisibilityToPersons(Boolean visibility, Set<Long> personsId) {
        personService.setPersonsVisibility(visibility, personsId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteAllById(List<Long> personsId) {
        personService.deletePersonsById(personsId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteAllById() {
        personService.deleteAllPersons();
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
