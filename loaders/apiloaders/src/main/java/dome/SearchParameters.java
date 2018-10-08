package dome;

public class SearchParameters {

    private double latitude;
    private double longitude;
    private double radius;
    private String descRiption;

    public SearchParameters(double latitude, double longitude, double radius, String descRiption) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.descRiption = descRiption;
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
}
