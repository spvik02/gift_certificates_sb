package ru.clevertec.ecl.controller;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.clevertec.ecl.model.exception.ErrorResponse;

import java.util.NoSuchElementException;

/**
 * Handle exceptions
 */
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        String errorMessage = ex.getName() + " should be of type " + ex.getRequiredType().getName();

        ErrorResponse apiError = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                errorMessage + " (" + ex.getLocalizedMessage() + ")",
                request.getDescription(false));
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {
        String errorMessage = ex.getMessage();

        ErrorResponse apiError = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                errorMessage + " (" + ex.getLocalizedMessage() + ")",
                request.getDescription(false));
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(NoSuchElementException ex, WebRequest request) {
        String errorMessage = ex.getMessage();

        ErrorResponse apiError = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                errorMessage + " (" + ex.getLocalizedMessage() + ")",
                request.getDescription(false));
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(ConstraintViolationException ex, WebRequest request) {
        String errorMessage = ex.getMessage() + ". Constraint name - " + ex.getConstraintName();

        ErrorResponse apiError = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                errorMessage + " (" + ex.getLocalizedMessage() + ")",
                request.getDescription(false));
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
