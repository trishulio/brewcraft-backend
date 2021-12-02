package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import io.company.brewcraft.model.Product;
import io.company.brewcraft.model.Sku;
import io.company.brewcraft.model.SkuAccessor;
import io.company.brewcraft.model.SkuMaterial;
import io.company.brewcraft.service.ProductAccessor;
import io.company.brewcraft.service.SkuMaterialAccessor;

public class SkuRefresher implements Refresher<Sku, SkuAccessor> {
    private static final Logger log = LoggerFactory.getLogger(SkuRefresher.class);

    private final Refresher<Product, ProductAccessor> productRefresher;

    private final Refresher<SkuMaterial, SkuMaterialAccessor> skuMaterialRefresher;

    private final AccessorRefresher<Long, SkuAccessor, Sku> refresher;

    @Autowired
    public SkuRefresher(Refresher<Product, ProductAccessor> productRefresher, Refresher<SkuMaterial, SkuMaterialAccessor> skuMaterialRefresher, AccessorRefresher<Long, SkuAccessor, Sku> refresher) {
        this.productRefresher = productRefresher;
        this.skuMaterialRefresher = skuMaterialRefresher;
        this.refresher = refresher;
    }

    @Override
    public void refreshAccessors(Collection<? extends SkuAccessor> accessors) {
        this.refresher.refreshAccessors(accessors);
    }

    @Override
    public void refresh(Collection<Sku> skus) {
        this.productRefresher.refreshAccessors(skus);
        final List<SkuMaterial> materials = skus.stream().filter(i -> i.getMaterials() != null).flatMap(i -> i.getMaterials().stream()).collect(Collectors.toList());

        this.skuMaterialRefresher.refresh(materials);
    }
}
