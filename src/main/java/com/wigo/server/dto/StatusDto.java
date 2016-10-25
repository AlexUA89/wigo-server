package com.wigo.server.dto;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public class StatusDto {

    private UUID id;

    private UUID userId;

    private double latitude;

    private double longitude;

    private String name;

    private String text;

    private Instant startDate;

    private Instant endDate;

    private StatusKind kind;

    private Set<String> hashtags;

    public StatusDto(){

    }

    public StatusDto(UUID id, UUID userId, double latitude, double longitude, String name, String text, Instant startDate, Instant endDate, StatusKind kind, Set<String> hashtags) {
        this.id = id;
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.text = text;
        this.startDate = startDate;
        this.endDate = endDate;
        this.kind = kind;
        this.hashtags = hashtags;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public StatusKind getKind() {
        return kind;
    }

    public void setKind(StatusKind kind) {
        this.kind = kind;
    }

    public Set<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(Set<String> hashtags) {
        this.hashtags = hashtags;
    }

}
