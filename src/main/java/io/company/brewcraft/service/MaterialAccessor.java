package io.company.brewcraft.service;

import io.company.brewcraft.model.Material;

public interface MaterialAccessor {
    final String ATTR_MATERIAL = "material";

    Material getMaterial();
    
    void setMaterial(Material material);
}
