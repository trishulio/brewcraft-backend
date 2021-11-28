package io.company.brewcraft.repository;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.SkuMaterial;
import io.company.brewcraft.repository.MaterialRefresher;
import io.company.brewcraft.repository.SkuMaterialRefresher;

public class SkuMaterialRefresherTest {
    private SkuMaterialRefresher skuMaterialRefresher;

    private MaterialRefresher materialRefresher;

    @SuppressWarnings("unchecked")
    @BeforeEach
    public void init() {
        materialRefresher = mock(MaterialRefresher.class);

        skuMaterialRefresher = new SkuMaterialRefresher(materialRefresher);
    }

    @Test
    public void testRefresh_PerformsRefreshOnChildEntities() {
        List<SkuMaterial> skuMaterials = List.of(new SkuMaterial(1L));

        skuMaterialRefresher.refresh(skuMaterials);

        verify(materialRefresher, times(1)).refreshAccessors(skuMaterials);
    }

}
