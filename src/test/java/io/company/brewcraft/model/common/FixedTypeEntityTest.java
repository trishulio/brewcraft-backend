package io.company.brewcraft.model.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FixedTypeEntityTest {

    @Test
    public void testSetGetId() {
        final Long id = 1L;
        final FixedTypeEntity fixedTypeEntity = new FixedTypeEntity();
        fixedTypeEntity.setId(id);
        assertEquals(id, fixedTypeEntity.getId());
    }

    @Test
    public void testSetGetName() {
        final String name = "name";
        final FixedTypeEntity fixedTypeEntity = new FixedTypeEntity();
        fixedTypeEntity.setName(name);
        assertEquals(name, fixedTypeEntity.getName());
    }
}