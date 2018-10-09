package com.wigo.server.domain.Status;

import java.time.Instant;
import java.util.UUID;

public class StatusHashtag {

    private UUID statusId;

    private String hashtag;

    private Instant created;

    public StatusHashtag() {
    }

    public StatusHashtag(UUID statusId, String hashtag, Instant created) {
        this.statusId = statusId;
        this.hashtag = hashtag;
        this.created = created;
    }

    public UUID getStatusId() {
        return statusId;
    }

    public void setStatusId(UUID statusId) {
        this.statusId = statusId;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }
}
