package ru.vtb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vtb.model.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query(value = "from Person p " +
            "where p.id = :id and " +
            "(p.visibility = :visibility and :visibility is not null or " +
            ":visibility is null) ")
    Optional<Person> findByIdAndVisibility(@Param("id") Long id,
                                           @Param("visibility") Boolean visibility);
    @Query(value = "from Person p " +
            "join p.documents as d " +
            "where d.type = 'PASSPORT' and d.number = :passportNumber and d.visibility = true and" +
            "(p.visibility = :visibility and :visibility is not null or " +
            ":visibility is null)")
    Optional<Person> findByPassportNumber(@Param("passportNumber") String passportNumber,
                                          @Param("visibility") Boolean visibility);

    //TODO сделать пагинацию не в памяти
    @Query(value = "select p from Person as p " +
            "join p.addresses as a " +
            "where (p.visibility= :visibility and :visibility is not null or :visibility is null)  " +
            "and (a.region= cast(:region as string) and cast(:region as string) is not null or cast(:region as string) is null) ")
    List<Person> findAllPersonsWithPaginationByVisibility(@Param("visibility") Boolean visibility,
                                                          @Param("region") String region,
                                                          Pageable pageable);

    @Modifying
    @Query(value = "update Person p set p.visibility = :visibility where p.id in :personsId")
    void setVisibilityToDocuments(@Param("visibility") Boolean visibility,
                                  @Param("personsId") List<Long> personsId);
}