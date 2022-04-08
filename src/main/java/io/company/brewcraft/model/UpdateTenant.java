package io.company.brewcraft.model;

import java.util.UUID;

import io.company.brewcraft.service.UpdatableEntity;

public interface UpdateTenant extends BaseTenant, UpdatableEntity<UUID> {
}
