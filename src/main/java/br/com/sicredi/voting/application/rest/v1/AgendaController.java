package br.com.sicredi.voting.application.rest.v1;

import br.com.sicredi.voting.domain.dto.request.AgendaRequest;
import br.com.sicredi.voting.domain.dto.response.EntityCreatedResponse;
import br.com.sicredi.voting.domain.services.AgendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/agendas")
@RequiredArgsConstructor
public class AgendaController {

    private final AgendaService agendaService;

    @PostMapping
    public Mono<EntityCreatedResponse> save(@RequestBody @Valid AgendaRequest request) {
        return agendaService.save(request.getName());
    }

}
