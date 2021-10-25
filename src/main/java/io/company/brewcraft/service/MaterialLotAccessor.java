package io.company.brewcraft.service;

import io.company.brewcraft.model.MaterialLot;

public interface MaterialLotAccessor {
    final String ATTR_MATERIAL_LOT = "materialLot";

    MaterialLot getMaterialLot();

    void setMaterialLot(MaterialLot lot);
}
