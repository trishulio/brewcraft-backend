package io.company.brewcraft.service.exception;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IncompatibleQuantityUnitExceptionTest {

    private Exception ex;

    @BeforeEach
    public void init() {
        ex = new IncompatibleQuantityUnitException();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(ex.getMessage());
        assertNull(ex.getCause());
    }

    @Test
    public void testConstructor_String_SetsMessage() {
        ex = new IncompatibleQuantityUnitException("Message");

        assertEquals("Message", ex.getMessage());
    }
}
