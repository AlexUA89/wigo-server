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

    private StatusKind kind;

    private Set<String> hashtags = new HashSet<>();

    private String category;

    private List<URL> images = new ArrayList<>();

    public StatusDto(){

    }

    public StatusDto(UUID id, UUID userId, double latitude, double longitude, String name, String text, URL url, Instant startDate, Instant endDate, StatusKind kind, Set<String> hashtags, String category, List<URL> images) {
        this.id = id;
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.text = text;
        this.url = url;
        this.startDate = startDate;
        this.endDate = endDate;
        this.kind = kind;
        this.hashtags = hashtags;
        this.category = category;
        this.images = images;
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

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<URL> getImages() {
        return images;
    }

    public void setImages(List<URL> images) {
        this.images = images;
    }
}
