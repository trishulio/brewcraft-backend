package io.company.brewcraft.dto;

import java.time.LocalDateTime;

public interface Audited {
    LocalDateTime getCreatedAt();

    void setCreatedAt(LocalDateTime createdAt);

    LocalDateTime getLastUpdated();

    void setLastUpdated(LocalDateTime lastUpdated);
}
