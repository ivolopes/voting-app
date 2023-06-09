package br.com.sicredi.voting.application.rest.v1;

import br.com.sicredi.voting.application.rest.dtos.AgendaRequest;
import br.com.sicredi.voting.application.rest.dtos.SessionRequest;
import br.com.sicredi.voting.domain.dto.response.EntityCreatedResponse;
import br.com.sicredi.voting.domain.services.AgendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/agendas")
@RequiredArgsConstructor
public class AgendaController {

    private final AgendaService agendaService;

    @PostMapping
    public Mono<EntityCreatedResponse> save(@RequestBody AgendaRequest request) {
        return agendaService.save(request.getName());
    }

    @PostMapping("/{agendaId}/session")
    public Mono<EntityCreatedResponse> createSession(@PathVariable("agendaId") String agendaId, @RequestBody SessionRequest request) {
        return agendaService.createSession(agendaId, request.getName());
    }

}
