package ru.vtb.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.vtb.dto.createInput.AddressCreateInputDto;
import ru.vtb.dto.getOrUpdate.AddressDto;
import ru.vtb.mapper.IModelMapper;
import ru.vtb.model.Address;

@Component
@RequiredArgsConstructor
public class AddressMapper implements IModelMapper<Address, AddressCreateInputDto, AddressDto> {
    private final ModelMapper mapper;

    @Override
    public Address convertFromCreateDto(AddressCreateInputDto createDto) {
        Address address = mapper.map(createDto, Address.class);
        address.setVisibility(true);
        return address;
    }

    @Override
    public Address convertFromUpdateDto(AddressDto updateDto) {
        return mapper.map(updateDto, Address.class);
    }

    @Override
    public AddressDto convertToOutputDto(Address entity) {
        return mapper.map(entity, AddressDto.class);
    }
}
