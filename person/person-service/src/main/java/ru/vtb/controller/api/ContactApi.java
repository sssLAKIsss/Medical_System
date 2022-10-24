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
import ru.vtb.dto.createInput.ContactCreateInputDto;
import ru.vtb.dto.getOrUpdate.ContactDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Tag(
        name = "Contact API",
        description = "Позволяет получить, создавать и обновлять доукмент/список документов"
)
@Validated
@ApiParams
@ApiV1
public interface ContactApi {

    @Operation(
            summary = "Получить контакт по его id в базе данных",
            description = "Позволяет получить контакт по его уникальному идентификатору"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = ContactDto.class)))
    })
    @GetMapping("/contacts/{id}")
    ResponseEntity<ContactDto> findContactById(@PathVariable Long id,
                                                      @RequestParam(required = false) Boolean visibility);


    @Operation(
            summary = "Получить список контактов",
            description = "Позволяет получить список контактов"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ContactDto.class))))
    })
    @GetMapping("/contacts")
    ResponseEntity<List<ContactDto>> findAllContacts(@RequestParam(required = false) Boolean visibility);


    @Operation(
            summary = "Получить список контактов для определенных id пользователей",
            description = "Позволяет получить список контактов по списку уникальных интентификаторов пользователей"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200")
    })
    @GetMapping("/contacts/personsId")
    ResponseEntity<Map<Long, List<ContactDto>>> findContactsByPersonsId(
            @RequestParam(required = true) List<Long> personsId,
            @RequestParam(required = false) Boolean visibility);


    @Operation(
            summary = "Добавить список контактов в базу",
            description = "Позволяет сохранить список контактов"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ContactDto.class))))
    })
    @PostMapping("/contacts")
    ResponseEntity<List<ContactDto>> createContacts(@Valid @RequestBody List<ContactCreateInputDto> contact);


    @Operation(
            summary = "Обновить список контактов",
            description = "Позволяет обновить входной список контактов"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ContactDto.class))))
    })
    @PutMapping("/contacts")
    ResponseEntity<List<ContactDto>> updateContacts(@Valid @RequestBody List<ContactDto> contacts);


    @Operation(
            summary = "Выставить уровень видимости контактам",
            description = "Позволяет установить нужный уровень видимости контактов по их id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = HttpStatus.class))))
    })
    @PutMapping("/contacts/visibility")
    ResponseEntity<HttpStatus> setVisibilityByContacts(
            @RequestParam List<Long> contactsId,
            @RequestParam(required = true) Boolean visibility);


    @Operation(
            summary = "Удалить список контактов",
            description = "Позволяет удалить контакты из базы по их id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = HttpStatus.class))))
    })
    @DeleteMapping("/contacts/contactsId")
    ResponseEntity<HttpStatus> deleteContactsById(@RequestParam List<Long> contactsId);


    @Operation(
            summary = "Удалить все контакты",
            description = "Позволяет удалить все контакты из базы"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = HttpStatus.class))))
    })
    @DeleteMapping("/contacts")
    ResponseEntity<HttpStatus> deleteAllContacts();
}
