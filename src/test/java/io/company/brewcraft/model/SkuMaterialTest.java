package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import javax.measure.Quantity;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import io.company.brewcraft.service.exception.IncompatibleQuantityUnitException;
import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;
import io.company.brewcraft.util.SupportedUnits;

public class SkuMaterialTest {
    private SkuMaterial skuMaterial;

    @BeforeEach
    public void init() {
        skuMaterial = new SkuMaterial();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        Sku sku = new Sku(2L);
        Material material = new Material(9L);
        Quantity<?> quantity = Quantities.getQuantity(100.0, SupportedUnits.HECTOLITRE);
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        SkuMaterial skuMaterial = new SkuMaterial(id, sku, material, quantity, created, lastUpdated, version);

        assertEquals(1L, skuMaterial.getId());
        assertEquals(new Sku(2L), skuMaterial.getSku());
        assertEquals(new Material(9L), skuMaterial.getMaterial());
        assertEquals(Quantities.getQuantity(100.0, SupportedUnits.HECTOLITRE), skuMaterial.getQuantity());
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), skuMaterial.getCreatedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), skuMaterial.getLastUpdated());
        assertEquals(1, skuMaterial.getVersion());
    }

    @Test
    public void testGetSetId() {
        skuMaterial.setId(1L);
        assertEquals(1L, skuMaterial.getId());
    }

    @Test
    public void testGetSetSku() {
        skuMaterial.setSku(new Sku(3L));
        assertEquals(new Sku(3L), skuMaterial.getSku());
    }

    @Test
    public void testGetSetMaterial() {
        skuMaterial.setMaterial(new Material(3L));
        assertEquals(new Material(3L), skuMaterial.getMaterial());
    }

    @Test
    public void testSetMaterial_ThrowsException_WhenQuantityIsIncompatibleWithMaterialQuantityUnit() {
        Material material = new Material(3L);
        material.setBaseQuantityUnit(SupportedUnits.LITRE);

        skuMaterial.setQuantity(Quantities.getQuantity("10 g"));
        assertThrows(IncompatibleQuantityUnitException.class, () -> skuMaterial.setMaterial(material));
    }

    @Test
    public void testGetSetQuantity() {
        skuMaterial.setQuantity(Quantities.getQuantity(100.0, SupportedUnits.HECTOLITRE));
        assertEquals(Quantities.getQuantity(100.0, SupportedUnits.HECTOLITRE), skuMaterial.getQuantity());
    }

    @Test
    public void testSetQuantity_ThrowsException_WhenQuantityIsIncompatibleWithMaterialQuantityUnit() {
        Material material = new Material(3L);
        material.setBaseQuantityUnit(SupportedUnits.GRAM);
        skuMaterial.setMaterial(material);

        assertThrows(IncompatibleQuantityUnitException.class, () -> skuMaterial.setQuantity(Quantities.getQuantity("10 l")));
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        skuMaterial.setCreatedAt(created);
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), skuMaterial.getCreatedAt());
    }

    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        skuMaterial.setLastUpdated(lastUpdated);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), skuMaterial.getLastUpdated());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        skuMaterial.setVersion(version);
        assertEquals(version, skuMaterial.getVersion());
    }

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        Long id = 1L;
        Sku sku = new Sku(2L);
        Material material = new Material(9L);
        Quantity<?> quantity = Quantities.getQuantity(100.0, SupportedUnits.HECTOLITRE);
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        SkuMaterial skuMaterial = new SkuMaterial(id, sku, material, quantity, created, lastUpdated, version);

        final String json = "{\"id\":1,\"quantity\":{\"symbol\":\"hl\",\"value\":100},\"createdAt\":\"2019-01-02T03:04:00\",\"lastUpdated\":\"2020-01-02T03:04:00\",\"version\":1}";
        JSONAssert.assertEquals(json, skuMaterial.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
