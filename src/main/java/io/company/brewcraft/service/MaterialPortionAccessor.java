package io.company.brewcraft.service;

import io.company.brewcraft.model.MaterialPortion;

public interface MaterialPortionAccessor {
    final String ATTR_MATERIAL_PORTION = "materialPortion";

    MaterialPortion getMaterialPortion();

    void setMaterialPortion(MaterialPortion materialPortion);
}
