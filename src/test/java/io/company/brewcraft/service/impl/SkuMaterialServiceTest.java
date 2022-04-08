package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.BaseSkuMaterial;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.Sku;
import io.company.brewcraft.model.SkuMaterial;
import io.company.brewcraft.model.UpdateSkuMaterial;
import io.company.brewcraft.service.SkuMaterialService;
import io.company.brewcraft.service.UpdateService;
import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class SkuMaterialServiceTest {
    private SkuMaterialService service;
    private UpdateService<Long, SkuMaterial, BaseSkuMaterial<?>, UpdateSkuMaterial<?>> mUpdateService;

    @BeforeEach
    public void init() {
        this.mUpdateService = mock(UpdateService.class);
        this.service = new SkuMaterialService(this.mUpdateService);
    }

    @Test
    public void testGetPutSkuMaterials_ReturnsNewSkuMaterialsWithExistingSkuMaterialsUpdated_WhenPayloadObjectsHaveIds() {
        final List<SkuMaterial> existingSkuMaterials = List.of(
            new SkuMaterial(1L, new Sku(10L), new Material(12L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.HECTOLITRE), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1),
            new SkuMaterial(2L, new Sku(11L), new Material(13L), Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.HECTOLITRE), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 2)
        );

        final List<UpdateSkuMaterial<?>> skuMaterialUpdates = List.of(
            new SkuMaterial(1L, new Sku(20L), new Material(22L), Quantities.getQuantity(new BigDecimal("300"), SupportedUnits.HECTOLITRE), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1),
            new SkuMaterial(2L, new Sku(21L), new Material(23L), Quantities.getQuantity(new BigDecimal("400"), SupportedUnits.HECTOLITRE), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 2)
        );

        doAnswer(skuMaterial -> skuMaterial.getArgument(1, List.class)).when(this.mUpdateService).getPutEntities(existingSkuMaterials, skuMaterialUpdates);

        final List<SkuMaterial> updatedSkuMaterials = this.service.getPutEntities(existingSkuMaterials, skuMaterialUpdates);

        final List<UpdateSkuMaterial<?>> expected = List.of(
            new SkuMaterial(1L, new Sku(20L), new Material(22L), Quantities.getQuantity(new BigDecimal("300"), SupportedUnits.HECTOLITRE), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1),
            new SkuMaterial(2L, new Sku(21L), new Material(23L), Quantities.getQuantity(new BigDecimal("400"), SupportedUnits.HECTOLITRE), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 2)
        );

        assertEquals(expected, updatedSkuMaterials);
    }

    @Test
    public void testGetPatchSkuMaterials_ReturnsNewSkuMaterialCollectionWithNonNullPropertiesApplied_WhenPayloadObjectsHaveId() {
        final List<SkuMaterial> existingSkuMaterials = List.of(
            new SkuMaterial(1L, new Sku(10L), new Material(12L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.HECTOLITRE), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1),
            new SkuMaterial(2L, new Sku(11L), new Material(13L), Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.HECTOLITRE), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 2)
        );

        final List<UpdateSkuMaterial<?>> skuMaterialUpdates = List.of(
            new SkuMaterial(1L, new Sku(20L), new Material(22L), Quantities.getQuantity(new BigDecimal("300"), SupportedUnits.HECTOLITRE), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1),
            new SkuMaterial(2L, new Sku(21L), new Material(23L), Quantities.getQuantity(new BigDecimal("400"), SupportedUnits.HECTOLITRE), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 2)
        );

        doAnswer(skuMaterial -> skuMaterial.getArgument(1, List.class)).when(this.mUpdateService).getPatchEntities(existingSkuMaterials, skuMaterialUpdates);

        final List<SkuMaterial> updatedSkuMaterials = this.service.getPatchEntities(existingSkuMaterials, skuMaterialUpdates);

        final List<UpdateSkuMaterial<?>> expected = List.of(
            new SkuMaterial(1L, new Sku(20L), new Material(22L), Quantities.getQuantity(new BigDecimal("300"), SupportedUnits.HECTOLITRE), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1),
            new SkuMaterial(2L, new Sku(21L), new Material(23L), Quantities.getQuantity(new BigDecimal("400"), SupportedUnits.HECTOLITRE), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 2)
        );

        assertEquals(expected, updatedSkuMaterials);
    }

    @Test
    public void testAddCollection_ReturnsCollectionOfBaseSkuMaterials_WhenInputIsNotNull() {
        final List<BaseSkuMaterial<?>> skuMaterialUpdates = List.of(
            new SkuMaterial(1L, new Sku(20L), new Material(22L), Quantities.getQuantity(new BigDecimal("300"), SupportedUnits.HECTOLITRE), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1),
            new SkuMaterial(2L, new Sku(21L), new Material(23L), Quantities.getQuantity(new BigDecimal("400"), SupportedUnits.HECTOLITRE), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 2)
        );

        doAnswer(skuMaterial -> skuMaterial.getArgument(0, List.class)).when(this.mUpdateService).getAddEntities(skuMaterialUpdates);

        final List<SkuMaterial> updatedSkuMaterials = this.service.getAddEntities(skuMaterialUpdates);

        final List<BaseSkuMaterial<?>> expected = List.of(
            new SkuMaterial(1L, new Sku(20L), new Material(22L), Quantities.getQuantity(new BigDecimal("300"), SupportedUnits.HECTOLITRE), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 1),
            new SkuMaterial(2L, new Sku(21L), new Material(23L), Quantities.getQuantity(new BigDecimal("400"), SupportedUnits.HECTOLITRE), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), 2)
        );

        assertEquals(expected, updatedSkuMaterials);
    }
}
