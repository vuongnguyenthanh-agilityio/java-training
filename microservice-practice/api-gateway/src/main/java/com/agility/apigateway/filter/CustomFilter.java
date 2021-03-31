package com.agility.apigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class CustomFilter extends AbstractGatewayFilterFactory<CustomFilter.Config> {
  private final Logger LOG = LoggerFactory.getLogger(getClass());
  public CustomFilter() {
    super(Config.class);
  }

  private boolean isAuthorizationValid(String authorizationHeader) {
    boolean isValid = true;

    // Logic for checking the value

    return isValid;
  }

  private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus)  {
    exchange.getResponse().beforeCommit(() -> {
      ServerHttpResponse response = exchange.getResponse();
      byte[] bytes = err.getBytes(StandardCharsets.UTF_8);
      DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
      response.setStatusCode(httpStatus);
      return response.writeWith(Flux.just(buffer));
    });


   return exchange.getResponse().setComplete();
  }

  @Override
  public GatewayFilter apply(Config config) {
    return ((exchange, chain) -> {
      LOG.info("Request -> {} ", exchange.getRequest());
      ServerHttpRequest request = exchange.getRequest();

      if (!request.getHeaders().containsKey("Authorization")) {
        return this.onError(exchange, "No Authorization header", HttpStatus.UNAUTHORIZED);
      };

      String authorizationHeader = request.getHeaders().get("Authorization").get(0);

      if (!this.isAuthorizationValid(authorizationHeader)) {
        return this.onError(exchange, "Invalid Authorization header", HttpStatus.UNAUTHORIZED);
      }

      ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().
          header("secret", "Test modified").
          build();
      return chain
          .filter(exchange.mutate().request(modifiedRequest).build())
          .then(Mono.fromRunnable(() -> {
        System.out.println("First post filter");
      }));
//      return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//        System.out.println("First post filter");
//      }));
    });
  }

  @Override
  public Config newConfig() {
    return new Config("CustomFilter");
  }

  public static class Config {
    public Config(String name){
      this.name = name;
    }
    private String name;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }
}
