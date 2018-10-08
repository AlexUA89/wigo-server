package com.wigo.server.dto;

import java.time.Instant;
import java.util.UUID;

public class BriefStatusDto {

    private UUID id;

    private double latitude;

    private double longitude;

    private String name;

    private UUID category_id;

    private Instant startDate;

    private Instant endDate;

    public BriefStatusDto(){

    }

    public BriefStatusDto(UUID id, double latitude, double longitude, String name, UUID category_id, Instant startDate, Instant endDate) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.category_id = category_id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public UUID getCategoryId() {
        return category_id;
    }

    public void setCategoryId(UUID category_id) {
        this.category_id = category_id;
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
}
