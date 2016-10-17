package com.wigo.server.dto;

import java.util.UUID;

public class UserDto {

    private UUID id;

    private String nickname;

    private String name;

    public UserDto(){

    }

    public UserDto(UUID id, String nickname, String name) {
        this.id = id;
        this.nickname = nickname;
        this.name = name;
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
}
