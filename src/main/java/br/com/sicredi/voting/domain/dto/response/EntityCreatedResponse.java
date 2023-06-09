package br.com.sicredi.voting.domain.dto.response;

import lombok.Getter;

@Getter
public class EntityCreatedResponse {
    private String id;

    public EntityCreatedResponse(String id) {
        this.id = id;
    }
}
