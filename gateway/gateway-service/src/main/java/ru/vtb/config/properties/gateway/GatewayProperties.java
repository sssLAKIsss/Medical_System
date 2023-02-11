package ru.vtb.config.properties.gateway;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.http.HttpMethod;
import org.springframework.validation.annotation.Validated;
import ru.vtb.config.properties.gateway.params.BaseRouteParams;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@ConfigurationProperties(prefix = "gateway")
@ConstructorBinding
@RequiredArgsConstructor
@Getter
@Validated
public class GatewayProperties {

    @Valid
    @NotNull
    private final BaseRouteParams personServiceParams;

    @Valid
    @NotNull
    private final BaseRouteParams medicalServiceParams;

    @Valid
    @NotNull
    private final BaseRouteParams qrServiceParams;

    @NotEmpty
    private final List<HttpMethod> httpMethods;
}
