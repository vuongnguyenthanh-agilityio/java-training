package com.agility.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class SpringCloudConfig {
  @Bean
  public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
    return builder.routes()
        .route(r -> r.path("/v1/api/currency-exchange/**")
          .uri("lb://currency-exchange-service"))
        .route(r -> r.path("/v1/api/currency-conversion/**")
          .uri("lb://currency-conversion-service"))
        .build();
  }
}
