package ru.vtb.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.vtb.mapper.IModelMapper;
import ru.vtb.model.Patient;
import ru.vtb.model.Vaccination;
import ru.vtb.model.VaccinationPoint;
import ru.vtb.model.Vaccine;
import ru.vtb.model.csv.CsvFileStructure;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class CsvFileStructureMapper implements IModelMapper<Patient, CsvFileStructure> {
    private final ModelMapper modelMapper;

    public Patient convertToEntity(CsvFileStructure dto) {
        Patient patient = modelMapper.map(dto, Patient.class);

        VaccinationPoint vaccinationPoint = VaccinationPoint.builder()
                .address(dto.getAddress())
                .vaccinationPointNumber(dto.getVaccinationPointNumber())
                .vaccinationPointTitle(dto.getVaccinationPointTitle())
                .city(dto.getCity())
                .build();

        Vaccine vaccine = Vaccine.builder()
                .vaccineTitle(dto.getVaccineTitle())
                .build();

        Vaccination vaccination = Vaccination.builder()
                .vaccine(vaccine)
                .vaccinationPoint(vaccinationPoint)
                .chippingDate(dto.getChippingDate())
                .build();

        patient.setVaccinations(Set.of(vaccination));
        return patient;
    }

    @Override
    public CsvFileStructure convertToDto(Patient entity) {
        return null;
    }
}
