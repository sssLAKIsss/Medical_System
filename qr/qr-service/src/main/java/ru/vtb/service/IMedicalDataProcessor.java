package ru.vtb.service;

public interface IMedicalDataProcessor {
    void saveVaccinationDataFromKafka(String vaccinationDto);
}
