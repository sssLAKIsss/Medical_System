package ru.vtb.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    boolean existsPersonByFirstNameAndLastNameAndPatronymic(
            String firstName,
            String lastName,
            String patronymic
    );

    @Query(value = "from Person p " +
            "where p.id = :id and " +
            "(p.visibility = :visibility and :visibility is not null or " +
            ":visibility is null) ")
    Optional<Person> findByIdAndVisibility(@Param("id") Long id,
                                           @Param("visibility") Boolean visibility);
    @Query(value = "from Person p " +
            "left join p.documents as d " +
            "where d.type = 'PASSPORT' and d.number = :passportNumber and d.visibility = true and" +
            "(p.visibility = :visibility and :visibility is not null or " +
            ":visibility is null)")
    Optional<Person> findByPassportNumber(@Param("passportNumber") String passportNumber,
                                          @Param("visibility") Boolean visibility);

    @Query(value = "select p from Person as p " +
            "left join p.addresses as a " +
            "where (p.visibility= :visibility and :visibility is not null or :visibility is null)  " +
            "and (a.region= cast(:region as string) and cast(:region as string) is not null or cast(:region as string) is null) ")
    List<Person> findAllPersonsWithPaginationByVisibility(@Param("visibility") Boolean visibility,
                                                          @Param("region") String region,
                                                          Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "delete from Person p where p.id in :personsId")
    void deletePersonsById(@Param("personsId") Set<Long> personsId);

    @Transactional
    @Modifying
    @Query(value = "delete from Person p")
    void deleteAllPersons();
}