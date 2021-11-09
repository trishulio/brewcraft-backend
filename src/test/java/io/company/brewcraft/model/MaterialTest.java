package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import javax.measure.Unit;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import io.company.brewcraft.util.SupportedUnits;

public class MaterialTest {

    private Material materialEntity;

    @BeforeEach
    public void init() {
        materialEntity = new Material();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        String name = "testName";
        String description = "testDesc";
        MaterialCategory category = new MaterialCategory();
        String upc = "testUPC";
        Unit<?> baseQuantityUnit = SupportedUnits.KILOGRAM;
        String imageSrc = "http://www.test.com";
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        Material materialEntity = new Material(id, name, description, category, upc, baseQuantityUnit, imageSrc, created, lastUpdated, version);

        assertSame(id, materialEntity.getId());
        assertSame(name, materialEntity.getName());
        assertSame(description, materialEntity.getDescription());
        assertSame(category, materialEntity.getCategory());
        assertSame(upc, materialEntity.getUPC());
        assertSame(baseQuantityUnit, materialEntity.getBaseQuantityUnit());
        assertSame(imageSrc, materialEntity.getImageSrc());
        assertSame(created, materialEntity.getCreatedAt());
        assertSame(lastUpdated, materialEntity.getLastUpdated());
        assertSame(version, materialEntity.getVersion());
    }

    @Test
    public void testGetSetId() {
        Long id = 1L;
        materialEntity.setId(id);
        assertSame(id, materialEntity.getId());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        materialEntity.setName(name);
        assertSame(name, materialEntity.getName());
    }

    @Test
    public void testGetSetDescription() {
        String description = "testDesc";
        materialEntity.setDescription(description);
        assertSame(description, materialEntity.getDescription());
    }

    @Test
    public void testGetSetCategory() {
        MaterialCategory category = new MaterialCategory();
        materialEntity.setCategory(category);
        assertSame(category, materialEntity.getCategory());
    }

    @Test
    public void testGetSetUPC() {
        String upc = "testUpc";
        materialEntity.setUPC(upc);
        assertSame(upc, materialEntity.getUPC());
    }

    @Test
    public void testGetSetBaseQuantityUnit() {
        Unit<?> baseQuantityUnit = SupportedUnits.KILOGRAM;
        materialEntity.setBaseQuantityUnit(baseQuantityUnit);
        assertSame(baseQuantityUnit, materialEntity.getBaseQuantityUnit());
    }

    @Test
    public void testGetSetImageSrc() {
        materialEntity.setImageSrc("http://www.test.com");
        assertSame("http://www.test.com", materialEntity.getImageSrc());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.now();
        materialEntity.setCreatedAt(created);
        assertSame(created, materialEntity.getCreatedAt());
    }

    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.now();
        materialEntity.setLastUpdated(lastUpdated);
        assertSame(lastUpdated, materialEntity.getLastUpdated());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        materialEntity.setVersion(version);
        assertSame(version, materialEntity.getVersion());
    }

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        Long id = 1L;
        String name = "testName";
        String description = "testDesc";
        MaterialCategory category = new MaterialCategory();
        String upc = "testUPC";
        Unit<?> baseQuantityUnit = SupportedUnits.KILOGRAM;
        String imageSrc = "http://www.test.com";
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        Material material = new Material(id, name, description, category, upc, baseQuantityUnit, imageSrc, created, lastUpdated, version);

        final String json = "{\"id\":1,\"name\":\"testName\",\"description\":\"testDesc\",\"category\":{\"id\":null,\"name\":null,\"parentCategory\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"upc\":\"testUPC\",\"baseQuantityUnit\":{\"symbol\":\"kg\"},\"imageSrc\":\"http://www.test.com\",\"createdAt\":\"2020-01-02T03:04:00\",\"lastUpdated\":\"2020-01-02T03:04:00\",\"version\":1}";
        JSONAssert.assertEquals(json, material.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    public void testDeepClone_ReturnsACopyOfObject() {
        Long id = 1L;
        String name = "testName";
        String description = "testDesc";
        MaterialCategory category = new MaterialCategory();
        String upc = "testUPC";
        Unit<?> baseQuantityUnit = SupportedUnits.KILOGRAM;
        String imageSrc = "http://www.test.com";
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        Material material = new Material(id, name, description, category, upc, baseQuantityUnit, imageSrc, created, lastUpdated, version);

        Material copy = material.deepClone();

        assertEquals(material, copy);
    }
}