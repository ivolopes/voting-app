package br.com.sicredi.voting.domain.dto.response;

import lombok.Data;

@Data
public class CpfValidationResponse {
    private StatusCpfEnum status;
}
