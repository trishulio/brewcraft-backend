package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Product;
import io.company.brewcraft.model.Sku;
import io.company.brewcraft.model.SkuAccessor;
import io.company.brewcraft.model.SkuMaterial;
import io.company.brewcraft.repository.AccessorRefresher;
import io.company.brewcraft.repository.Refresher;
import io.company.brewcraft.repository.SkuRefresher;
import io.company.brewcraft.service.ProductAccessor;
import io.company.brewcraft.service.SkuMaterialAccessor;

public class SkuRefresherTest {
    private SkuRefresher skuRefresher;

    private Refresher<Product, ProductAccessor> productRefresher;

    private Refresher<SkuMaterial, SkuMaterialAccessor> skuMaterialRefresher;

    private AccessorRefresher<Long, SkuAccessor, Sku> skuAccessorMock;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        productRefresher = mock(Refresher.class);
        skuMaterialRefresher = mock(Refresher.class);
        skuAccessorMock = mock(AccessorRefresher.class);

        skuRefresher = new SkuRefresher(productRefresher, skuMaterialRefresher, skuAccessorMock);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<Sku> skus = List.of(new Sku(1L));
        List<SkuMaterial> skuMaterials = List.of(new SkuMaterial(1L));

        skus.get(0).setMaterials(skuMaterials);

        skuRefresher.refresh(skus);

        verify(productRefresher, times(1)).refreshAccessors(skus);
        verify(skuMaterialRefresher, times(1)).refresh(skuMaterials);
    }

    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<SkuAccessor> accessors = List.of(mock(SkuAccessor.class), mock(SkuAccessor.class));

        skuRefresher.refreshAccessors(accessors);

        verify(skuAccessorMock, times(1)).refreshAccessors(accessors);
    }
}
