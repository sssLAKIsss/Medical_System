package ru.vtb.service;

import org.springframework.web.multipart.MultipartFile;

public interface IVaccinationCSVDataService {
    void uploadDataFromFile(MultipartFile file);
}
