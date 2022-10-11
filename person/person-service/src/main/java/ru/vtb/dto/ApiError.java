package ru.vtb.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Schema(description = "Ошибка обработки запроса")

@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    @Schema(description = "Статус")
    private int status;
    @Schema(description = "Системное сообщение ошибки")
    private String message;
    @Schema(description = "Ошибка")
    private String error;
    @Schema(description = "Время, когда произошла ошибка")
    private LocalDateTime timestamp;
    @Schema(description = "Сообщение исключеня java")
    private String rawMessage;
}
