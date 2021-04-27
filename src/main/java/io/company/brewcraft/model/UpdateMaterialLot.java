package io.company.brewcraft.model;

public interface UpdateMaterialLot<T extends UpdateShipment<? extends UpdateMaterialLot<T>>> extends BaseMaterialLot<T>, Versioned, Identified<Long> {
}
