package io.company.brewcraft.repository;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.SkuMaterial;
import io.company.brewcraft.service.MaterialAccessor;
import io.company.brewcraft.service.SkuMaterialAccessor;

public class SkuMaterialRefresher implements Refresher<SkuMaterial, SkuMaterialAccessor> {
    private static final Logger log = LoggerFactory.getLogger(SkuMaterialRefresher.class);

    private final Refresher<Material, MaterialAccessor> materialRefresher;

    public SkuMaterialRefresher(Refresher<Material, MaterialAccessor> materialRefresher) {
        this.materialRefresher = materialRefresher;
    }

    @Override
    public void refresh(Collection<SkuMaterial> skus) {
        materialRefresher.refreshAccessors(skus);
    }

    @Override
    public void refreshAccessors(Collection<? extends SkuMaterialAccessor> accessors) {
        // NOTE: not needed at this time
    }
}
