package com.agility.apigateway.filter;

import com.agility.apigateway.exception.TokenException;
import com.agility.apigateway.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuthorizationFilter implements GatewayFilter {
  private final Logger LOG = LoggerFactory.getLogger(getClass());
  @Value("${jwt.secret}")
  private String jwtSecret;


  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    LOG.info("Request -> {} ", exchange.getRequest().getBody());
    ServerHttpRequest request = exchange.getRequest();

    if (!request.getHeaders().containsKey("Authorization")) {
      throw new UnauthorizedException();
    };

    String token = request.getHeaders().get("Authorization").get(0);
    String userId = null;
    List<String> roles = new ArrayList<>();
    try {
      Claims claims = Jwts.parser()
          .setSigningKey(jwtSecret.getBytes())
          .parseClaimsJws(token.replace("Bearer", ""))
          .getBody();
      userId = claims.getSubject();
      roles = (ArrayList<String>) claims.get("Roles");
      LOG.info("UserId -> {}", userId);
      LOG.info("Roles -> {}", roles);
    } catch (Exception ex) {
      LOG.info("Parse token error -> {}", ex.toString());
    }

    if (userId == null || userId.isEmpty() || roles == null || roles.isEmpty()) {
      throw new UnauthorizedException();
    }

    ServerHttpRequest modifiedRequest = exchange.getRequest()
        .mutate()
        .header("user-id", userId)
        .header("role", roles.get(0))
        .build();
    return chain
        .filter(exchange.mutate().request(modifiedRequest).build())
        .then(Mono.fromRunnable(() -> {
          System.out.println("Post filter");
        }));
  }

  private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus, GatewayFilterChain chain) {
    ServerHttpResponse response = exchange.getResponse();
    byte[] bytes = err.getBytes(StandardCharsets.UTF_8);
    DataBuffer buffer = response.bufferFactory().wrap(bytes);
    response.setStatusCode(httpStatus);
    response.writeWith(Flux.just(buffer));
    response.getHeaders().add("error-message", err);
    return response.setComplete();
  }
}
