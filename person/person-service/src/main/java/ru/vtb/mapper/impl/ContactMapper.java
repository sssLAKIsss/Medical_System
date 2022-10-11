package ru.vtb.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.vtb.dto.createInput.ContactCreateInputDto;
import ru.vtb.dto.getOrUpdate.ContactDto;
import ru.vtb.mapper.IModelMapper;
import ru.vtb.model.Contact;


@Component
@RequiredArgsConstructor
public class ContactMapper implements IModelMapper<Contact, ContactCreateInputDto, ContactDto> {
    private final ModelMapper mapper;

    @Override
    public Contact convertFromCreateDto(ContactCreateInputDto createDto) {
        Contact c = mapper.map(createDto, Contact.class);
        c.setVisibility(true);
        return c;
    }

    @Override
    public Contact convertFromUpdateDto(ContactDto updateDto) {
        return mapper.map(updateDto, Contact.class);
    }

    @Override
    public ContactDto convertToOutputDto(Contact entity) {
        return mapper.map(entity, ContactDto.class);
    }
}
