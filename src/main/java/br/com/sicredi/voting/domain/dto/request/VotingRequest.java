package br.com.sicredi.voting.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VotingRequest {
    @NotBlank(message = "The session ID is required")
    private String sessionId;
    @NotBlank(message = "The affiliated ID is required")
    private String affiliatedId;
    private boolean vote;
}
