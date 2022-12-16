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
public class VaccineDto extends BaseDtoModel {

    @Schema(description = "Название вакцины",
            example = "Спутник-5",
            required = true)
    private String vaccineTitle;
}
