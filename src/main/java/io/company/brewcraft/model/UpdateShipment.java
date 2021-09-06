package io.company.brewcraft.model;

import io.company.brewcraft.service.UpdatableEntity;

public interface UpdateShipment<T extends UpdateMaterialLot<? extends UpdateShipment<T>>> extends BaseShipment<T>, UpdatableEntity<Long> {
}