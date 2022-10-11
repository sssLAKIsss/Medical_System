package ru.vtb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.vtb.model.VaccinationPoint;

import java.util.Optional;

@Repository
public interface VaccinationPointRepository extends JpaRepository<VaccinationPoint, Long> {
    Optional<VaccinationPoint> findByVaccinationPointNumber(@Param("vaccinationPointNumber") String vaccinationPointNumber);
}
