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
public class VaccinationPointDto extends BaseDtoModel {

    @Schema(description = "Уникальный номер пункта вакцинации",
            example = "Пункт №1",
            required = true)
    private String vaccinationPointNumber;

    @Schema(description = "Название пункта вакцинации",
            example = "Детская неожиданность",
            required = true)
    private String vaccinationPointTitle;

    @Schema(description = "Город",
            example = "Волгоград",
            required = true)
    private String city;

    @Schema(description = "Адрес пункта вакцинации",
            example = "Менделеева 104, 55",
            required = true)
    private String address;
}
