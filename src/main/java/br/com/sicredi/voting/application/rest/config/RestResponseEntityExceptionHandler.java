package br.com.sicredi.voting.application.rest.config;

import br.com.sicredi.voting.application.rest.dtos.ErrorResponse;
import br.com.sicredi.voting.domain.exceptions.AlreadyExistsException;
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
}
