package com.wigo.server.domain;

import java.time.Instant;
import java.util.UUID;

public class User {

    private UUID id;

    private String nickname;

    private String name;

    private String email;

    private String fbId;

    private Instant created;

    public User(){

    }

    public User(UUID id, String nickname, String name, String email, String fbId, Instant created) {
        this.id = id;
        this.nickname = nickname;
        this.name = name;
        this.email = email;
        this.fbId = fbId;
        this.created = created;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }
}
