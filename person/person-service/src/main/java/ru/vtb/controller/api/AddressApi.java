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
import ru.vtb.dto.createInput.AddressCreateInputDto;
import ru.vtb.dto.getOrUpdate.AddressDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Tag(
        name = "Address API",
        description = "Позволяет получить, создавать и обновлять адреса пользователя/список адресов"
)
@Validated
@ApiParams
@ApiV1
public interface AddressApi {

    @Operation(
            summary = "Получить адрес по его id в базе данных",
            description = "Позволяет получить адрес по его уникальному идентификатору"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = AddressDto.class)))
    })
    @GetMapping("/addresses/{id}")
    ResponseEntity<AddressDto> findAddressById(@PathVariable Long id);

    @GetMapping("/addresses")
    @Operation(
            summary = "Получить все адреса",
            description = "Позволяет получить все адреса в зависимости от указанного уровня видимости"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = AddressDto.class)))
    })
    ResponseEntity<List<AddressDto>> findAllAddresses(@RequestParam(required = false) Boolean visibility);

    @GetMapping("/addresses/byPersonsId")
    @Operation(
            summary = "Получить все адреса",
            description = "Позволяет получить все адреса конкретных пользователей в зависимости от указанного уровня видимости"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200")
    })
    ResponseEntity<Map<Long, List<AddressDto>>> findAllAddressesByPersonsId(@RequestParam(required = false) Boolean visibility,
                                                                            @RequestParam(required = true) List<Long> personsId);

    @Operation(
            summary = "Добавить список адресов в базу",
            description = "Позволяет сохранить список адресов в базу"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Long.class))))
    })
    @PostMapping("/addresses")
    ResponseEntity<List<Long>> saveAddresses(@Valid @RequestBody List<AddressCreateInputDto> addresses);

    @Operation(
            summary = "Обновить список адресов в базе",
            description = "Позволяет обновить список адресов в базе"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Long.class))))
    })
    @PutMapping("/addresses")
    ResponseEntity<List<Long>> updateAddresses(@Valid @RequestBody List<AddressDto> addresses);

    @Operation(
            summary = "Обновить список адресов в базе",
            description = "Позволяет обновить список адресов в базе"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = HttpStatus.class))))
    })
    @PutMapping("/addresses/visibility")
    ResponseEntity<HttpStatus> setVisibilityToAddresses(@RequestParam(required = true) List<Long> addresses,
                                                               @RequestParam(required = true) Boolean visibility);

    @Operation(
            summary = "Удалить список адресов",
            description = "Позволяет удалить список адресов из базы по их id"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = HttpStatus.class))))
    })
    @DeleteMapping("/addresses/addressesId")
    ResponseEntity<HttpStatus> deleteDocumentsById(@RequestParam List<Long> addressesId);

    @Operation(
            summary = "Удалить все адреса",
            description = "Позволяет удалить все документы из базы"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = HttpStatus.class))))
    })
    @DeleteMapping("/addresses")
    ResponseEntity<HttpStatus> deleteAllDocuments();
}
