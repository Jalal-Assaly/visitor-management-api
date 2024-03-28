package org.example.visitormanagementapi.exceptionhandlers;

import com.example.companydatagateway.exceptionhandlers.responsebodies.EntityNotFoundExceptionResponseBody;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.example.visitormanagementapi.exceptionhandlers.responsebodies.ValidationExceptionBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<EntityNotFoundExceptionResponseBody> handleEntityNotFoundException(EntityNotFoundException exception) {
        HttpStatusCode status = HttpStatus.BAD_REQUEST;
        EntityNotFoundExceptionResponseBody entityNotFoundExceptionResponseBody = new EntityNotFoundExceptionResponseBody(status, exception);
        return new ResponseEntity<>(entityNotFoundExceptionResponseBody, status);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ValidationExceptionBody> handleValidationException(ValidationException exception) {
        HttpStatusCode status = HttpStatus.BAD_REQUEST;
        ValidationExceptionBody body = new ValidationExceptionBody(status, exception);
        return new ResponseEntity<>(body, status);
    }
}
