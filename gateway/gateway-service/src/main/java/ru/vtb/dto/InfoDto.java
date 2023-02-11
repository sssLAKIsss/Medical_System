package ru.vtb.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.vtb.medicalservice.client.model.PatientDto;
import ru.vtb.personservice.client.model.PersonDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@Getter
@Setter
@Builder
@ToString
@Schema(description = "Данные пользователя, также его мед.данные и qr-code в base64 формате")
public class InfoDto {

    @NotNull
    @Schema(description = "Данные из справочника клиентов", required = true)
    private PersonDto personDto;

    @NotNull
    @Schema(description = "Данные из мед справочника", required = true)
    private PatientDto patientDto;

    @NotBlank
    @Schema(description = "Данные из справочника qrt-code", required = true)
    private String bufferedImage;
}
