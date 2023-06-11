package br.com.sicredi.voting.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class VotingResultResponse {
    private BigDecimal yesPercentage;
    private BigDecimal noPercentage;
}
