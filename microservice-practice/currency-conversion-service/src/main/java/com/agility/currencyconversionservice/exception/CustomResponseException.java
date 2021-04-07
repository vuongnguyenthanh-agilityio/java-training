package com.agility.currencyconversionservice.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
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
    String message = (ex.getMessage() != null) ? ex.getMessage() : "Internal server error.";

    ExceptionResponse exceptionResponse = new ExceptionResponse()
        .setTimestamp(new Date())
        .setMessage(message)
        .setDetails(request.getDescription(false));

    return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(CurrencyConversionException.NotFoundException.class)
  public final ResponseEntity<ExceptionResponse> handleNotFoundException(Exception ex, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse()
        .setTimestamp(new Date())
        .setMessage(ex.getMessage())
        .setDetails(request.getDescription(false));

    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(FeignException.NotFound.class)
  public final ResponseEntity<ExceptionResponse> handleNotFoundFeignException(
      FeignException ex, HttpServletResponse response) {
    ObjectMapper mapper = new ObjectMapper();
    ExceptionResponse exceptionResponse = new ExceptionResponse()
      .setTimestamp(new Date());
    JsonNode root;
    try {
      root = mapper.readTree(ex.contentUTF8());
    } catch (JsonProcessingException e) {
      exceptionResponse.setMessage("Internal server error.");
      return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    String message = root.path("message").asText();
    exceptionResponse.setMessage(message);

    return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(FeignException.BadRequest.class)
  public final ResponseEntity<ExceptionResponse> handleBadRequestFeignException(
      FeignException ex, HttpServletResponse response) {
    ObjectMapper mapper = new ObjectMapper();
    ExceptionResponse exceptionResponse = new ExceptionResponse()
        .setTimestamp(new Date());
    JsonNode root;
    try {
      root = mapper.readTree(ex.contentUTF8());
    } catch (JsonProcessingException e) {
      exceptionResponse.setMessage("Internal server error.");
      return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    String message = root.path("message").asText();
    exceptionResponse.setMessage(message);

    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CurrencyConversionException.BadRequestException.class)
  public final ResponseEntity<ExceptionResponse> handleBadRequestException(Exception ex, WebRequest request) {
    ExceptionResponse exceptionResponse = new ExceptionResponse()
        .setTimestamp(new Date())
        .setMessage(ex.getMessage())
        .setDetails(request.getDescription(false));

    return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CurrencyConversionException.UnauthorizedException.class)
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
