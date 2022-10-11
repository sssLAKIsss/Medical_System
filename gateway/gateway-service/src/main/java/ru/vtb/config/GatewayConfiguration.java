package ru.vtb.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class GatewayConfiguration {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("person_route",
                        route -> route.path("/api/v1/person/**")
                                .and()
                                .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)
                                .uri("lb://person-service"))
                .route("qr_code_route",
                        route -> route.path("/api/v1/qr/**")
                                .and()
                                .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)
                                .uri("lb://qr-code-storage-service"))
                .route("medical_route",
                        route -> route.path("/api/v1/medical/**")
                                .and()
                                .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE)
                                .uri("lb://medical-service"))
                .build();
    }
}
