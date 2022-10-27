package ru.vtb.dto.createInput;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.vtb.model.type.DocumentType;
import ru.vtb.validator.EnumNamePattern;

import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class DocumentCreateInputDto {

    @Schema(description = "Тип документа: паспорт, снилс",
            example = "PASSPORT",
            required = true)
    @EnumNamePattern(regexp = "SNILS|PASSPORT", message = "Тип документа может быть только {regexp}")
    private String type;

    @Pattern(regexp = "^[0-9]{10}$",
            message = "Серия и номер пасспорта должны быть одной строкой без пробела 10 символов")
    @Schema(description = "Серия и номер документа без пробела",
            example = "4515001002",
            required = true)
    private String number;
}
