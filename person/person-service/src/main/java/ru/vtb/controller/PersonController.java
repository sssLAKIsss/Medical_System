package ru.vtb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import ru.vtb.dto.createInput.PersonCreateInputDto;
import ru.vtb.dto.getOrUpdate.PersonDto;
import ru.vtb.service.IPersonService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;


@Tag(
        name = "persons-controller",
        description = "Позволяет получить, создавать и обновлять пользователя/список пользователей"
)
@RestController
@RequestMapping("/api/v1/person")
@RequiredArgsConstructor
@Validated
@Slf4j
public class PersonController {
    private final IPersonService personService;

    @Operation(
            summary = "Получить пользователя по его id в базе данных",
            description = "Позволяет получить пользователя по его уникальному идентификатору"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = PersonDto.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> findPersonById(@PathVariable Long id,
                                                    @RequestParam(required = false) Boolean visibility
    ) {
        return ResponseEntity.ok(personService.findById(id, visibility));
    }

    @Operation(
            summary = "Получить пользователя по номеру его паспорта в базе данных",
            description = "Позволяет получить пользователя по номеру его паспорта"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = PersonDto.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    @GetMapping("/passportNumber/{passportNumber}")
    public ResponseEntity<PersonDto> findPersonByPassportNumber(@PathVariable String passportNumber,
                                                                @RequestParam(required = false) Boolean visibility
    ) {
        return ResponseEntity.ok(personService.findByPassportNumber(passportNumber, visibility));
    }

    @Operation(
            summary = "Получить список пользователей",
            description = "Позволяет получить список пользователей с возможностью сортировки," +
                    "по умолчанию сортировка по ФИО"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PersonDto.class)))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    @GetMapping
    public ResponseEntity<List<PersonDto>> findAllPersons(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "100") Integer pageSize,
            @RequestParam(required = false, defaultValue = "lastName") String sortField,

            @RequestParam(required = false) Boolean visibility,

            @Parameter(description = "Имя региона, по которому будет сделана фильтрация")
            @RequestParam(required = false) String filterParameter
    ) {
        return ResponseEntity.ok(personService.findAllPersons(
                visibility,
                filterParameter,
                PageRequest.of(pageNumber, pageSize, Sort.by(sortField))));
    }

    @Operation(
            summary = "Обновить данные пользователя",
            description = "Позволяет обновить данные пользователя на основе PersonDto"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping
    public ResponseEntity<Long> updatePerson(@Valid @RequestBody PersonDto personDto) {
        return ResponseEntity.ok(personService.update(personDto));
    }

    @Operation(
            summary = "Обновить данные списка пользователей",
            description = "Позволяет обновить данные списка пользователей на основе PersonDto"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Long.class)))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping("/updateAll")
    public ResponseEntity<List<Long>> updateAllPersons(@Valid @RequestBody List<PersonDto> personDtos) {
        return ResponseEntity.ok(personService.updateAll(personDtos));
    }

    @Operation(
            summary = "Добавить в базу данные списка пользователей",
            description = "Позволяет добавить в базу данные списка пользователей на основе PersonCreateInputDto"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Long.class)))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping("/saveAll")
    public ResponseEntity<List<Long>> saveAllPersons(@Valid @RequestBody List<PersonCreateInputDto> personCreateInputDtos) {
        return ResponseEntity.ok(personService.saveAll(personCreateInputDtos));
    }


    @Operation(
            summary = "Создать пользователя",
            description = "Позволяет создать пользователя на основе PersonCreateInputDto"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "500",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping
    public ResponseEntity<Long> savePerson(@Valid @RequestBody PersonCreateInputDto personCreateInputDto) {
        return ResponseEntity.ok(personService.save(personCreateInputDto));
    }

    @Operation(
            summary = "Проверить, есть ли в базе пользователь с определенным паспортом и ФИО",
            description = "Позволяет проверить базу на наличие опредленного пользователя"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "500",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/checkByValidPersonData/{personFullName}/{passportNumber}")
    public ResponseEntity<Boolean> checkByValidPersonData(@Parameter(description = "ФИО через пробел") @PathVariable String personFullName,
                                                          @PathVariable String passportNumber,
                                                          @RequestParam(required = false) Boolean visibility) {
        return ResponseEntity.ok(personService.isValidPassportForPerson(personFullName, passportNumber, visibility));
    }

    @Operation(
            summary = "Установить видимость всем пользователям в базе",
            description = "Позволяет установить всем пользователям и их документам, контактам и адресам" +
                    "(если адрес только за определенным пользователем)"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "500",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping("/visibility")
    public ResponseEntity<HttpStatus> setVisibilityToPersons(@RequestParam Boolean visibility,
                                                             @RequestParam Set<Long> personsId) {
        personService.setPersonsVisibility(visibility, personsId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(
            summary = "Удалить список пользователей",
            description = "Позволяет удалить пользователей из базы по их id"
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
    @DeleteMapping("/personsId")
    public ResponseEntity<HttpStatus> deleteAllById(@RequestParam List<Long> personsId) {
        personService.deletePersonsById(personsId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(
            summary = "Удалить всех пользователей",
            description = "Удаляет всех пользотвалетей из базы"
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
    public ResponseEntity<HttpStatus> deleteAllById() {
        personService.deleteAllPersons();
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
