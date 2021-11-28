package io.company.brewcraft.service;

import java.util.List;

public interface SkuMaterialAccessor<T> {
    final String ATTR_SKU_MATERIALS = "materials";

    List<T> getMaterials();

    void setMaterials(List<T> materials);
}
