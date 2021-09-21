package io.company.brewcraft.model;

import io.company.brewcraft.service.UpdatableEntity;

public interface UpdateSku<T extends UpdateSkuMaterial<? extends UpdateSku<T>>> extends BaseSku<T>, UpdatableEntity<Long> {

}
