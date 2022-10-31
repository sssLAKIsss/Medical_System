package ru.vtb.dto.getOrUpdate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ru.vtb.dto.superclass.BaseDtoModel;
import ru.vtb.model.type.DocumentType;

import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class DocumentDto extends BaseDtoModel {

    @Schema(description = "Идентификатор",
            example = "1",
            required = true)
    private Long id;

    @Schema(description = "Тип документа: паспорт, снилс",
            example = "PASSPORT",
            required = true)
    private DocumentType type;

    @Pattern(regexp = "^[0-9]{10}$",
            message = "Серия и номер пасспорта должны быть одной строкой без пробела 10 символов")
    @Schema(description = "Серия и номер документа без пробела",
            example = "4515001002",
            required = true)
    private String number;
}
