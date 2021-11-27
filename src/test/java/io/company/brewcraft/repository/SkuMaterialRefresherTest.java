package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.SkuMaterial;

public class SkuMaterialRefresherTest {
    private SkuMaterialRefresher skuMaterialRefresher;

    private MaterialRepository materialRepository;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        materialRepository = mock(MaterialRepository.class);

        skuMaterialRefresher = new SkuMaterialRefresher(materialRepository);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<SkuMaterial> skuMaterials = List.of(new SkuMaterial(1L));

        skuMaterialRefresher.refresh(skuMaterials);

        verify(materialRepository, times(1)).refreshAccessors(skuMaterials);
    }

}
