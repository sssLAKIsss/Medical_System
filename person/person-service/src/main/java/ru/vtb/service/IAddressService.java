package ru.vtb.service;

import ru.vtb.dto.createInput.AddressCreateInputDto;
import ru.vtb.dto.getOrUpdate.AddressDto;

import java.util.List;
import java.util.Map;

public interface IAddressService {
    AddressDto findById(Long id);
    List<AddressDto> findAllAddresses(Boolean visibility);
    Map<Long, List<AddressDto>> findAllAddressesByPersonsId(Boolean visibility, List<Long> personsId);

    List<Long> createListOfAddresses(List<AddressCreateInputDto> addresses);
    List<Long> updateListOfAddresses(List<AddressDto> addresses);

    void setAddressesVisibility(boolean visibility, List<Long> addressesId);
    void deleteAddressesFromDB(List<Long> addressesId);
    void deleteAllAddresses();

}
