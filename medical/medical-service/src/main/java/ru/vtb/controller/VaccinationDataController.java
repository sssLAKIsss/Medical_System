package ru.vtb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.vtb.controller.api.VaccinationApi;
import ru.vtb.service.IVaccinationCSVDataService;

@RestController
@RequiredArgsConstructor
public class VaccinationDataController implements VaccinationApi {
    private final IVaccinationCSVDataService vaccinationCSVData;

    @Override
    public ResponseEntity<HttpStatus> saveCsvVaccinationFile(MultipartFile file) {
        vaccinationCSVData.uploadDataFromFile(file);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
