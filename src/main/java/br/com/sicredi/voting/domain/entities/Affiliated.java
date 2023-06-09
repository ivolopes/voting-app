package br.com.sicredi.voting.domain.entities;

import br.com.sicredi.voting.domain.exceptions.InvalidEntityException;
import br.com.sicredi.voting.domain.utils.ValidaCPF;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@EqualsAndHashCode
public class Affiliated {
    private String id;
    private String name;
    private String cpf;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Affiliated(String name, String cpf) {
        this.id = UUID.randomUUID().toString();
        setName(name);
        setCpf(cpf);
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    private void setCpf(String cpf) {

        if(cpf == null || "".equals(cpf.trim())){
            throw new InvalidEntityException("The cpf is required");
        } else {
            this.cpf = cpf.replaceAll("[^0-9]+", "");
            if (!isValidCpf()) {
                throw new InvalidEntityException("The cpf " + cpf + "is invalid.");
            }
        }
    }
    private void setName(String name) {
        if(name == null || "".equals(name.trim())) {
            throw new InvalidEntityException("The affiliated name is required.");
        }
        this.name = name;
    }

    private boolean isValidCpf() {
        return ValidaCPF.isCPF(this.cpf);
    }
}
