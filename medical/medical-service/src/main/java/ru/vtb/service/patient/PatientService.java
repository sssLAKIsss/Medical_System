package ru.vtb.service.patient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.dto.PatientDto;
import ru.vtb.mapper.IModelMapper;
import ru.vtb.model.Patient;
import ru.vtb.repository.PatientRepository;
import ru.vtb.repository.VaccinationPointRepository;
import ru.vtb.repository.VaccineRepository;
import ru.vtb.service.queue.IDataQueue;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientService implements IPatientService {
    private final PatientRepository patientRepository;
    private final VaccinationPointRepository vaccinationPointRepository;
    private final VaccineRepository vaccineRepository;
    private final IDataQueue<Long> patientDataQueue;
    private final IModelMapper<Patient, PatientDto> patientMapper;

    @Override
    @Transactional(readOnly = true)
    public PatientDto getAllInfoAboutPatientByPassportNumber(String passportNumber) {
        return patientMapper.convertToDto(
                patientRepository
                        .findByDocumentNumber(passportNumber)
                        .orElseThrow(() -> new RuntimeException("Patient not found"))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public void checkVaccinationAndVaccinationPointsInDataBase(Patient patient) {
        patient.getVaccinations()
                .stream()
                .filter(v ->
                        Objects.isNull(v.getVaccine().getId()) ||
                                Objects.isNull(v.getVaccinationPoint().getId()))
                .forEach(
                        v -> {
                            v.setVaccine(vaccineRepository
                                    .findByVaccineTitle(v.getVaccine().getVaccineTitle())
                                    .orElse(v.getVaccine()));
                            v.setVaccinationPoint(vaccinationPointRepository
                                    .findByVaccinationPointNumber(v.getVaccinationPoint().getVaccinationPointNumber())
                                    .orElse(v.getVaccinationPoint()));
                        }
                );
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean saveOperation(Patient patient) {
        log.info("Save vaccinationData to DB");
        patientRepository.save(patient);
        log.info("Send vaccinationData to kafka");
        patientDataQueue.putInQueue(patient.getId());
        return true;
    }
}
