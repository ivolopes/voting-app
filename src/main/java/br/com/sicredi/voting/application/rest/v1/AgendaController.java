package br.com.sicredi.voting.application.rest.v1;

import br.com.sicredi.voting.application.rest.dtos.AgendaRequest;
import br.com.sicredi.voting.domain.dto.response.AgendaResponse;
import br.com.sicredi.voting.domain.dto.response.SessionResponse;
import br.com.sicredi.voting.domain.inputs.AgendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/agenda")
@RequiredArgsConstructor
public class AgendaController {

    private final AgendaService agendaService;

    @PostMapping
    public Mono<AgendaResponse> save(@RequestBody AgendaRequest request) {
        return agendaService.save(request.getName());
    }

    @PostMapping("/{agendaId}/session")
    public Mono<SessionResponse> createSession(@PathVariable("agendaId") String agendaId) {
        return agendaService.createSession(agendaId);
    }

}
