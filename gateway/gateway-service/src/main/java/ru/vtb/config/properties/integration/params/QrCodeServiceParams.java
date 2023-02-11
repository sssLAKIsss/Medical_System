package ru.vtb.config.properties.integration.params;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Validated
public class QrCodeServiceParams {

    @NotBlank
    private String passportPath;
}
