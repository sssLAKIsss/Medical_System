package ru.vtb.dto.createInput;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.vtb.model.type.ContactType;

import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ContactCreateInputDto {

    @Schema(description = "Тип контакта: мобильный, домашний",
            example = "HOME",
            required = true)
    private ContactType type;

    @Pattern(regexp = "^[+][0-9]{11}$",
            message = "Номер телефона должен начиться с '+', далее 11 символов без пробелов")
    @Schema(description = "Номер телефона",
            example = "+79999999999",
            required = true)
    private String phoneNumber;
}
