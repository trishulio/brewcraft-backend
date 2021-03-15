package io.company.brewcraft.pojo;

import io.company.brewcraft.model.Versioned;

public interface UpdateShipment<T extends UpdateShipmentItem> extends BaseShipment<T>, Versioned {
}
