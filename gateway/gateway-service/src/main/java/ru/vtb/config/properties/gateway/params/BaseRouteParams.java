package ru.vtb.config.properties.gateway.params;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Validated
public class BaseRouteParams {

    @NotBlank
    private String baseRouteId;

    @NotBlank
    private String baseRoutePathPattern;

    @NotBlank
    private String baseRedirectUri;
}
