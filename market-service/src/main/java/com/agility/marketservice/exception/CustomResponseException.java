package com.agility.marketservice.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomResponseException extends ResponseEntityExceptionHandler {

  /**
   * Handle all exception in system
   * @param ex
   * @param request
   * @return
   */
  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse()
        .setTimestamp(new Date())
        .setMessage(ex.getMessage())
        .setDetails(request.getDescription(false));

    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(MarketException.NotFoundException.class)
  public final ResponseEntity<ExceptionResponse> handleNotFoundException(Exception ex, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse()
        .setTimestamp(new Date())
        .setMessage(ex.getMessage())
        .setDetails(request.getDescription(false));

    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MarketException.BadRequestException.class)
  public final ResponseEntity<ExceptionResponse> handleBadRequestException(Exception ex, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse()
        .setTimestamp(new Date())
        .setMessage(ex.getMessage())
        .setDetails(request.getDescription(false));

    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MarketException.UnauthorizedException.class)
  public final ResponseEntity<ExceptionResponse> handleUnauthorizedException(Exception ex, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse()
        .setTimestamp(new Date())
        .setMessage(ex.getMessage())
        .setDetails(request.getDescription(false));

    return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpHeaders headers,
                                                                HttpStatus status,
                                                                WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse()
        .setTimestamp(new Date())
        .setMessage("Validation Failed")
        .setDetails(ex.getBindingResult().toString());

    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }
}
