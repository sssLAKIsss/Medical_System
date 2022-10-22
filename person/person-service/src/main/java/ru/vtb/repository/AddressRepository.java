package ru.vtb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.model.Address;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    @Query(value = "from Address a " +
            "where a.visibility = :visibility and :visibility is not null or " +
            ":visibility is null ")
    List<Address> findAllAddressesByVisibility(@Param("visibility") Boolean visibility);

    @Query(value = "from Address a " +
            "join a.persons p where " +
            "p.id in :personsId and  " +
            "(a.visibility = :visibility and :visibility is not null or :visibility is null) ")
    List<Address> findAllAddressesByPersonsIdIsInAndVisibilityIsLike(@Param("personsId") List<Long> personsId,
                                                                     @Param("visibility") Boolean visibility);

    @Transactional
    @Modifying
    @Query(value = "update Address a set a.visibility = :visibility where a.id in :addressesId")
    void setVisibilityToAddresses(@Param("visibility") Boolean visibility,
                                  @Param("addressesId") List<Long> addressesId);
}
