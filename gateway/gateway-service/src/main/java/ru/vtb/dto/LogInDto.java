package ru.vtb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Data
@Builder
@Schema(description = "Данные для авторизации/аутентификации клиента")
public class LogInDto {
    @NotBlank
    @Schema(description = "Логин пользователя", required = true)
    private String userLogin;
    @NotBlank
    @Schema(description = "Пароль пользователя", required = true)
    private String userPassword;
}
