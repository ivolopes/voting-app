package br.com.sicredi.voting.domain.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document
@Getter
@EqualsAndHashCode
public class Vote {
    @Id
    private String id;
    private String agenda;
    private String affiliated;
    private Boolean vote;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Vote(String agenda, String affiliated, boolean vote) {
        this.id = UUID.randomUUID().toString();
        this.agenda = agenda;
        this.affiliated = affiliated;
        this.vote = vote;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }
}
