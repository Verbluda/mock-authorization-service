package org.example.mock_authorization_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class MockAuthExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleWrongJson(HttpMessageNotReadableException ex) {
        return Map.of(
                "error", "BAD_REQUEST",
                "message", "Wrong JSON"
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidation(MethodArgumentNotValidException ex) {
        List<String> violations = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::format)
                .toList();
        return Map.of(
                "error", "BAD_REQUEST",
                "violations", violations
        );
    }

    private String format(FieldError fieldError) {
        return fieldError.getDefaultMessage();
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> handleUserNotFound(UserNotFoundException ex) {
        return Map.of(
                "error", "INTERNAL_SERVER_ERROR",
                "message", ex.getMessage()
        );
    }
}
