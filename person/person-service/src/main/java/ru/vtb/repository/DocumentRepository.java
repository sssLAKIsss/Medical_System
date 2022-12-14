package ru.vtb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vtb.model.Document;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Query(value = "from Document d " +
            "where d.id = :id and " +
            "(d.visibility = :visibility and :visibility is not null or " +
            ":visibility is null) ")
    Optional<Document> findDocumentById(@Param("id") Long id, @Param("visibility") Boolean visibility);

    @Query(value = "from Document d " +
            "where d.number = :number and " +
            "(d.visibility = :visibility and :visibility is not null or " +
            ":visibility is null) ")
    Optional<Document> findDocumentByNumber(@Param("number") String number, @Param("visibility") Boolean visibility);

    @Query(value = "from Document d " +
            "where d.visibility = :visibility and :visibility is not null or " +
            ":visibility is null ")
    List<Document> findAllDocumentsByVisibility(@Param("visibility") Boolean visibility);

    @Query(value = "from Document d " +
            "where d.personId in :personsId and" +
            "(d.visibility = :visibility and :visibility is not null or :visibility is null) ")
    List<Document> findAllDocumentsByPersonsId(
            @Param("personsId") List<Long> personsId,
            @Param("visibility") Boolean visibility);

    @Modifying
    @Query(value = "update Document d set d.visibility = :visibility where d.id in :documentsId")
    void setVisibilityToDocuments(@Param("visibility") Boolean visibility, @Param("documentsId") List<Long> documentsId);

    @Modifying
    @Query(value = "update Document d set d.visibility = :visibility where d.personId in :personsId")
    void setVisibilityToDocumentsByPersonsId(@Param("visibility") Boolean visibility, @Param("personsId") List<Long> personsId);
}
