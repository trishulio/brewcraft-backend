package io.company.brewcraft.service;

import io.company.brewcraft.model.MixtureMaterialPortion;

public interface MixtureMaterialPortionAccessor {
    final String ATTR_MATERIAL_PORTION = "materialPortion";

    MixtureMaterialPortion getMaterialPortion();

    void setMaterialPortion(MixtureMaterialPortion materialPortion);
}
