package br.com.sicredi.voting.application.rest.v1;

import br.com.sicredi.voting.domain.dto.request.AffiliatedRequest;
import br.com.sicredi.voting.domain.dto.response.EntityCreatedResponse;
import br.com.sicredi.voting.domain.services.AffiliatedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/affiliated")
@RequiredArgsConstructor
public class AffiliatedController {

    private final AffiliatedService service;

    @PostMapping
    public Mono<EntityCreatedResponse> save(@RequestBody AffiliatedRequest request) {
        return service.save(request.getName(), request.getCpf());
    }
}
