package com.agility.apigateway.config;

import com.agility.apigateway.exception.UnauthorizedException;
import com.agility.apigateway.filter.AuthorizationFilter;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Vuong Nguyen
 *
 * This class help config routes for api gateway and exception
 */
@Configuration
public class SpringCloudConfig {
  private AuthorizationFilter authorizationFilter;

  public SpringCloudConfig(AuthorizationFilter authorizationFilter) {
    this.authorizationFilter = authorizationFilter;
  }

  /**
   * Config routes for api gateway and put this config into application context
   *
   * @param builder
   * @return
   */
  @Bean
  public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
    return builder.routes()
        //  Config route to currency-exchange-service. Add filter authorization
        .route(r -> r
            .path("/v1/api/currency-exchange/**")
            .filters(f -> f.filter(authorizationFilter))
            .uri("lb://currency-exchange-service")
        )
        // Config route to currency-conversion-service
        .route(r -> r.path("/v1/api/currency-conversion/**")
            .uri("lb://currency-conversion-service"))
        // Config route to oauth-service
        .route(r -> r.path("/v1/api/auth/**")
            .uri("lb://oauth-service"))
        .build();
  }

  /**
   * Config exception when handle authorization filter and put this Bean into application context
   *
   * @return
   */
  @Bean
  public ErrorWebExceptionHandler unauthorizedException() {
    return new UnauthorizedException();
  }
}
