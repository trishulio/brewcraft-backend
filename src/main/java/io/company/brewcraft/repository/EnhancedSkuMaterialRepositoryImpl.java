package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.company.brewcraft.model.SkuMaterial;

public class EnhancedSkuMaterialRepositoryImpl implements EnhancedSkuMaterialRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedSkuMaterialRepositoryImpl.class);
    
    private MaterialRepository materialRepository;
    
    @Autowired
    public EnhancedSkuMaterialRepositoryImpl(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    @Override
    public void refresh(Collection<SkuMaterial> skus) {
        materialRepository.refreshAccessors(skus);
    }
}
