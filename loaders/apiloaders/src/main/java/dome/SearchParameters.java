package dome;

import java.time.Instant;

public class SearchParameters {

    private double latitude;
    private double longitude;
    private double radius;
    private String descRiption;
    private Instant startDate;
    private Instant endDate;

    public SearchParameters(double latitude, double longitude, double radius, String descRiption, Instant startDate, Instant endDate) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.descRiption = descRiption;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public String getDescRiption() {
        return descRiption;
    }

    public void setDescRiption(String descRiption) {
        this.descRiption = descRiption;
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
