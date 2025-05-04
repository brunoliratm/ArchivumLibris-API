package com.archivumlibris.shared.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import com.archivumlibris.book.exception.BookNotFoundException;
import com.archivumlibris.book.exception.InvalidBookDataException;

@ControllerAdvice
public class GlobalHandlerException {

  @ExceptionHandler(BookNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleBookNotFound(BookNotFoundException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("message", ex.getMessage());
    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(InvalidBookDataException.class)
  public ResponseEntity<Map<String, Object>> handleInvalidBookData(InvalidBookDataException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("message", ex.getMessage());
    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<Map<String, Object>> handleNoResourceFoundException(
      NoResourceFoundException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("message", "The requested route was not found.");
    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("message", "The provided JSON is malformed or contains syntax errors");
    body.put("details", ex.getMessage());
    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, Object> body = new HashMap<>();
    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    body.put("message", "Validation error");
    body.put("errors", errors);

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
    Map<String, Object> body = new HashMap<>();
    body.put("message", "An unexpected error occurred");
    body.put("error", ex.getMessage());
    return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
