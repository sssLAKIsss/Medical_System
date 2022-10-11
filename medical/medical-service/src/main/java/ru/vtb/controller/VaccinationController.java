package ru.vtb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vtb.dto.PatientDto;
import ru.vtb.personservice.client.model.ApiError;
import ru.vtb.service.IPatientService;

@Tag(
        name = "patient-controller",
        description = "Позволяет получить данные о вакцинации пациента по номеру паспорта"
)
@RequestMapping("/api/v1/medical")
@RestController
@RequiredArgsConstructor
@Validated
public class VaccinationController {
    private final IPatientService patientService;

    @Operation(
            summary = "Получить данные о вакцинации",
            description = "Позволяет получить данные о вакцинации по номеру паспорта"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = PatientDto.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    @GetMapping("/patient/{passportNumber}")
    public ResponseEntity<PatientDto> getByPassportNumber(@PathVariable String passportNumber) {
        return ResponseEntity.ok(patientService.getAllInfoAboutPatientByPassportNumber(passportNumber));
    }
}
