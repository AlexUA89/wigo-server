package com.wigo.server.dto;

import java.net.URL;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.wigo.server.utils.DaoUtils.MAX_INSTANT;
import static com.wigo.server.utils.DaoUtils.MIN_INSTANT;

/**
 * Created by alyaxey on 9/16/16.
 */
public class StatusSearchParamsDto {
    private double startLatitude = -1e4;
    private double endLatitude = 1e4;
    private double startLongitude = -1e4;
    private double endLongitude = 1e4;
    private Instant startDate = MIN_INSTANT;
    private Instant endDate = MAX_INSTANT;
    private String search;
    private Set<UUID> categories = new HashSet<>();
    private URL url;

    public double getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public double getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(double endLatitude) {
        this.endLatitude = endLatitude;
    }

    public double getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public double getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(double endLongitude) {
        this.endLongitude = endLongitude;
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

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public boolean getNoCategories() {
        return categories.isEmpty();
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public Set<UUID> getCategories() {
        return categories;
    }

    public void setCategories(Set<UUID> categories) {
        this.categories = categories;
    }
}
