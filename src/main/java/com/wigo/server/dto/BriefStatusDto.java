package com.wigo.server.dto;

import java.net.URL;
import java.time.Instant;
import java.util.*;

public class BriefStatusDto {

    private UUID id;

    private double latitude;

    private double longitude;

    private String name;

    private String category;

    public BriefStatusDto(){

    }

    public BriefStatusDto(UUID id, double latitude, double longitude, String name, String category) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
