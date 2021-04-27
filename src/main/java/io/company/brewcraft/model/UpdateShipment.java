package io.company.brewcraft.model;

public interface UpdateShipment<T extends UpdateMaterialLot<? extends UpdateShipment<T>>> extends BaseShipment<T>, Versioned {
}