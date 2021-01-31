package io.company.brewcraft.pojo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MaterialTest {

    private Material material;

    @BeforeEach
    public void init() {
        material = new Material();
    }

    @Test
    public void testAllArgsConstructor() {
        material = new Material(1L);

        assertEquals(1L, material.getId());
    }

    @Test
    public void testAccessId() {
        assertNull(material.getId());
        material.setId(1L);
        assertEquals(1L, material.getId());
    }
}
