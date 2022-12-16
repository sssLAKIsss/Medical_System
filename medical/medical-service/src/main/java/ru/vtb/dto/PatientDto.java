package ru.vtb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Pattern;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@SuperBuilder
public class PatientDto {

    @Schema(description = "Фамилия вакцинальщика",
            example = "Андреев",
            required = true)
    private String lastName;
    @Schema(description = "Имя вакцинальщика",
            example = "Андрей",
            required = true)
    private String firstName;
    @Schema(description = "Отчество вакцинальщика",
            example = "Андреич",
            required = true)
    private String patronymic;

    @Pattern(regexp = "^[0-9]{10}$",
            message = "Серия и номер пасспорта должны быть одной строкой без пробела 10 символов")
    @Schema(description = "Номер паспорта",
            example = "4510003221",
            required = true)
    private String documentNumber;

    @Schema(description = "Вакцинация")
    private List<VaccinationDto> vaccinationDtos;
}
