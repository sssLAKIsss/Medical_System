package ru.vtb.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.dto.createInput.AddressCreateInputDto;
import ru.vtb.dto.getOrUpdate.AddressDto;
import ru.vtb.exception.AddressNotFoundException;
import ru.vtb.mapper.IModelMapper;
import ru.vtb.model.Address;
import ru.vtb.repository.AddressRepository;
import ru.vtb.service.IAddressService;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements IAddressService {
    private final IModelMapper<Address, AddressCreateInputDto, AddressDto> addressMapper;
    private final AddressRepository addressRepository;

    @Override
    @Transactional(readOnly = true)
    public AddressDto findById(Long id) {
        return addressMapper.convertToOutputDto(
                addressRepository.findById(id).orElseThrow(AddressNotFoundException::new)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<AddressDto> findAllAddresses(Boolean visibility) {
        return addressRepository.findAllAddressesByVisibility(visibility)
                .stream()
                .map(addressMapper::convertToOutputDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Long, List<AddressDto>> findAllAddressesByPersonsId(Boolean visibility, List<Long> personsId) {
        return personsId.stream()
                .map(personId ->
                        Map.entry(
                                personId,
                                addressRepository.findAllAddressesByPersonIdAndVisibility(personId, visibility)
                                        .stream()
                                        .map(addressMapper::convertToOutputDto)
                                        .collect(Collectors.toList())
                        ))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    @Transactional
    public List<Long> createListOfAddresses(List<AddressCreateInputDto> addresses) {
        return addressRepository.saveAll(
                        addresses.stream()
                                .map(addressMapper::convertFromCreateDto)
                                .filter(address -> !addressRepository
                                        .existsAddressByCountryAndCityAndRegionAndStreetAndHomeAndFlat(
                                                address.getCountry(),
                                                address.getCity(),
                                                address.getRegion(),
                                                address.getStreet(),
                                                address.getHome(),
                                                Objects.isNull(address.getFlat())
                                                        ? null
                                                        : address.getFlat()
                                        ))
                                .collect(Collectors.toList()))
                .stream()
                .map(Address::getId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<Long> updateListOfAddresses(List<AddressDto> addresses) {
        return addressRepository.saveAll(
                        addresses.stream()
                                .map(addressMapper::convertFromUpdateDto)
                                .collect(Collectors.toList()))
                .stream()
                .map(Address::getId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void setAddressesVisibility(boolean visibility, List<Long> addressesId) {
        addressRepository.setVisibilityToAddresses(visibility, addressesId);
    }

    @Override
    @Transactional
    public void deleteAddressesFromDB(List<Long> addressesId) {
        addressRepository.deleteAddressesById(new HashSet<>(addressesId));
    }

    @Override
    @Transactional
    public void deleteAllAddresses() {
        addressRepository.deleteAllAddresses();
    }
}
