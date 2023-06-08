package br.com.sicredi.voting.domain.entities;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Document
@Getter
public class Agenda {
    @Id
    private String id;
    private String name;
    private Date createdAt;
    private Date updatedAt;

    public Agenda(String name) {
        id = UUID.randomUUID().toString();
        createdAt = new Date();
        setUpdatedAt();
        this.name = name;
    }

    public void updateName(String name) {
        this.name = name;
        setUpdatedAt();
    }

    private void setUpdatedAt() {
        updatedAt = new Date();
    }
}
