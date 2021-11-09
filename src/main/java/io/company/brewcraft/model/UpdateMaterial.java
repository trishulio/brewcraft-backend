package io.company.brewcraft.model;

import io.company.brewcraft.service.UpdatableEntity;

public interface UpdateMaterial extends BaseMaterial, Versioned, UpdatableEntity<Long> {

}
