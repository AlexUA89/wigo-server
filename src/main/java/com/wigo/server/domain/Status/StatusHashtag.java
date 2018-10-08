package com.wigo.server.domain.Status;

import java.util.UUID;

public class StatusHashtag {

    private UUID statusId;

    private String hashtag;

    public StatusHashtag() {
    }

    public StatusHashtag(UUID statusId, String hashtag) {
        this.statusId = statusId;
        this.hashtag = hashtag;
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

}
