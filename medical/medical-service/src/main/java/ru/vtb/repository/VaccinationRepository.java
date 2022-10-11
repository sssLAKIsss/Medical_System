package ru.vtb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vtb.model.Vaccination;

import java.util.Optional;

@Repository
public interface VaccinationRepository extends JpaRepository<Vaccination, Long> {
    @Query(value = "from Patient p where p.documentNumber = :documentNumber and p.isDeleted = false")
    Optional<Vaccination> findAllByPatientDocumentNumber(@Param("documentNumber") String documentNumber);
}
