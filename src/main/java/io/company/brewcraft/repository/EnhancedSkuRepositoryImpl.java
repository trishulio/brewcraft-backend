package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.company.brewcraft.model.Sku;
import io.company.brewcraft.model.SkuAccessor;
import io.company.brewcraft.model.SkuMaterial;

public class EnhancedSkuRepositoryImpl implements EnhancedSkuRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedSkuRepositoryImpl.class);
    
    private ProductRepository productRepository;
    
    private SkuMaterialRepository skuMaterialRepository;

    private AccessorRefresher<Long, SkuAccessor, Sku> refresher;

    @Autowired
    public EnhancedSkuRepositoryImpl(ProductRepository productRepository, SkuMaterialRepository skuMaterialRepository, AccessorRefresher<Long, SkuAccessor, Sku> refresher) {
        this.productRepository = productRepository;
        this.skuMaterialRepository = skuMaterialRepository;
        this.refresher = refresher;
    }

    @Override
    public void refreshAccessors(Collection<? extends SkuAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }

    @Override
    public void refresh(Collection<Sku> skus) {
        this.productRepository.refreshAccessors(skus);
        final List<SkuMaterial> materials = skus.stream().filter(i -> i.getMaterials() != null).flatMap(i -> i.getMaterials().stream()).collect(Collectors.toList());
    
        this.skuMaterialRepository.refresh(materials);
    }
}
