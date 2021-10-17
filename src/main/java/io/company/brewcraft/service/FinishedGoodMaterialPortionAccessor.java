package io.company.brewcraft.service;

import io.company.brewcraft.model.FinishedGoodMaterialPortion;

public interface FinishedGoodMaterialPortionAccessor {
    final String ATTR_MATERIAL_PORTION = "materialPortion";

    FinishedGoodMaterialPortion getMaterialPortion();

    void setMaterialPortion(FinishedGoodMaterialPortion materialPortion);
}
