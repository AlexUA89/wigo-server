package com.wigo.server.dto;

import java.time.Instant;
import java.util.UUID;

/**
 * Created by AlexUA on 9/11/2016.
 */
public class MessageDto {

    private UUID id;

    private UUID userId;

    private String text;

    private Instant created;

    public MessageDto(){

    }

    public MessageDto(UUID id, UUID userId, String text, Instant created) {
        this.id = id;
        this.userId = userId;
        this.text = text;
        this.created = created;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }
}
