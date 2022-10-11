package ru.vtb.service;

import ru.vtb.dto.createInput.AddressCreateInputDto;
import ru.vtb.dto.getOrUpdate.AddressDto;

import java.util.List;

public interface IAddressService {
    AddressDto findById(Long id);
    List<AddressDto> findAllAddresses(Boolean visibility);
    List<AddressDto> findAllAddressesByPersonsId(Boolean visibility, List<Long> personsId);

    List<AddressDto> createListOfAddresses(List<AddressCreateInputDto> addresses);
    List<AddressDto> updateListOfAddresses(List<AddressDto> addresses);

    void setAddressesVisibility(boolean visibility, List<Long> addressesId);
    void deleteAddressesFromDB(List<Long> addressesId);
    void deleteAllAddresses();

}
