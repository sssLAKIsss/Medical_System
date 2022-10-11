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
import ru.vtb.dto.createInput.AddressCreateInputDto;
import ru.vtb.dto.getOrUpdate.AddressDto;
import ru.vtb.dto.getOrUpdate.DocumentDto;
import ru.vtb.service.IAddressService;

import javax.validation.Valid;
import java.util.List;

@Tag(
        name = "address-controller",
        description = "Позволяет получить, создавать и обновлять адреса пользователя/список адресов"
)
@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
@Validated
public class AddressController {
    private final IAddressService addressService;

    @Operation(
            summary = "Получить адрес по его id в базе данных",
            description = "Позволяет получить адрес по его уникальному идентификатору"
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
    public ResponseEntity<AddressDto> findAddressById(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.findById(id));
    }

    @GetMapping
    @Operation(
            summary = "Получить все адреса",
            description = "Позволяет получить все адреса в зависимости от указанного уровня видимости"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = AddressDto.class))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    public ResponseEntity<List<AddressDto>> findAllAddresses(@RequestParam(required = false) Boolean visibility) {
        return ResponseEntity.ok(addressService.findAllAddresses(visibility));
    }

    @GetMapping("/byPersonsId")
    @Operation(
            summary = "Получить все адреса",
            description = "Позволяет получить все адреса конкретных пользователей в зависимости от указанного уровня видимости"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AddressDto.class)))),
            @ApiResponse(
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ApiError.class))
            )
    })
    public ResponseEntity<List<AddressDto>> findAllAddressesByPersonsId(
            @RequestParam(required = false) Boolean visibility,
            @RequestParam(required = true) List<Long> personsId) {
        return ResponseEntity.ok(addressService.findAllAddressesByPersonsId(visibility, personsId));
    }

    @Operation(
            summary = "Добавить список адресов в базу",
            description = "Позволяет сохранить список адресов в базу"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AddressDto.class)))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "500",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PostMapping
    public ResponseEntity<List<AddressDto>> saveAddresses(@Valid @RequestBody List<AddressCreateInputDto> addresses) {
        return ResponseEntity.ok(addressService.createListOfAddresses(addresses));
    }

    @Operation(
            summary = "Обновить список адресов в базе",
            description = "Позволяет обновить список адресов в базе"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AddressDto.class)))),
            @ApiResponse(
                    responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(
                    responseCode = "500",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PutMapping
    public ResponseEntity<List<AddressDto>> updateAddresses(@Valid @RequestBody List<AddressDto> addresses) {
        return ResponseEntity.ok(addressService.updateListOfAddresses(addresses));
    }

    @Operation(
            summary = "Обновить список адресов в базе",
            description = "Позволяет обновить список адресов в базе"
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
    public ResponseEntity<HttpStatus> setVisibilityToAddresses(@RequestParam(required = true) List<Long> addresses,
                                                               @RequestParam(required = true) Boolean visibility) {
        addressService.setAddressesVisibility(visibility, addresses);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(
            summary = "Удалить список адресов",
            description = "Позволяет удалить список адресов из базы по их id"
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
    @DeleteMapping("/addressesId")
    public ResponseEntity<HttpStatus> deleteDocumentsById(@RequestParam List<Long> addressesId) {
        addressService.deleteAddressesFromDB(addressesId);
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
        addressService.deleteAllAddresses();
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
