package io.company.brewcraft.model;

import java.time.LocalDateTime;

public interface Audited {
    final String FIELD_CREATED_AT = "createdAt";
    final String FIELD_LAST_UPDATED_AT = "lastUpdatedAt";
    
    LocalDateTime getCreatedAt();

    void setCreatedAt(LocalDateTime createdAt);

    LocalDateTime getLastUpdated();

    void setLastUpdated(LocalDateTime lastUpdated);
}
