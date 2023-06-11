package br.com.sicredi.voting.domain.dto.request;

import lombok.Data;

@Data
public class ErrorResponse {

    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
