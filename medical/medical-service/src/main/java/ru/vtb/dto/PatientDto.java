package ru.vtb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@Builder
public class PatientDto {

    @Schema(description = "ФИО вакцинальщика",
            example = "Андреев Андрей Андреич",
            required = true)
    private String fullName;

    @Schema(description = "Номер паспорта",
            example = "4510003221",
            required = true)
    private String documentNumber;

    @Schema(description = "Вакцинация",
            required = false)
    private List<VaccinationDto> vaccinationDtos;
}
