package br.com.sicredi.voting.domain.domainservices;

import br.com.sicredi.voting.domain.dto.response.EntityCreatedResponse;
import br.com.sicredi.voting.domain.entities.Affiliated;
import br.com.sicredi.voting.domain.exceptions.AlreadyExistsException;
import br.com.sicredi.voting.domain.repository.AffiliatedRepository;
import br.com.sicredi.voting.domain.services.AffiliatedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class AffiliatedDomainService implements AffiliatedService {

    private final AffiliatedRepository repository;

    public Mono<EntityCreatedResponse> save(String name, String cpf) {
        log.info("Start creating affiliated with cpf [{}]", cpf);
        Affiliated affiliated = new Affiliated(name, cpf);
        Mono<Affiliated> affiliatedMono = repository.findByCpf(cpf);

        return  affiliatedMono.map(Optional::of).defaultIfEmpty(Optional.empty()).flatMap(a -> {
            if (a.isPresent()) {
                log.error("This affiliated with cpf [{}] already exists", cpf);
                return Mono.error(new AlreadyExistsException("affiliated with cpf " + cpf + ""));
            } else {
                return repository.save(affiliated).map(s -> {
                    log.info("Affiliated with cpf [{}] created successfully", cpf);
                    return new EntityCreatedResponse(s.getId());
                });
            }
        });
    }
}
