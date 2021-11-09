package io.company.brewcraft.dto;

import io.company.brewcraft.service.UpdatableEntity;

public interface UpdateMaterialCategory<T extends UpdateMaterialCategory<T>> extends BaseMaterialCategory<T>, UpdatableEntity<Long> {
}
