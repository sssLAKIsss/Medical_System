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
import ru.vtb.dto.createInput.ContactCreateInputDto;
import ru.vtb.dto.getOrUpdate.ContactDto;
import ru.vtb.service.IContactService;

import javax.validation.Valid;
import java.util.List;

@Tag(
        name = "contact-controller",
        description = "Позволяет получить, создавать и обновлять доукмент/список документов"
)
@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
@Validated
public class ContactController {
    private final IContactService contactService;

    @Operation(
            summary = "Получить контакт по его id в базе данных",
            description = "Позволяет получить контакт по его уникальному идентификатору"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = ContactDto.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<ContactDto> findContactById(@PathVariable Long id,
                                                        @RequestParam(required = false) Boolean visibility) {
        return ResponseEntity.ok(contactService.findById(id, visibility));
    }

    @Operation(
            summary = "Получить список контактов",
            description = "Позволяет получить список контактов"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ContactDto.class)))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    @GetMapping()
    public ResponseEntity<List<ContactDto>> findAllContacts(@RequestParam(required = false) Boolean visibility) {
        return ResponseEntity.ok(contactService.findAllContacts(visibility));
    }

    @Operation(
            summary = "Получить список контактов для определенных id пользователей",
            description = "Позволяет получить список контактов по списку уникальных интентификаторов пользователей"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ContactDto.class)))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    @GetMapping("/personsId")
    public ResponseEntity<List<ContactDto>> findContactsByPersonsId(
            @RequestParam(required = true) List<Long> personsId,
            @RequestParam(required = false) Boolean visibility) {
        return ResponseEntity.ok(contactService.findAllContactsByPersonsId(personsId, visibility));
    }

    @Operation(
            summary = "Добавить список контактов в базу",
            description = "Позволяет сохранить список контактов"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ContactDto.class)))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "500",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping
    public ResponseEntity<List<ContactDto>> createContacts(@Valid @RequestBody List<ContactCreateInputDto> contact) {
        return ResponseEntity.ok(contactService.createListOfContacts(contact));
    }

    @Operation(
            summary = "Обновить список контактов",
            description = "Позволяет обновить входной список контактов"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ContactDto.class)))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "500",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping
    public ResponseEntity<List<ContactDto>> updateContacts(@Valid @RequestBody List<ContactDto> contacts) {
        return ResponseEntity.ok(contactService.updateListOfContacts(contacts));
    }

    @Operation(
            summary = "Выставить уровень видимости контактам",
            description = "Позволяет установить нужный уровень видимости контактов по их id"
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
    public ResponseEntity<HttpStatus> setVisibilityByContacts(
            @RequestParam List<Long> contactsId,
            @RequestParam(required = true) Boolean visibility) {
        contactService.setContactsVisibility(contactsId, visibility);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(
            summary = "Удалить список контактов",
            description = "Позволяет удалить контакты из базы по их id"
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
    @DeleteMapping("/contactsId")
    public ResponseEntity<HttpStatus> deleteContactsById(@RequestParam List<Long> contactsId) {
        contactService.deleteContactsFromDB(contactsId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(
            summary = "Удалить все контакты",
            description = "Позволяет удалить все контакты из базы"
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
    public ResponseEntity<HttpStatus> deleteAllContacts() {
        contactService.deleteAllContacts();
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
