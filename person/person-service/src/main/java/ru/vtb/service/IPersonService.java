package ru.vtb.service;

import org.springframework.data.domain.Pageable;
import ru.vtb.dto.createInput.PersonCreateInputDto;
import ru.vtb.dto.getOrUpdate.PersonDto;

import java.util.List;

public interface IPersonService {
    PersonDto findById(Long id, Boolean visibility);
    PersonDto findByPassportNumber(String passportNumber, Boolean visibility);
    List<PersonDto> findAllPersons(Boolean visibility, String filterParam, Pageable pageable);

    Long save(PersonCreateInputDto personCreateInputDto);
    Long update(PersonDto personInputDto);
    List<Long> saveAll(List<PersonCreateInputDto> personCreateInputDtos);
    List<Long> updateAll(List<PersonDto> personDtos);

    boolean isValidPassportForPerson(String personFullName, String passportNumber, Boolean visibility);
    void setPersonsVisibility(Boolean visibility, List<Long> personsId);

    //TODO будет время - надо сделать
//    void setDocumentsToPersons(List<Long> personsId, List<Long> documentsId);
//    void setContactsToPersons(List<Long> personsId, List<Long> contactsId);
//    void setAddressesToPersons(List<Long> personsId, List<Long> addressesId);

    void deletePersonsById(List<Long> personsId);
    void deleteAllPersons();
}
