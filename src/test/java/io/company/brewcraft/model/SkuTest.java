package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import javax.measure.Quantity;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class SkuTest {

    private Sku sku;

    @BeforeEach
    public void init() {
        sku = new Sku();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        String number = "1101094";
        String name = "testName";
        String description = "testDescription";
        Product product = new Product(2L);
        List<SkuMaterial> materials = List.of(new SkuMaterial(9L));
        Quantity<?> quantity = Quantities.getQuantity(100.0, SupportedUnits.HECTOLITRE);
        boolean isPackageable = true;
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        Sku sku = new Sku(id, number, name, description, product, materials, quantity, isPackageable, created, lastUpdated, version);

        assertEquals(1L, sku.getId());
        assertEquals("testName", sku.getName());
        assertEquals("testDescription", sku.getDescription());

        SkuMaterial expectedSkuMaterial = new SkuMaterial(9L);
        expectedSkuMaterial.setSku(sku);
        assertEquals(List.of(expectedSkuMaterial), sku.getMaterials());

        assertEquals(Quantities.getQuantity(100.0, SupportedUnits.HECTOLITRE), sku.getQuantity());
        assertEquals(true, sku.getIsPackageable());
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), sku.getCreatedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), sku.getLastUpdated());
        assertEquals(1, sku.getVersion());
    }

    @Test
    public void testGetSetId() {
        sku.setId(1L);
        assertEquals(1L, sku.getId());
    }

    @Test
    public void testGetSetNumber() {
        sku.setNumber("1101094");
        assertEquals("1101094", sku.getNumber());
    }

    @Test
    public void testGetSetName() {
        sku.setName("testName");
        assertEquals("testName", sku.getName());
    }

    @Test
    public void testGetSetDescription() {
        sku.setDescription("testDescription");
        assertEquals("testDescription", sku.getDescription());
    }

    @Test
    public void testGetSetProduct() {
        sku.setProduct(new Product(3L));
        assertEquals(new Product(3L), sku.getProduct());
    }

    @Test
    public void testGetSetMaterials() {
        sku.setMaterials(List.of(new SkuMaterial(9L)));

        SkuMaterial expectedSkuMaterial = new SkuMaterial(9L);
        expectedSkuMaterial.setSku(sku);
        assertEquals(List.of(expectedSkuMaterial), sku.getMaterials());
    }

    @Test
    public void testGetSetQuantity() {
        sku.setQuantity(Quantities.getQuantity(100.0, SupportedUnits.HECTOLITRE));
        assertEquals(Quantities.getQuantity(100.0, SupportedUnits.HECTOLITRE), sku.getQuantity());
    }

    @Test
    public void testGetSetIsPackageable() {
        sku.setIsPackageable(true);
        assertTrue(sku.getIsPackageable());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        sku.setCreatedAt(created);
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), sku.getCreatedAt());
    }

    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        sku.setLastUpdated(lastUpdated);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), sku.getLastUpdated());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        sku.setVersion(version);
        assertEquals(version, sku.getVersion());
    }

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        Long id = 1L;
        String number = "1101094";
        String name = "testName";
        String description = "testDescription";
        Product product = new Product(2L);
        List<SkuMaterial> materials = List.of(new SkuMaterial(9L));
        Quantity<?> quantity = Quantities.getQuantity(100.0, SupportedUnits.HECTOLITRE);
        boolean isPackageable = true;
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        Sku sku = new Sku(id, number, name, description, product, materials, quantity, isPackageable, created, lastUpdated, version);

        final String json = "{\"id\":1,\"number\":\"1101094\",\"name\":\"testName\",\"description\":\"testDescription\",\"product\":{\"id\":2,\"name\":null,\"description\":null,\"category\":null,\"targetMeasures\":null,\"imageSrc\":null,\"createdAt\":null,\"lastUpdated\":null,\"deletedAt\":null,\"version\":null},\"materials\":[{\"id\":9,\"quantity\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null}],\"quantity\":{\"symbol\":\"hl\",\"value\":100},\"isPackageable\":true,\"createdAt\":\"2019-01-02T03:04:00\",\"lastUpdated\":\"2020-01-02T03:04:00\",\"version\":1}";
        JSONAssert.assertEquals(json, sku.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
