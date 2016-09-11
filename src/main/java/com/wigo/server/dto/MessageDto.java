package com.wigo.server.dto;

import java.time.Instant;

/**
 * Created by AlexUA on 9/11/2016.
 */
public class MessageDto {

    private String message;

    private UserDto user;

    private Instant created;

    public MessageDto(){

    }

    public MessageDto(String message, UserDto user, Instant created) {
        this.message = message;
        this.user = user;
        this.created = created;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }
}
