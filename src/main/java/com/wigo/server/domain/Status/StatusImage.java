package com.wigo.server.domain.Status;

import java.net.URL;
import java.util.UUID;

public class StatusImage {
    private UUID statusId;

    private URL url;

    public StatusImage() {
    }

    public StatusImage(UUID statusId, URL url) {
        this.statusId = statusId;
        this.url = url;
    }

    public UUID getStatusId() {
        return statusId;
    }

    public void setStatusId(UUID statusId) {
        this.statusId = statusId;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
