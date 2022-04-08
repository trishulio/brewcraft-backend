package io.company.brewcraft.model;

import io.company.brewcraft.service.UpdatableEntity;

public interface UpdateMaterial extends BaseMaterial, VersionAccessor, UpdatableEntity<Long> {
}
