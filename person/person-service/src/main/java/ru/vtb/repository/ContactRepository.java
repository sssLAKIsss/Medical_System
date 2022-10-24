package ru.vtb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.model.Contact;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Query(value = "from Contact c " +
            "where c.id = :id and " +
            "(c.visibility = :visibility and :visibility is not null or " +
            ":visibility is null) ")
    Optional<Contact> findContactById(@Param("id") Long id, @Param("visibility") Boolean visibility);

    @Query(value = "from Contact c " +
            "where c.personId = :personId and" +
            "(c.visibility = :visibility and :visibility is not null or :visibility is null) ")
    List<Contact> findAllContactsByPersonIdAndVisibility(@Param("personId") Long personId,
                                                         @Param("visibility") Boolean visibility);

    @Query(value = "from Contact c " +
            "where c.visibility = :visibility and :visibility is not null or " +
            ":visibility is null ")
    List<Contact> findAllContactsByVisibility(@Param("visibility") Boolean visibility);

    @Query(value = "from Contact c " +
            "where c.personId in :personsId and" +
            "(c.visibility = :visibility and :visibility is not null or :visibility is null) ")
    List<Contact> findAllContactsByPersonIdIsInAndVisibilityIsLike(@Param("personsId") List<Long> personsId,
                                                                    @Param("visibility") Boolean visibility);

    @Transactional
    @Modifying
    @Query(value = "update Contact c set c.visibility = :visibility where c.id in :contactsId")
    void setVisibilityToContacts(@Param("visibility") Boolean visibility, @Param("contactsId") List<Long> contactsId);
}
