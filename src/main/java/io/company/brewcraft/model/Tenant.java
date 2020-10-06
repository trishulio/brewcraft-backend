package io.company.brewcraft.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Tenant {

    private UUID id;
    private String name;
    private String url;
    private LocalDateTime created;

    public Tenant() {

    }

    public Tenant(UUID id, String name, String url, LocalDateTime created) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.created = created;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
