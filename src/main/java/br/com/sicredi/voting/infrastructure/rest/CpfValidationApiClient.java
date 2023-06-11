package br.com.sicredi.voting.infrastructure.rest;

import br.com.sicredi.voting.domain.dto.response.CpfValidationResponse;
import br.com.sicredi.voting.domain.dto.response.StatusCpfEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Objects;

@Component
@Log4j2
public class CpfValidationApiClient {

    @Value("${app.rest.cpf-validation}")
    private String apiUrl;
    @Value("${app.cpf-validation}")
    private Boolean cpfValidationFlag;
    private final RestTemplate restTemplate;

    @Autowired
    public CpfValidationApiClient() {
        restTemplate = new RestTemplateBuilder().setConnectTimeout(Duration.ofSeconds(2))
                .setReadTimeout(Duration.ofSeconds(2)).build();
    }

    public boolean validateCpf(String cpf) {

        if (cpfValidationFlag) {
            if (cpf == null || "".equals(cpf.trim())) {
                return false;
            }

            cpf = cpf.replaceAll("[^0-9]+", "");
            String url = this.apiUrl + cpf;

            try {
                ResponseEntity<CpfValidationResponse> response = restTemplate.getForEntity(url, CpfValidationResponse.class);

                if (response.getStatusCode().is2xxSuccessful()) {
                    return Objects.requireNonNull(response.getBody()).getStatus().equals(StatusCpfEnum.ABLE_TO_VOTE);
                } else {
                    return false;
                }
            } catch (HttpClientErrorException httpException) {
                return false;
            }
        } else {
            return true;
        }
    }
}
