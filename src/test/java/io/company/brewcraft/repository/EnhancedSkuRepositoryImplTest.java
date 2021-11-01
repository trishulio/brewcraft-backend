package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Sku;
import io.company.brewcraft.model.SkuAccessor;
import io.company.brewcraft.model.SkuMaterial;

public class EnhancedSkuRepositoryImplTest {
    private EnhancedSkuRepository repo;

    private ProductRepository productRepository;

    private SkuMaterialRepository skuMaterialRepository;

    private AccessorRefresher<Long, SkuAccessor, Sku> skuAccessorMock;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        productRepository = mock(ProductRepository.class);
        skuMaterialRepository = mock(SkuMaterialRepository.class);
        skuAccessorMock = mock(AccessorRefresher.class);

        repo = new EnhancedSkuRepositoryImpl(productRepository, skuMaterialRepository, skuAccessorMock);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<Sku> skus = List.of(new Sku(1L));
        List<SkuMaterial> skuMaterials = List.of(new SkuMaterial(1L));

        skus.get(0).setMaterials(skuMaterials);

        repo.refresh(skus);

        verify(productRepository, times(1)).refreshAccessors(skus);
        verify(skuMaterialRepository, times(1)).refresh(skuMaterials);
    }

    @Test
    public void testRefreshAccessors_CallsRefresherAccessor() {
        List<SkuAccessor> accessors = List.of(mock(SkuAccessor.class), mock(SkuAccessor.class));

        repo.refreshAccessors(accessors);

        verify(skuAccessorMock, times(1)).refreshAccessors(accessors);
    }

}
