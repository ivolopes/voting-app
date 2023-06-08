package br.com.sicredi.voting.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException(String resource) {
        super(resource + " already exists");
    }
}
