package io.company.brewcraft.dto.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FixedTypeDtoTest {

    @Test
    public void testSetGetName() {
        final String name = "name";
        final FixedTypeDto fixedTypeDto = new FixedTypeDto();
        fixedTypeDto.setName(name);
        assertEquals(name, fixedTypeDto.getName());
    }
}