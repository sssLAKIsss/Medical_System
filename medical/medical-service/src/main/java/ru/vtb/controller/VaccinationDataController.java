package ru.vtb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.vtb.personservice.client.model.ApiError;
import ru.vtb.service.IVaccinationCSVDataService;

@Tag(
        name = "vaccination-data-controller",
        description = "Позволяет загрузить данные с csv-файла"
)
@RequestMapping("/api/v1/medical")
@RestController
@RequiredArgsConstructor
@Validated
public class VaccinationDataController {
    private final IVaccinationCSVDataService vaccinationCSVData;

    @Operation(
            summary = "Загрузить данные с файла",
            description = "Позволяет загрузить данные с csv-файла, файл имеет стурктуру: " +
                    "ФИО" +
                    "паспорт" +
                    "дата вакцинации" +
                    "название вакцины" +
                    "уникальный номер пункта вакцинации" +
                    "название пункта вакцинации" +
                    "адрес пункта"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = HttpStatus.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    @PostMapping(value = "/process-file-multipart", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpStatus> saveCsvVaccinationFile(@RequestParam(required = true) MultipartFile file) {
        vaccinationCSVData.uploadDataFromFile(file);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
