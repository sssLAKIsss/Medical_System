package ru.vtb.mapper.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import ru.vtb.dto.createInput.AddressCreateInputDto;
import ru.vtb.dto.getOrUpdate.AddressDto;
import ru.vtb.model.Address;
import ru.vtb.AbstractTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AddressMapperTest extends AbstractTest {
    @Autowired
    protected AddressMapper addressMapper;

    private static final String ADDRESS_DATA_PATH = "src/test/resources/ru/vtb/test-address-data.json";

    @Test
    void convertFromCreateDto() throws IOException {
        AddressCreateInputDto addressCreateInputDto = objectMapper.readValue(
                ResourceUtils.getFile(ADDRESS_DATA_PATH),
                AddressCreateInputDto.class);
        Address address = addressMapper.convertFromCreateDto(addressCreateInputDto);

        assertEquals(address.getType().name(), (addressCreateInputDto.getType()));
        assertEquals(address.getCity(), (addressCreateInputDto.getCity()));
        assertEquals(address.getCountry(), (addressCreateInputDto.getCountry()));
        assertEquals(address.getFlat(), (addressCreateInputDto.getFlat()));
        assertEquals(address.getHome(), (addressCreateInputDto.getHome()));
        assertEquals(address.getRegion(), (addressCreateInputDto.getRegion()));
        assertEquals(address.getStreet(), (addressCreateInputDto.getStreet()));
        assertTrue(address.isVisibility());
    }

    @Test
    void convertFromUpdateDto() throws IOException {
        AddressDto addressDto = objectMapper.readValue(
                ResourceUtils.getFile(ADDRESS_DATA_PATH),
                AddressDto.class
        );
        Address address = addressMapper.convertFromUpdateDto(addressDto);

        assertEquals(address.getId(), (addressDto.getId()));
        assertEquals(address.getType(), (addressDto.getType()));
        assertEquals(address.getCity(), (addressDto.getCity()));
        assertEquals(address.getCountry(), (addressDto.getCountry()));
        assertEquals(address.getFlat(), (addressDto.getFlat()));
        assertEquals(address.getHome(), (addressDto.getHome()));
        assertEquals(address.getRegion(), (addressDto.getRegion()));
        assertEquals(address.getStreet(), (addressDto.getStreet()));

    }

    @Test
    void convertToOutputDto() throws IOException {
        Address address = objectMapper.readValue(
                ResourceUtils.getFile(ADDRESS_DATA_PATH),
                Address.class
        );
        AddressDto addressDto = addressMapper.convertToOutputDto(address);

        assertEquals(address.getId(), (addressDto.getId()));
        assertEquals(address.getType(), (addressDto.getType()));
        assertEquals(address.getCity(), (addressDto.getCity()));
        assertEquals(address.getCountry(), (addressDto.getCountry()));
        assertEquals(address.getFlat(), (addressDto.getFlat()));
        assertEquals(address.getHome(), (addressDto.getHome()));
        assertEquals(address.getRegion(), (addressDto.getRegion()));
        assertEquals(address.getStreet(), (addressDto.getStreet()));
    }
}