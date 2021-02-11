package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MaterialEntityTest {
    private MaterialEntity material;

    @BeforeEach
    public void init() {
        material = new MaterialEntity();
    }

    @Test
    public void testIdConstructor() {
        material = new MaterialEntity(1L);

        assertEquals(1L, material.getId());
    }

    @Test
    public void testAllArgsConstructor() {
        material = new MaterialEntity(1L);

        assertEquals(1L, material.getId());
    }

    @Test
    public void testAccessId() {
        assertNull(material.getId());
        material.setId(1L);
        assertEquals(1L, material.getId());
    }
}
