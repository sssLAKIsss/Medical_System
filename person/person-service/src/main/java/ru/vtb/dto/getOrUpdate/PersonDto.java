package ru.vtb.dto.getOrUpdate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.vtb.dto.superclass.BaseDtoModel;

import javax.validation.Valid;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PersonDto extends BaseDtoModel {

    @Schema(description = "Идентификатор",
            example = "1",
            required = true)
    private Long id;

    @Schema(description = "Фамилия пользователя",
            example = "Федоров",
            required = true)
    private String lastName;

    @Schema(description = "Имя пользователя",
            example = "Федор",
            required = true)
    private String firstName;

    @Schema(description = "Отчество пользователя",
            example = "Федорович",
            required = false)
    private String patronymic;

    @Valid
    @Schema(description = "Список документов, принадлежащих пользователю",
            required = false)
    private Set<DocumentDto> documents;

    @Valid
    @Schema(description = "Список адресов, где зарегистрирован/проживает пользователь",
            required = false)
    private Set<AddressDto> addresses;

    @Valid
    @Schema(description = "Список контактов, принадлежащих пользователю",
            required = false)
    private Set<ContactDto> contacts;
}
