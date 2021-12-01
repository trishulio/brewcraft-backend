package io.company.brewcraft.service;

import java.util.List;

import io.company.brewcraft.model.SkuMaterial;

public interface SkuMaterialAccessor {
    final String ATTR_SKU_MATERIALS = "materials";

    List<SkuMaterial> getMaterials();

    void setMaterials(List<SkuMaterial> materials);
}
