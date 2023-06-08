package br.com.sicredi.voting.application.rest.v1;

import br.com.sicredi.voting.application.rest.dtos.AgendaRequest;
import br.com.sicredi.voting.domain.dto.response.AgendaResponse;
import br.com.sicredi.voting.domain.inputs.AgendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
