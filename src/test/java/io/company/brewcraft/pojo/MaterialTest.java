package io.company.brewcraft.pojo;

import java.time.LocalDateTime;

import javax.measure.Unit;
import javax.measure.quantity.Mass;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.utils.SupportedUnits;

public class MaterialTest {

    private Material material;

    @BeforeEach
    public void init() {
        material = new Material();
    }
    
    @Test
    public void testConstructor() {
        Long id = 1L;
        String name = "testName";
        String description = "testDesc";
        Category category = new Category();
        String upc = "testUPC";
        Unit<Mass> baseQuantityUnit = SupportedUnits.KILOGRAM;
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        Material material = new Material(id, name, description, category, upc, baseQuantityUnit, created, lastUpdated, version);
        
        assertSame(id, material.getId());
        assertSame(name, material.getName());
        assertSame(description, material.getDescription());
        assertSame(category, material.getCategory());
        assertSame(upc, material.getUPC());
        assertSame(baseQuantityUnit, material.getBaseQuantityUnit());
        assertSame(created, material.getCreatedAt());
        assertSame(lastUpdated, material.getLastUpdated());
        assertSame(version, material.getVersion());        
    }
    
    @Test
    public void testGetSetId() {
        Long id = 1L;
        material.setId(id);
        assertSame(id, material.getId());
    }

    @Test
    public void testGetSetName() {
        String name = "testName";
        material.setName(name);
        assertSame(name, material.getName());
    }
    
    @Test
    public void testGetSetDescription() {
        String description = "testDesc";
        material.setDescription(description);
        assertSame(description, material.getDescription());
    }
    
    @Test
    public void testGetSetCategory() {
        Category category = new Category();
        material.setCategory(category);
        assertSame(category, material.getCategory());
    }
    
    @Test
    public void testGetSetUPC() {
        String upc = "testUpc";
        material.setUPC(upc);
        assertSame(upc, material.getUPC());
    }

    @Test
    public void testGetSetBaseQuantityUnit() {
        Unit<Mass> baseQuantityUnit = SupportedUnits.KILOGRAM;
        material.setBaseQuantityUnit(baseQuantityUnit);
        assertSame(baseQuantityUnit, material.getBaseQuantityUnit());
    }
    
    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.now();
        material.setCreatedAt(created);
        assertSame(created, material.getCreatedAt());
    }
    
    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.now();
        material.setLastUpdated(lastUpdated);
        assertSame(lastUpdated, material.getLastUpdated());
    }
    
    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        material.setVersion(version);
        assertSame(version, material.getVersion());
    }
}