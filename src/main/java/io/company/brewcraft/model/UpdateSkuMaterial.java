package io.company.brewcraft.model;

import io.company.brewcraft.service.UpdatableEntity;

public interface UpdateSkuMaterial<T extends UpdateSku<? extends UpdateSkuMaterial<T>>> extends BaseSkuMaterial<T>, UpdatableEntity<Long> {
}
