package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MeasureTest {
    
    Measure measure;
    
    @BeforeEach
    public void init() {
        measure = new Measure();
    }
    
    @Test
    public void testConstructor() {
        measure = new Measure(1L, "abv");
        
        assertEquals(1L, measure.getId());
        assertEquals("abv", measure.getName());
    }
    
    @Test
    public void testGetSetId() {
        measure.setId(1L);
        assertEquals(1L, measure.getId());
    }
    
    @Test
    public void testGetSetName() {
        measure.setName("abv");
        assertEquals("abv", measure.getName());
    }

}
