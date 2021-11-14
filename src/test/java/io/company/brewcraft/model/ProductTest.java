package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class ProductTest {

    private Product product;

    @BeforeEach
    public void init() {
        product = new Product();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        String name = "testName";
        String description = "testDesc";
        ProductCategory category = new ProductCategory();
        List<ProductMeasureValue> targetMeasures = List.of();
        String imageSrc = "http://www.test.com";
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime deletedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        Product product = new Product(id, name, description, category, targetMeasures, imageSrc, created, lastUpdated, deletedAt, version);

        assertEquals(1L, product.getId());
        assertEquals("testName", product.getName());
        assertEquals("testDesc", product.getDescription());
        assertEquals(new ProductCategory(), product.getCategory());
        assertEquals(List.of(), product.getTargetMeasures());
        assertEquals(imageSrc, product.getImageSrc());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), product.getCreatedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), product.getLastUpdated());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), product.getDeletedAt());
        assertEquals(1, product.getVersion());
    }

    @Test
    public void testGetSetId() {
        Long id = 1L;
        product.setId(id);
        assertEquals(1L, product.getId());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        product.setName(name);
        assertEquals("testName", product.getName());
    }

    @Test
    public void testGetSetDescription() {
        String description = "testDesc";
        product.setDescription(description);
        assertEquals("testDesc", product.getDescription());
    }

    @Test
    public void testGetSetCategory() {
        ProductCategory category = new ProductCategory();
        product.setCategory(category);
        assertEquals(new ProductCategory(), product.getCategory());
    }

    @Test
    public void testGetSetTargetMeasures() {
        List<ProductMeasureValue> targetMeasures = List.of();
        product.setTargetMeasures(targetMeasures);
        assertEquals(List.of(), product.getTargetMeasures());
    }
    
    @Test
    public void testGetSetImageSrc() {
        product.setImageSrc("http://www.test.com");
        assertEquals("http://www.test.com", product.getImageSrc());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        product.setCreatedAt(created);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), product.getCreatedAt());
    }

    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        product.setLastUpdated(lastUpdated);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), product.getLastUpdated());
    }

    @Test
    public void testGetSetDeletedAt() {
        LocalDateTime deletedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        product.setDeletedAt(deletedAt);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), product.getDeletedAt());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        product.setVersion(version);
        assertEquals(version, product.getVersion());
    }

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        Long id = 1L;
        String name = "testName";
        String description = "testDesc";
        ProductCategory category = new ProductCategory();
        List<ProductMeasureValue> targetMeasures = List.of();
        String imageSrc = "http://www.test.com";
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime deletedAt = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        Product product = new Product(id, name, description, category, targetMeasures, imageSrc, created, lastUpdated, deletedAt, version);

        final String json = "{\"id\":1,\"name\":\"testName\",\"description\":\"testDesc\",\"category\":{\"id\":null,\"name\":null,\"parentCategory\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"targetMeasures\":[],\"createdAt\":\"2020-01-02T03:04:00\",\"lastUpdated\":\"2020-01-02T03:04:00\",\"deletedAt\":\"2020-01-02T03:04:00\",\"version\":1}";
        JSONAssert.assertEquals(json, product.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
