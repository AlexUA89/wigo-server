package com.wigo.server.dto;

import java.time.Instant;
import java.util.UUID;

public class CategoryDto {

    private UUID id;

    private String name;

    private String imageUrl;

    private UUID parentId;

    private Instant created;

    private String displayName;

    private String desc;

    public CategoryDto() {
    }

    public CategoryDto(UUID id, String name, String imageUrl, UUID parentId, Instant created, String displayName, String desc) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.parentId = parentId;
        this.created = created;
        this.displayName = displayName;
        this.desc = desc;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
