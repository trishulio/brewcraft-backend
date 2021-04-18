package io.company.brewcraft.service;

import io.company.brewcraft.model.Material;

public interface MaterialAccessor {
    Material getMaterial();
    
    void setMaterial(Material material);
}
