package ru.vtb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.vtb.controller.api.PatientApi;
import ru.vtb.dto.PatientDto;
import ru.vtb.service.IPatientService;

@RestController
@RequiredArgsConstructor
public class PatientController implements PatientApi {
    private final IPatientService patientService;

    @Override
    public ResponseEntity<PatientDto> getByPassportNumber(String passportNumber) {
        return ResponseEntity.ok(patientService.getAllInfoAboutPatientByPassportNumber(passportNumber));
    }
}
