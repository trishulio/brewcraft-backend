package io.company.brewcraft.model;

import io.company.brewcraft.service.UpdatableEntity;

public interface UpdateMaterialLot<T extends UpdateShipment<? extends UpdateMaterialLot<T>>> extends BaseMaterialLot<T>,UpdatableEntity<Long> {
}
