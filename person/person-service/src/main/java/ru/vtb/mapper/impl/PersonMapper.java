package ru.vtb.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.vtb.dto.createInput.PersonCreateInputDto;
import ru.vtb.dto.getOrUpdate.PersonDto;
import ru.vtb.mapper.IModelMapper;
import ru.vtb.model.Person;

@Component
@RequiredArgsConstructor
public class PersonMapper implements IModelMapper<Person, PersonCreateInputDto, PersonDto> {
    private final ModelMapper mapper;

    @Override
    public Person convertFromCreateDto(PersonCreateInputDto createDto) {
        Person p = mapper.map(createDto, Person.class);
        p.setVisibility(true);
        p.getAddresses().forEach(a -> a.setVisibility(true));
        p.getDocuments().forEach(d -> d.setVisibility(true));
        p.getContacts().forEach(c -> c.setVisibility(true));
        return p;
    }

    @Override
    public Person convertFromUpdateDto(PersonDto updateDto) {
        Person p = mapper.map(updateDto, Person.class);
        p.getDocuments().forEach(d -> d.setPersonId(p.getId()));
        p.getContacts().forEach(c -> c.setPersonId(p.getId()));
        return p;
    }

    @Override
    public PersonDto convertToOutputDto(Person entity) {
        return mapper.map(entity, PersonDto.class);
    }
}
