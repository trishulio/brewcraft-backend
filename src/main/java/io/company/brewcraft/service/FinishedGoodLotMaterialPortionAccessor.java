package io.company.brewcraft.service;

import io.company.brewcraft.model.FinishedGoodLotMaterialPortion;

public interface FinishedGoodLotMaterialPortionAccessor {
    final String ATTR_MATERIAL_PORTION = "materialPortion";

    FinishedGoodLotMaterialPortion getMaterialPortion();

    void setMaterialPortion(FinishedGoodLotMaterialPortion materialPortion);
}
