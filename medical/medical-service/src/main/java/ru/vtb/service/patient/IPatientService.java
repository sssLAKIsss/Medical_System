package ru.vtb.service.patient;


import ru.vtb.dto.PatientDto;
import ru.vtb.model.Patient;

public interface IPatientService {
    PatientDto getAllInfoAboutPatientByPassportNumber(String passportNumber);

    void checkVaccinationAndVaccinationPointsInDataBase(Patient patient);

    boolean saveOperation(Patient patient);
}