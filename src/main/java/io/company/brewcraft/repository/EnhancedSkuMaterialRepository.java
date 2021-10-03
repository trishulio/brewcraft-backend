package io.company.brewcraft.repository;

import java.util.Collection;

import io.company.brewcraft.model.SkuMaterial;

public interface EnhancedSkuMaterialRepository {
    
    void refresh(Collection<SkuMaterial> entities);
}
