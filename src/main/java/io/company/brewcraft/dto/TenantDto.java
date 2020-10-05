package io.company.brewcraft.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;

public class TenantDto {

    private UUID id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String domain;

    private LocalDateTime created;

    public TenantDto() {

    }

    public TenantDto(UUID id, String name, String domain, LocalDateTime created) {
        this.id = id;
        this.name = name;
        this.domain = domain;
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

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
