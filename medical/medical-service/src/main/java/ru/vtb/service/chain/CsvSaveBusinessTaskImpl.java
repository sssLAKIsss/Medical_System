package ru.vtb.service.chain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.vtb.mapper.IModelMapper;
import ru.vtb.model.Patient;
import ru.vtb.model.Vaccination;
import ru.vtb.model.csv.CsvFileStructure;
import ru.vtb.repository.PatientRepository;
import ru.vtb.service.IPatientService;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class CsvSaveBusinessTaskImpl implements IBusinessTask<CsvFileStructure> {
    private final PatientRepository patientRepository;
    private final IPatientService patientService;
    private final IModelMapper<Patient, CsvFileStructure> patientMapper;

    @Override
    @Transactional
    public boolean execute(CsvFileStructure obj) {
        try {
            Patient newPatient = patientMapper.convertToEntity(obj);

            Optional<Patient> oldPatientOpt = patientRepository
                    .findByDocumentNumber(newPatient.getDocumentNumber());

            if (oldPatientOpt.isPresent() && oldPatientOpt.get().equals(newPatient)) {

                if (oldPatientOpt.get().getVaccinations()
                        .stream()
                        .anyMatch(v -> newPatient.getVaccinations().contains(v))) {
                    return false;
                }
                for (Vaccination v : newPatient.getVaccinations()) {
                    oldPatientOpt.get().getVaccinations().add(v);
                }
                patientService.checkVaccinationAndVaccinationPointsInDataBase(oldPatientOpt.get());
                return patientService.loggingAndSaveOperation(oldPatientOpt.get());
            }
            patientService.checkVaccinationAndVaccinationPointsInDataBase(newPatient);
            return patientService.loggingAndSaveOperation(newPatient);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("Failed to save csvData in Database");
            return false;
        }
    }

    @Override
    public OperationType getOperationName() {
        return OperationType.SAVE;
    }
}
