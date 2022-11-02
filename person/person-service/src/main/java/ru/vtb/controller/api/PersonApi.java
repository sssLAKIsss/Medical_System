package ru.vtb.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import ru.vtb.dto.createInput.PersonCreateInputDto;
import ru.vtb.dto.getOrUpdate.PersonDto;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Tag(
        name = "Person API",
        description = "Позволяет получить, создавать и обновлять пользователя/список пользователей"
)
@Validated
@ApiParams
@ApiV1
public interface PersonApi {

    @Operation(
            summary = "Получить пользователя по его id в базе данных",
            description = "Позволяет получить пользователя по его уникальному идентификатору"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = PersonDto.class)))
    })
    @GetMapping("/persons/{id}")
    ResponseEntity<PersonDto> findPersonById(@PathVariable Long id,
                                             @RequestParam(required = false) Boolean visibility);


    @Operation(
            summary = "Получить пользователя по номеру его паспорта в базе данных",
            description = "Позволяет получить пользователя по номеру его паспорта"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = PersonDto.class)))
    })
    @GetMapping("/persons/passportNumber/{passportNumber}")
    ResponseEntity<PersonDto> findPersonByPassportNumber(
            @PathVariable @Pattern(regexp = "^[0-9]{10}$", message = "Номер документа строго 10 цифр") String passportNumber,
            @RequestParam(required = false) Boolean visibility);


    @Operation(
            summary = "Получить список пользователей",
            description = "Позволяет получить список пользователей с возможностью сортировки," +
                    "по умолчанию сортировка по ФИО"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PersonDto.class))))
    })
    @GetMapping("/persons")
    ResponseEntity<List<PersonDto>> findAllPersons(
            @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "100") Integer pageSize,
            @RequestParam(required = false, defaultValue = "lastName") String sortField,

            @RequestParam(required = false) Boolean visibility,

            @Parameter(description = "Имя региона, по которому будет сделана фильтрация")
            @RequestParam(required = false) String filterParameter);


    @Operation(
            summary = "Обновить данные пользователя",
            description = "Позволяет обновить данные пользователя на основе PersonDto",
            deprecated = true
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Long.class)))
    })
    @PutMapping("/persons")
    ResponseEntity<Long> updatePerson(@Valid @RequestBody PersonDto personDto);


    @Operation(
            summary = "Обновить данные списка пользователей",
            description = "Позволяет обновить данные списка пользователей на основе PersonDto"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Long.class))))
    })
    @PutMapping("/persons/updateAll")
    ResponseEntity<List<Long>> updateAllPersons(@Valid @RequestBody List<PersonDto> personDtos);


    @Operation(
            summary = "Добавить в базу данные списка пользователей",
            description = "Позволяет добавить в базу данные списка пользователей на основе PersonCreateInputDto"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Long.class))))
    })
    @PostMapping("/persons/saveAll")
    ResponseEntity<List<Long>> saveAllPersons(@Valid @RequestBody List<PersonCreateInputDto> personCreateInputDtos);


    @Operation(
            summary = "Создать пользователя",
            description = "Позволяет создать пользователя на основе PersonCreateInputDto",
            deprecated = true
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Long.class)))
    })
    @PostMapping("/persons")
    ResponseEntity<Long> savePerson(@Valid @RequestBody PersonCreateInputDto personCreateInputDto);


    @Operation(
            summary = "Проверить, есть ли в базе пользователь с определенным паспортом и ФИО",
            description = "Позволяет проверить базу на наличие определенного пользователя"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Boolean.class)))
    })
    @GetMapping("/persons/checkByValidPersonData")
    ResponseEntity<Boolean> checkByValidPersonData(
            @Parameter(description = "Фамилия") @RequestParam @Size(min = 1, max = 30) String firstName,
            @Parameter(description = "Имя") @RequestParam @Size(min = 1, max = 30) String lastName,
            @Parameter(description = "Отчество") @RequestParam(required = false) @Size(min = 1, max = 30) String patronymic,
            @Parameter(description = "Серия и номер документа 10 цифр без пробела") @RequestParam @Pattern(regexp = "^[0-9]{10}$", message = "Серия и номер документа - цифры  без пробела") String passportNumber,
            @RequestParam(required = false) Boolean visibility);


    @Operation(
            summary = "Установить видимость всем пользователям в базе",
            description = "Позволяет установить всем пользователям и их документам, контактам и адресам" +
                    "(если адрес только за определенным пользователем)"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = HttpStatus.class))))
    })
    @PutMapping("/persons/visibility")
    ResponseEntity<HttpStatus> setVisibilityToPersons(@RequestParam Boolean visibility,
                                                      @RequestParam Set<Long> personsId);


    @Operation(
            summary = "Удалить список пользователей",
            description = "Позволяет удалить пользователей из базы по их id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = HttpStatus.class))))
    })
    @DeleteMapping("/persons/personsId")
    ResponseEntity<HttpStatus> deleteAllById(@RequestParam List<Long> personsId);


    @Operation(
            summary = "Удалить всех пользователей",
            description = "Удаляет всех пользотвалетей из базы"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = HttpStatus.class))))
    })
    @DeleteMapping("/persons")
    ResponseEntity<HttpStatus> deleteAllById();
}
