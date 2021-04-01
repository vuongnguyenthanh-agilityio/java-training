package com.agility.currencyexchangeservice.exception;

import com.agility.currencyexchangeservice.util.ExceptionTypeEnum;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
public class CurrencyExchangeException {

  public static RuntimeException throwException(ExceptionTypeEnum exceptionType) {
    return throwException(exceptionType, "", null);
  }

  public static RuntimeException throwException(ExceptionTypeEnum exceptionType, String message) {
    return throwException(exceptionType, message, null);
  }

  public static RuntimeException throwException(ExceptionTypeEnum exceptionType, String message, Throwable error) {
    switch (exceptionType) {
      case BAD_REQUEST:
        return new BadRequestException(message, error);
      case NOT_FOUND:
        return new NotFoundException(message, error);
      case UNAUTHORIZED:
        return new UnauthorizedException(message, error);
      case FORBIDDEN:
        return new ForbiddenException(message, error);
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

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public static class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message, Throwable error) {
      super(message, error);
    }
  }

  @ResponseStatus(HttpStatus.FORBIDDEN)
  public static class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message, Throwable error) {
      super(message, error);
    }
  }
}
