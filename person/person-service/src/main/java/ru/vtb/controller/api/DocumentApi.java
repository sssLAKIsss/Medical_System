package ru.vtb.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.vtb.controller.api.custom.ApiParams;
import ru.vtb.controller.api.custom.ApiV1;
import ru.vtb.dto.createInput.DocumentCreateInputDto;
import ru.vtb.dto.getOrUpdate.DocumentDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Tag(
        name = "document-controller",
        description = "Позволяет получить, создавать и обновлять доукмент/список документов"
)
@Validated
@ApiParams
@ApiV1
public interface DocumentApi {

    @Operation(
            summary = "Получить документ по его id в базе данных",
            description = "Позволяет получить документ по его уникальному идентификатору"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = DocumentDto.class)))
    })
    @GetMapping("/documents/{id}")
    ResponseEntity<DocumentDto> findDocumentById(@PathVariable Long id,
                                                        @RequestParam(required = false) Boolean visibility);


    @Operation(
            summary = "Получить список документов",
            description = "Позволяет получить список документов"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DocumentDto.class))))
    })
    @GetMapping("/documents")
    ResponseEntity<List<DocumentDto>> findAllDocuments(@RequestParam(required = false) Boolean visibility);


    @Operation(
            summary = "Получить список документов для определенных id пользователей",
            description = "Позволяет получить список документов по списку уникальных интентификаторов пользователей"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200")
    })
    @GetMapping("/documents/personsId")
    ResponseEntity<Map<Long, List<DocumentDto>>> findDocumentsByPersonsId(
            @RequestParam(required = true) List<Long> personsId,
            @RequestParam(required = false) Boolean visibility);


    @Operation(
            summary = "Добавить список документов в базу",
            description = "Позволяет сохранить список документов"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DocumentDto.class))))
    })
    @PostMapping("/documents")
    ResponseEntity<List<DocumentDto>> createDocuments(@Valid @RequestBody List<DocumentCreateInputDto> documents);


    @Operation(
            summary = "Обновить список документов",
            description = "Позволяет обнвоить входной список документов"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DocumentDto.class))))
    })
    @PutMapping("/documents")
    ResponseEntity<List<DocumentDto>> updateDocuments(@Valid @RequestBody List<DocumentDto> documents);


    @Operation(
            summary = "Выставить уровень видимости документам",
            description = "Позволяет установить нужный уровень видимости документов по их id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = HttpStatus.class))))
    })
    @PutMapping("/documents/visibility")
    ResponseEntity<HttpStatus> setVisibilityByDocuments(
            @RequestParam List<Long> documentsId,
            @RequestParam(required = true) Boolean visibility);


    @Operation(
            summary = "Удалить список документов",
            description = "Позволяет удалить документы из базы по их id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = HttpStatus.class))))
    })
    @DeleteMapping("/documents/documentsId")
    ResponseEntity<HttpStatus> deleteDocumentsById(@RequestParam List<Long> documentsId);


    @Operation(
            summary = "Удалить все документов",
            description = "Позволяет удалить все документы из базы"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = HttpStatus.class))))
    })
    @DeleteMapping("/documents")
    ResponseEntity<HttpStatus> deleteAllDocuments();
}
