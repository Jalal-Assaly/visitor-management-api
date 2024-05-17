package org.pacs.visitormanagementapi.exceptionhandlers;

import jakarta.persistence.EntityExistsException;
import jakarta.validation.ConstraintViolationException;
import org.pacs.visitormanagementapi.exceptionhandlers.responsebodies.ConstraintViolationExceptionResponseBody;
import org.pacs.visitormanagementapi.exceptionhandlers.responsebodies.EntityExistsExceptionBody;
import org.pacs.visitormanagementapi.exceptionhandlers.responsebodies.EntityNotFoundExceptionResponseBody;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.pacs.visitormanagementapi.exceptionhandlers.responsebodies.ValidationExceptionBody;
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

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ConstraintViolationExceptionResponseBody> handleConstraintViolationException(ConstraintViolationException exception) {
        HttpStatusCode status = HttpStatus.BAD_REQUEST;
        ConstraintViolationExceptionResponseBody exceptionResponseBody = new ConstraintViolationExceptionResponseBody(status, exception);
        return new ResponseEntity<>(exceptionResponseBody, status);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ValidationExceptionBody> handleValidationException(ValidationException exception) {
        HttpStatusCode status = HttpStatus.BAD_REQUEST;
        ValidationExceptionBody body = new ValidationExceptionBody(status, exception);
        return new ResponseEntity<>(body, status);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<EntityExistsExceptionBody> handleValidationException(EntityExistsException exception) {
        HttpStatusCode status = HttpStatus.BAD_REQUEST;
        EntityExistsExceptionBody body = new EntityExistsExceptionBody(status, exception);
        return new ResponseEntity<>(body, status);
    }
}
