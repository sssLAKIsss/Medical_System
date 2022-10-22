package ru.vtb.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.vtb.dto.createInput.AddressCreateInputDto;
import ru.vtb.dto.getOrUpdate.AddressDto;
import ru.vtb.exception.AddressNotFoundException;
import ru.vtb.model.Address;
import ru.vtb.repository.AddressRepository;
import ru.vtb.AbstractTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddressServiceImplTest extends AbstractTest {
    @Autowired
    private AddressServiceImpl addressService;

    @MockBean
    private AddressRepository addressRepository;

    @Test
    void findById() {
        //when
        when(addressRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(Address.builder().build()));
        //then
        AddressDto addressDto = addressService.findById(1L);

        assertNotNull(addressDto);
        verify(addressRepository, times(1))
                .findById(anyLong());
    }
    @Test
    void findById_mustThrowException() {
        //when
        when(addressRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        //then
        assertThrows(AddressNotFoundException.class, () -> addressService.findById(1L));

        verify(addressRepository, times(1))
                .findById(anyLong());
    }

    @Test
    void findAllAddresses() {
        //when
        when(addressRepository.findAllAddressesByVisibility(anyBoolean()))
                .thenReturn(List.of(Address.builder().build()));
        //then
        List<AddressDto> addressDtos = addressService.findAllAddresses(Boolean.TRUE);

        assertNotNull(addressDtos);
        assertFalse(addressDtos.isEmpty());
        verify(addressRepository, times(1))
                .findAllAddressesByVisibility(anyBoolean());
    }

    @Test
    void findAllAddressesByPersonsId() {
        //when
        when(addressRepository.findAllAddressesByPersonsIdIsInAndVisibilityIsLike(anyList(), anyBoolean()))
                .thenReturn(List.of(Address.builder().build()));
        //then
        List<AddressDto> addressDtos = addressService.findAllAddressesByPersonsId(true, List.of(1L));

        assertNotNull(addressDtos);
        assertFalse(addressDtos.isEmpty());
        verify(addressRepository, times(1))
                .findAllAddressesByPersonsIdIsInAndVisibilityIsLike(anyList(), anyBoolean());
    }

    @Test
    void createListOfAddresses() {
        //when
        when(addressRepository.saveAll(any()))
                .thenReturn(List.of(Address.builder().build()));
        //then
        List<AddressDto> addressDtos = addressService.createListOfAddresses(List.of(AddressCreateInputDto.builder().build()));

        assertNotNull(addressDtos);
        assertFalse(addressDtos.isEmpty());
        verify(addressRepository, times(1))
                .saveAll(any());
    }

    @Test
    void updateListOfAddresses() {
        //when
        when(addressRepository.saveAll(any()))
                .thenReturn(List.of(Address.builder().build()));
        //then
        List<AddressDto> addressDtos = addressService.updateListOfAddresses(List.of(AddressDto.builder().build()));

        assertNotNull(addressDtos);
        assertFalse(addressDtos.isEmpty());
        verify(addressRepository, times(1))
                .saveAll(any());
    }
}