package ru.vtb.dto.createInput;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.vtb.validator.EnumNamePattern;

import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class ContactCreateInputDto {

    @Schema(description = "Тип контакта: мобильный, домашний",
            example = "HOME",
            required = true)
    @EnumNamePattern(regexp = "HOME|MOBILE", message = "Тип контакта может быть только {regexp}")
    private String type;

    @Pattern(regexp = "^[+][0-9]{11}$",
            message = "Номер телефона должен начинаться с '+', далее 11 символов без пробелов")
    @Schema(description = "Номер телефона",
            example = "+79999999999",
            required = true)
    private String phoneNumber;
}
