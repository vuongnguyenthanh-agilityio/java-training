package com.agility.apigateway.config;

import com.agility.apigateway.exception.UnauthorizedException;
import com.agility.apigateway.filter.AuthorizationFilter;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringCloudConfig {
  private AuthorizationFilter authorizationFilter;

  public SpringCloudConfig(AuthorizationFilter authorizationFilter) {
    this.authorizationFilter = authorizationFilter;
  }

  @Bean
  public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
    return builder.routes()
        .route(r -> r
            .path("/v1/api/currency-exchange/**")
            .filters(f -> f.filter(authorizationFilter))
            .uri("lb://currency-exchange-service")
        )
        .route(r -> r.path("/v1/api/currency-conversion/**")
            .uri("lb://currency-conversion-service"))
        .route(r -> r.path("/v1/api/auth/**")
            .uri("lb://oauth-service"))
        .build();
  }

  @Bean
  public ErrorWebExceptionHandler unauthorizedException() {
    return new UnauthorizedException();
  }
}
