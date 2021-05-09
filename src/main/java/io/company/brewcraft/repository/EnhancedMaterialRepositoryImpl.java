package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.company.brewcraft.model.Material;
import io.company.brewcraft.service.MaterialAccessor;

public class EnhancedMaterialRepositoryImpl implements EnhancedMaterialRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedMaterialRepositoryImpl.class);

    private AccessorRefresher<Long, MaterialAccessor, Material> refresher;

    @Autowired
    public EnhancedMaterialRepositoryImpl(AccessorRefresher<Long, MaterialAccessor, Material> refresher) {
        this.refresher = refresher;
    }

    @Override
    public void refreshAccessors(Collection<? extends MaterialAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }
}
