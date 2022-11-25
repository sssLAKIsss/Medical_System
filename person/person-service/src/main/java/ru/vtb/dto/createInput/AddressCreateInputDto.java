package ru.vtb.dto.createInput;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.vtb.validator.EnumNamePattern;

import javax.validation.constraints.Min;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AddressCreateInputDto {

    @Schema(description = "Тип адреса: регистрация или временный",
            example = "REGISTRATION",
            required = true)
    @EnumNamePattern(regexp = "REGISTRATION|TEMPORARY", message = "Тип адреса может быть только {regexp}")
    private String type;

    @Schema(description = "Страна проживания/регистрации",
            example = "Russia",
            required = true)
    private String country;

    @Schema(description = "Регион проживания/регистрации",
            example = "Республика Башкортостан",
            required = true)
    private String region;

    @Schema(description = "Город проживания/регистрации",
            example = "Стерлитамак",
            required = true)
    private String city;

    @Schema(description = "Улица проживания/регистрация",
            example = "Гоголя",
            required = true)
    private String street;

    @Schema(description = "Дом проживания/регситрации",
            example = "106",
            required = true)
    private Long home;

    @Schema(description = "Квартира проживания/регистрации",
            example = "1",
            required = false,
            nullable = true)
    @Min(value = 1, message = "Квартира - натуральное число, необразованное быдло!")
    private Long flat;
}
