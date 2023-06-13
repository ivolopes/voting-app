package br.com.sicredi.voting.application.rest.config;

import br.com.sicredi.voting.domain.dto.request.ErrorResponse;
import br.com.sicredi.voting.domain.exceptions.AlreadyExistsException;
import br.com.sicredi.voting.domain.exceptions.InvalidEntityException;
import br.com.sicredi.voting.domain.exceptions.NotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

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

    @Override
    protected Mono<ResponseEntity<Object>> handleWebExchangeBindException(WebExchangeBindException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .reduce(" ", String::concat);

        ErrorResponse response = new ErrorResponse(message);

        return Mono.just(new ResponseEntity<>(response, HttpStatus.BAD_REQUEST));
    }

}
