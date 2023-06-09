package br.com.sicredi.voting.application.rest.config;

import br.com.sicredi.voting.application.rest.dtos.ErrorResponse;
import br.com.sicredi.voting.domain.exceptions.AlreadyExistsException;
import br.com.sicredi.voting.domain.exceptions.InvalidEntityException;
import br.com.sicredi.voting.domain.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value
            = { AlreadyExistsException.class})
    protected ResponseEntity<ErrorResponse> handleConflict(
            RuntimeException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(value
            = { NotFoundException.class})
    protected ResponseEntity<ErrorResponse> handleNotFound(
            RuntimeException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(value
            = { InvalidEntityException.class})
    protected ResponseEntity<ErrorResponse> handleBadRequest(
            RuntimeException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
