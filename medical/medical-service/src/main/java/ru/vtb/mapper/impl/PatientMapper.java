package ru.vtb.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.vtb.dto.PatientDto;
import ru.vtb.dto.VaccinationDto;
import ru.vtb.dto.VaccinationPointDto;
import ru.vtb.dto.VaccineDto;
import ru.vtb.mapper.IModelMapper;
import ru.vtb.model.Patient;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PatientMapper implements IModelMapper<Patient, PatientDto> {
    private final ModelMapper mapper;

    @Override
    public Patient convertToEntity(PatientDto dto) {
        return mapper.map(dto, Patient.class);
    }

    @Override
    public PatientDto convertToDto(Patient entity) {
        //todo разобраться с маппером
        PatientDto p = mapper.map(entity, PatientDto.class);
        p.setVaccinationDtos(
                entity.getVaccinations()
                        .stream()
                        .map(v -> mapper.map(v, VaccinationDto.class))
                        .collect(Collectors.toList())
        );
        p.getVaccinationDtos().forEach(v -> {
                    v.setVaccine(mapper.map(v.getVaccine(), VaccineDto.class));
                    v.setVaccinationPoint(mapper.map(v.getVaccinationPoint(), VaccinationPointDto.class));
                });
        return p;
    }
}