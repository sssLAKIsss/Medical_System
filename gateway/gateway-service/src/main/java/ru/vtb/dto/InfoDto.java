package ru.vtb.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.vtb.medicalservice.client.model.PatientDto;
import ru.vtb.personservice.client.model.PersonDto;


@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@Builder
public class InfoDto {

    private PersonDto personDto;

    private PatientDto patientDto;

    private String bufferedImage;
}
