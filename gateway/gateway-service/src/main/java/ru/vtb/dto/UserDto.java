package ru.vtb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.vtb.validator.EnumNamePattern;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@AllArgsConstructor
@Data
@Builder
@Schema(description = "Необходимые данные для сохранения нового пользователя для авторизации/аутентификации")
public class UserDto {

    @NotBlank
    @Schema(description = "Логин будущего пользователя", required = true)
    private String username;

    @NotBlank
    @Schema(description = "Пароль будущего пользователя", required = true)
    private String password;
    @NotNull
    @EnumNamePattern(regexp = "ROLE_USER|ROLE_ADMIN", message = "Тип роли может быть только {regexp}")
    @Schema(description = "Роль будущего пользователя", required = true)
    private String role;
}
