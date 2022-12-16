package ru.vtb.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.vtb.controller.api.custom.ApiParams;
import ru.vtb.controller.api.custom.ApiV1;

@Tag(
        name = "vaccination-data-controller",
        description = "Позволяет загрузить данные с csv-файла"
)
@Validated
@ApiParams
@ApiV1
public interface VaccinationApi {

    @Operation(
            summary = "Загрузить данные с файла",
            description = "Позволяет загрузить данные с csv-файла, файл имеет структуру: " +
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
                    content = @Content(schema = @Schema(implementation = HttpStatus.class)))
    })
    @PostMapping(value = "/medical/process-file-multipart", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<HttpStatus> saveCsvVaccinationFile(@RequestParam MultipartFile file);
}
