package ru.vtb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import ru.vtb.dto.superclass.BaseDtoModel;

@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@SuperBuilder
public class VaccinationDto extends BaseDtoModel {

    @Schema(description = "Дата вакцинации",
            required = true)
    private String chippingDate;

    @Schema(description = "Вакцины, которые перенес вакцинальщик",
            required = true)
    private VaccineDto vaccine;

    @Schema(description = "Пункты вакцинации вакцинальщика",
            required = true)
    private VaccinationPointDto vaccinationPoint;
}
