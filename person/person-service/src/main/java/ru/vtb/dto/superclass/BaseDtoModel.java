package ru.vtb.dto.superclass;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseDtoModel {

    @Schema(description = "Видимость сущности для конечного пользователя", defaultValue = "true")
    private boolean visibility;

    @Schema(description = "Параметр версионирования сущности")
    private int version;

    @Schema(description = "Дата последнего изменения сущности")
    private String lastModifiedDate;

    @Schema(description = "Дата создания сущности")
    private String dateTimeCreation;

}
