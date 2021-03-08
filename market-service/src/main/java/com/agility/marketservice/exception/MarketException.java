package com.agility.marketservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
public class MarketException {

  public static RuntimeException throwException(ExceptionType exceptionType) {
    return throwException(exceptionType, "", null);
  }

  public static RuntimeException throwException(ExceptionType exceptionType, String message) {
    return throwException(exceptionType, message, null);
  }

  public static RuntimeException throwException(ExceptionType exceptionType, String message, Throwable error) {
    switch (exceptionType) {
      case BAD_REQUEST:
        return new BadRequestException(message, error);
      case NOT_FOUND:
        return new NotFoundException(message, error);
      case UNAUTHORIZED:
        return new UnauthorizedException(message, error);
      default:
        return new RuntimeException(message, error);
    }
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public static class BadRequestException extends RuntimeException{
    public BadRequestException(String message, Throwable error) {
      super(message, error);
    }
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  public static class NotFoundException extends RuntimeException{
    public NotFoundException(String message, Throwable error) {
      super(message, error);
    }
  }

  public static class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message, Throwable error) {
      super(message, error);
    }
  }
}
