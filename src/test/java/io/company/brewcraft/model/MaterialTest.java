package io.company.brewcraft.model;

import java.time.LocalDateTime;

import javax.measure.Unit;

import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.utils.SupportedUnits;

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
        LocalDateTime created = LocalDateTime.of(2020, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        Material materialEntity = new Material(id, name, description, category, upc, baseQuantityUnit, created, lastUpdated, version);
        
        assertSame(id, materialEntity.getId());
        assertSame(name, materialEntity.getName());
        assertSame(description, materialEntity.getDescription());
        assertSame(category, materialEntity.getCategory());
        assertSame(upc, materialEntity.getUPC());
        assertSame(baseQuantityUnit, materialEntity.getBaseQuantityUnit());
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
}