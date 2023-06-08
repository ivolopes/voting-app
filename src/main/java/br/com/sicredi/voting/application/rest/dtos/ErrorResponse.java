package br.com.sicredi.voting.application.rest.dtos;

import lombok.Data;

@Data
public class ErrorResponse {

    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
