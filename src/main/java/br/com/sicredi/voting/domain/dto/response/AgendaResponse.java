package br.com.sicredi.voting.domain.dto.response;

import lombok.Getter;

@Getter
public class AgendaResponse {
    private String id;

    public AgendaResponse(String id) {
        this.id = id;
    }
}
