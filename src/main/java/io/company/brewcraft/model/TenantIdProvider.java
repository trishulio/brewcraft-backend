package io.company.brewcraft.model;

import java.util.UUID;

public interface TenantIdProvider {
    UUID getTenantId();
}
