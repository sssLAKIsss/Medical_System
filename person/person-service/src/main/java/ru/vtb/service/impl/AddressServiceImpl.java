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

import java.util.List;
import java.util.Map;
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
    public List<AddressDto> createListOfAddresses(List<AddressCreateInputDto> addresses) {
        return addressRepository.saveAll(
                        addresses.stream()
                                .map(addressMapper::convertFromCreateDto)
                                .collect(Collectors.toList()))
                .stream()
                .map(addressMapper::convertToOutputDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<AddressDto> updateListOfAddresses(List<AddressDto> addresses) {
        return addressRepository.saveAll(
                        addresses.stream()
                                .map(addressMapper::convertFromUpdateDto)
                                .collect(Collectors.toList()))
                .stream()
                .map(addressMapper::convertToOutputDto)
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
        addressRepository.deleteAllById(addressesId);
    }

    @Override
    @Transactional
    public void deleteAllAddresses() {
        addressRepository.deleteAll();
    }
}
