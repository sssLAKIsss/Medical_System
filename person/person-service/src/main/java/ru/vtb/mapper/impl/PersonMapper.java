package ru.vtb.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.vtb.dto.createInput.PersonCreateInputDto;
import ru.vtb.dto.getOrUpdate.PersonDto;
import ru.vtb.mapper.IModelMapper;
import ru.vtb.model.Person;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class PersonMapper implements IModelMapper<Person, PersonCreateInputDto, PersonDto> {
    private final ModelMapper mapper;

    @Override
    public Person convertFromCreateDto(PersonCreateInputDto createDto) {
        Person p = mapper.map(createDto, Person.class);
        p.setVisibility(true);
        Optional.ofNullable(p.getAddresses()).orElse(Set.of()).forEach(a -> a.setVisibility(true));
        Optional.ofNullable(p.getDocuments()).orElse(Set.of()).forEach(d -> d.setVisibility(true));
        Optional.ofNullable(p.getContacts()).orElse(Set.of()).forEach(c -> c.setVisibility(true));
        return p;
    }

    @Override
    public Person convertFromUpdateDto(PersonDto updateDto) {
        Person p = mapper.map(updateDto, Person.class);
        Optional.ofNullable(p.getDocuments()).orElse(Set.of()).forEach(d -> d.setPersonId(p.getId()));
        Optional.ofNullable(p.getContacts()).orElse(Set.of()).forEach(c -> c.setPersonId(p.getId()));
        return p;
    }

    @Override
    public PersonDto convertToOutputDto(Person entity) {
        return mapper.map(entity, PersonDto.class);
    }
}
