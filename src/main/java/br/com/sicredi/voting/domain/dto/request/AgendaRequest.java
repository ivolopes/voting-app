package br.com.sicredi.voting.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AgendaRequest {
    @NotBlank(message = "The name is required")
    private String name;
}
