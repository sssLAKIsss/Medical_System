package ru.vtb.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import ru.vtb.config.properties.gateway.GatewayProperties;

@Configuration
@RequiredArgsConstructor
public class GatewayConfiguration {
    private final GatewayProperties properties;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(properties.getPersonServiceParams().getBaseRouteId(),
                        route -> route.path(properties.getPersonServiceParams().getBaseRoutePathPattern())
                                .and()
                                .method(properties.getHttpMethods().toArray(HttpMethod[]::new))
                                .uri(properties.getPersonServiceParams().getBaseRedirectUri()))
                .route(properties.getQrServiceParams().getBaseRouteId(),
                        route -> route.path(properties.getQrServiceParams().getBaseRoutePathPattern())
                                .and()
                                .method(properties.getHttpMethods().toArray(HttpMethod[]::new))
                                .uri(properties.getQrServiceParams().getBaseRedirectUri()))
                .route(properties.getMedicalServiceParams().getBaseRouteId(),
                        route -> route.path(properties.getMedicalServiceParams().getBaseRoutePathPattern())
                                .and()
                                .method(properties.getHttpMethods().toArray(HttpMethod[]::new))
                                .uri(properties.getMedicalServiceParams().getBaseRedirectUri()))
                .build();
    }
}
