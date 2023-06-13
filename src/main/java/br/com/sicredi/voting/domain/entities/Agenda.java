package br.com.sicredi.voting.domain.entities;

import br.com.sicredi.voting.domain.exceptions.InvalidEntityException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Document
@Getter
@EqualsAndHashCode
public class Agenda {
    @Id
    private String id;
    private String name;
    private Integer finishingTimeAmount;
    private LocalDateTime finishingTime;
    private Boolean accounted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Agenda(String name, Integer finishingTimeAmount) {
        id = UUID.randomUUID().toString();
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
        this.finishingTimeAmount = finishingTimeAmount;
        finishingTime = createdAt.plusMinutes(finishingTimeAmount);
        this.accounted = false;
        this.name = name;
        validateName();
    }

    public void markAsAccounted() {
        this.accounted = true;
        this.updatedAt = LocalDateTime.now();
    }
    private void validateName() {
        if (Objects.isNull(name) || name.trim().equals("")) {
            throw new InvalidEntityException("The agenda's name is required");
        }
    }
}
