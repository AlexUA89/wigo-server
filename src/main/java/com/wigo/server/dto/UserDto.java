package com.wigo.server.dto;

import com.wigo.server.domain.User;

import java.util.UUID;

public class UserDto {

    private UUID id;

    private String nickname;

    private String name;

    public UserDto(){

    }

    public UserDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.name = user.getName();
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
