package ru.vtb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.vtb.dto.ApiError;
import ru.vtb.dto.createInput.DocumentCreateInputDto;
import ru.vtb.dto.getOrUpdate.DocumentDto;
import ru.vtb.service.IDocumentService;

import javax.validation.Valid;
import java.util.List;

@Tag(
        name = "document-controller",
        description = "Позволяет получить, создавать и обновлять доукмент/список документов"
)
@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
@Validated
public class DocumentController {
    private final IDocumentService documentService;

    @Operation(
            summary = "Получить документ по его id в базе данных",
            description = "Позволяет получить документ по его уникальному идентификатору"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = DocumentDto.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<DocumentDto> findDocumentById(@PathVariable Long id,
                                                        @RequestParam(required = false) Boolean visibility) {
        return ResponseEntity.ok(documentService.findById(id, visibility));
    }

    @Operation(
            summary = "Получить список документов",
            description = "Позволяет получить список документов"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DocumentDto.class)))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    @GetMapping()
    public ResponseEntity<List<DocumentDto>> findAllDocuments(@RequestParam(required = false) Boolean visibility) {
        return ResponseEntity.ok(documentService.findAllDocuments(visibility));
    }

    @Operation(
            summary = "Получить список документов для определенных id пользователей",
            description = "Позволяет получить список документов по списку уникальных интентификаторов пользователей"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DocumentDto.class)))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    @GetMapping("/personsId")
    public ResponseEntity<List<DocumentDto>> findDocumentsByPersonsId(
            @RequestParam(required = true) List<Long> personsId,
            @RequestParam(required = false) Boolean visibility) {
        return ResponseEntity.ok(documentService.findAllDocumentsByPersonsId(personsId, visibility));
    }

    @Operation(
            summary = "Добавить список документов в базу",
            description = "Позволяет сохранить список документов"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DocumentDto.class)))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "500",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping
    public ResponseEntity<List<DocumentDto>> createDocuments(@Valid @RequestBody List<DocumentCreateInputDto> documents) {
        return ResponseEntity.ok(documentService.createListOfDocuments(documents));
    }

    @Operation(
            summary = "Обновить список документов",
            description = "Позволяет обнвоить входной список документов"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DocumentDto.class)))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "500",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping
    public ResponseEntity<List<DocumentDto>> updateDocuments(@Valid @RequestBody List<DocumentDto> documents) {
        return ResponseEntity.ok(documentService.updateListOfDocuments(documents));
    }

    @Operation(
            summary = "Выставить уровень видимости документам",
            description = "Позволяет установить нужный уровень видимости документов по их id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = HttpStatus.class)))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "500",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping("/visibility")
    public ResponseEntity<HttpStatus> setVisibilityByDocuments(
            @RequestParam List<Long> documentsId,
            @RequestParam(required = true) Boolean visibility) {
        documentService.setDocumentsVisibility(documentsId, visibility);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(
            summary = "Удалить список документов",
            description = "Позволяет удалить документы из базы по их id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200"),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "500",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @DeleteMapping("/documentsId")
    public ResponseEntity<HttpStatus> deleteDocumentsById(@RequestParam List<Long> documentsId) {
        documentService.deleteDocumentsFromDB(documentsId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(
            summary = "Удалить все документов",
            description = "Позволяет удалить все документы из базы"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200"),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "500",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAllDocuments() {
        documentService.deleteAllDocuments();
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
