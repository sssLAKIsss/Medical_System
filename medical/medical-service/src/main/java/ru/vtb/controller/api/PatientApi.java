package ru.vtb.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vtb.controller.api.custom.ApiParams;
import ru.vtb.controller.api.custom.ApiV1;
import ru.vtb.dto.PatientDto;

import javax.validation.constraints.Pattern;

@Tag(
        name = "patient-controller",
        description = "Позволяет получить данные о вакцинации пациента по номеру паспорта"
)
@Validated
@ApiParams
@ApiV1
public interface PatientApi {

    @Operation(
            summary = "Получить данные о вакцинации",
            description = "Позволяет получить данные о вакцинации по номеру паспорта"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = PatientDto.class)))
    })
    @GetMapping("/medical/patient")
    ResponseEntity<PatientDto> getByPassportNumber(
            @RequestParam @Pattern(regexp = "^[0-9]{10}$", message = "Номер документа строго 10 цифр") String passportNumber
    );
}
