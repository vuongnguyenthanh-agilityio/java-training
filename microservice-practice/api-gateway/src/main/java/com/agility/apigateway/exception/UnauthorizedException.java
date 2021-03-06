package com.agility.apigateway.exception;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * Create by Vuong Nguyen
 *
 * This class help update response error when authorization filter
 */
public class UnauthorizedException extends RuntimeException implements ErrorWebExceptionHandler {
  private HttpStatus status = HttpStatus.UNAUTHORIZED;
  private String message = "Unauthorized";

  /**
   * Handle response message when unauthorized
   *
   * @param exchange
   * @param throwable
   * @return
   */
  @Override
  public Mono<Void> handle(ServerWebExchange exchange, Throwable throwable) {
    byte[] bytes = message.getBytes(StandardCharsets.UTF_8);
    DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
    exchange.getResponse().setStatusCode(status);
    return exchange.getResponse().writeWith(Flux.just(buffer));
  }
}
