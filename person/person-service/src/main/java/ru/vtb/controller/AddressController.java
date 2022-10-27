package ru.vtb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.vtb.controller.api.AddressApi;
import ru.vtb.dto.createInput.AddressCreateInputDto;
import ru.vtb.dto.getOrUpdate.AddressDto;
import ru.vtb.service.IAddressService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AddressController implements AddressApi {
    private final IAddressService addressService;

    @Override
    public ResponseEntity<AddressDto> findAddressById(Long id) {
        return ResponseEntity.ok(addressService.findById(id));
    }

    @Override
    public ResponseEntity<List<AddressDto>> findAllAddresses(Boolean visibility) {
        return ResponseEntity.ok(addressService.findAllAddresses(visibility));
    }

    @Override
    public ResponseEntity<Map<Long, List<AddressDto>>> findAllAddressesByPersonsId(Boolean visibility, List<Long> personsId) {
        return ResponseEntity.ok(addressService.findAllAddressesByPersonsId(visibility, personsId));
    }

    @Override
    public ResponseEntity<List<Long>> saveAddresses(List<AddressCreateInputDto> addresses) {
        return ResponseEntity.ok(addressService.createListOfAddresses(addresses));
    }

    @Override
    public ResponseEntity<List<Long>> updateAddresses(List<AddressDto> addresses) {
        return ResponseEntity.ok(addressService.updateListOfAddresses(addresses));
    }

    @Override
    public ResponseEntity<HttpStatus> setVisibilityToAddresses(List<Long> addresses, Boolean visibility) {
        addressService.setAddressesVisibility(visibility, addresses);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteDocumentsById(List<Long> addressesId) {
        addressService.deleteAddressesFromDB(addressesId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<HttpStatus> deleteAllDocuments() {
        addressService.deleteAllAddresses();
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
