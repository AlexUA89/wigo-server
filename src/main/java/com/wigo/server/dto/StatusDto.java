package com.wigo.server.dto;

import java.net.URL;
import java.time.Instant;
import java.util.*;

public class StatusDto {

    private UUID id;

    private UUID userId;

    private double latitude;

    private double longitude;

    private String name;

    private String text;

    private URL url;

    private Instant startDate;

    private Instant endDate;

    private Set<String> hashtags = new HashSet<>();

    private UUID category_id;

    private List<URL> images = new ArrayList<>();

    private Instant created;

    public StatusDto(){

    }

    public StatusDto(UUID id, UUID userId, double latitude, double longitude, String name, String text, URL url, Instant startDate, Instant endDate, Set<String> hashtags, UUID category_id, List<URL> images, Instant created) {
        this.id = id;
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.text = text;
        this.url = url;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hashtags = hashtags;
        this.category_id = category_id;
        this.images = images;
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

    public Set<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(Set<String> hashtags) {
        this.hashtags = hashtags;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public List<URL> getImages() {
        return images;
    }

    public void setImages(List<URL> images) {
        this.images = images;
    }

    public UUID getCategoryId() {
        return category_id;
    }

    public void setCategoryId(UUID category_id) {
        this.category_id = category_id;
    }

    public UUID getCategory_id() {
        return category_id;
    }

    public void setCategory_id(UUID category_id) {
        this.category_id = category_id;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }
}
