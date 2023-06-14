package br.com.sicredi.voting.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VotingResultResponse {
    private Long yesTotal;
    private Long noTotal;
}
