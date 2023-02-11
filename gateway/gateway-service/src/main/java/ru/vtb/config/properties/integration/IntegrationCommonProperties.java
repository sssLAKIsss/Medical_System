package ru.vtb.config.properties.integration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@Validated
public class IntegrationCommonProperties {

    @NotBlank
    private String baseUri;
}
