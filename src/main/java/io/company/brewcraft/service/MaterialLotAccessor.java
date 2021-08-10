package io.company.brewcraft.service;

import io.company.brewcraft.model.MaterialLot;

public interface MaterialLotAccessor {
    MaterialLot getMaterialLot();

    void setMaterialLot(MaterialLot lot);
}
