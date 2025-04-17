package com.openclassrooms.mddapi.errors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles the LoginFailedException and returns a response entity with an appropriate status
   * code and message.
   *
   * @param e the exception instance containing the details of the login failure
   * @return a {@link ResponseEntity} with HTTP status 401 (UNAUTHORIZED) and the exception message in the response body
   */
  @ExceptionHandler(LoginFailedException.class)
  public ResponseEntity<?> handleLoginFailedException(LoginFailedException e) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
  }

  /**
   * Handles the UserNotFoundException and returns a response entity with an appropriate HTTP status
   * code and message.
   *
   * @param e the exception instance containing the details of the user not being found
   * @return a {@link ResponseEntity} with HTTP status 404 (NOT_FOUND) and the exception message in the response body
   */
  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, List<Map<String, String>>>> handleValidationErrors(MethodArgumentNotValidException ex) {
    List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors().stream()
            .map(error -> {
              Map<String, String> err = new HashMap<>();
              err.put("field", error.getField());
              err.put("message", error.getDefaultMessage());
              return err;
            })
            .collect(Collectors.toList());

    Map<String, List<Map<String, String>>> response = new HashMap<>();
    response.put("errorMessage", errors);

    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  /**
   * Handles the EmailAlreadyUsedException and returns a response entity with an appropriate status
   * code and message.
   *
   * @param e the exception instance containing the details of the email conflict
   * @return a {@link ResponseEntity} with HTTP status 409 (CONFLICT) and the exception message in the response body
   */
  @ExceptionHandler(EmailAlreadyUsedException.class)
  public ResponseEntity<Map<String, List<Map<String, String>>>> handleEmailAlreadyUsedException(EmailAlreadyUsedException ex) {
    Map<String, String> error = new HashMap<>();
    error.put("message", ex.getMessage());

    List<Map<String, String>> errors = new ArrayList<>();
    errors.add(error);

    Map<String, List<Map<String, String>>> response = new HashMap<>();
    response.put("errorMessage", errors);

    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
  }

}
