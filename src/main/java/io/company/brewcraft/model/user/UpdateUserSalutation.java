package io.company.brewcraft.model.user;

import io.company.brewcraft.model.Versioned;
import io.company.brewcraft.service.UpdatableEntity;

public interface UpdateUserSalutation extends BaseUserSalutation, UpdatableEntity<Long> {
}
