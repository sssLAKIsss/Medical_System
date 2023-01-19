package ru.vtb.service;

public interface IMedicalDataListener {
    void saveVaccinationDataFromKafka(String vaccinationDto);
}
