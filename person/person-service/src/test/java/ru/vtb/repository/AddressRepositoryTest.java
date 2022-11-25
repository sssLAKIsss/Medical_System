package ru.vtb.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import ru.vtb.TestContainerSetup;
import ru.vtb.model.Address;
import ru.vtb.model.superclass.BaseDateVersionEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@Sql(scripts = "sql/address/prepare-address-data.sql", executionPhase = BEFORE_TEST_METHOD)
@Sql(scripts = "sql/address/drop-address-data.sql", executionPhase = AFTER_TEST_METHOD)
class AddressRepositoryTest extends TestContainerSetup {
    @Autowired
    protected AddressRepository addressRepository;

    @Test
    void findAllAddressesByVisibility_visibilityTrue() {
        List<Address> addresses = addressRepository.findAllAddressesByVisibility(true);
        assertNotNull(addresses);
        assertFalse(addresses.isEmpty());
        assertThat(addresses).hasSize(1);
        assertTrue(addresses.stream().allMatch(BaseDateVersionEntity::isVisibility));
    }
    @Test
    void findAllAddressesByVisibility_visibilityFalse() {
        List<Address> addresses = addressRepository.findAllAddressesByVisibility(true);
        assertNotNull(addresses);
        assertFalse(addresses.isEmpty());
        assertThat(addresses).hasSize(1);
        assertFalse(addresses.stream().noneMatch(BaseDateVersionEntity::isVisibility));
    }
    @Test
    void findAllAddressesByVisibility_visibilityIgnore() {
        List<Address> addresses = addressRepository.findAllAddressesByVisibility(null);
        assertNotNull(addresses);
        assertFalse(addresses.isEmpty());
        assertThat(addresses).hasSize(2);
    }

    @Test
    void findAllAddressesByPersonsIdIsInAndVisibilityIsLike() {
        List<Address> addresses = addressRepository.findAllAddressesByPersonsIdIsInAndVisibilityIsLike(List.of(1L),true);
        assertNotNull(addresses);
        assertFalse(addresses.isEmpty());
        assertThat(addresses).hasSize(1);
    }

    @Test
    void setVisibilityToAddresses() {
        addressRepository.setVisibilityToAddresses(false, List.of(1L));
        assertNotNull(addressRepository.findById(1L));
        assertFalse(addressRepository.findById(1L).orElseThrow().isVisibility());
    }

    @Test
    void findAllByPersonIdAndVisibility() {
        List<Address> addresses = addressRepository.findAllAddressesByPersonIdAndVisibility(1L, true);
        assertNotNull(addresses);
        assertFalse(addresses.isEmpty());
        assertThat(addresses).hasSize(1);
    }

    @Test
    void existsAddressByCountryAndCityAndRegionAndStreetAndHomeAndFlatExists() {
        assertTrue(addressRepository.existsAddressByCountryAndCityAndRegionAndStreetAndHomeAndFlat(
                "Russia", "UFA", "RB", "GOGOl9", 106L, null));
        assertFalse(addressRepository.existsAddressByCountryAndCityAndRegionAndStreetAndHomeAndFlat(
                "Russia", "UFA", "RB", "GOGOl9", 106L, 1L));
    }
}