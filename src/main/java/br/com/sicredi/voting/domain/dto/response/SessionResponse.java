package br.com.sicredi.voting.domain.dto.response;

import lombok.Getter;

@Getter
public class SessionResponse {
    private String id;

    public SessionResponse(String id) {
        this.id = id;
    }
}
