package br.com.sicredi.voting.application.rest.dtos;

import lombok.Data;

@Data
public class AffiliatedRequest {
    private String name;
    private String cpf;
}
