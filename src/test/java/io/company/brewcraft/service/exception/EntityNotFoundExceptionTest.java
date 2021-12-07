package io.company.brewcraft.service.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class EntityNotFoundExceptionTest {

    @Test
    public void testEntityNotFoundException() {
        EntityNotFoundException exception = new EntityNotFoundException("EntityTest", "idTest");
        assertEquals("EntityTest not found with id: idTest", exception.getMessage());
    }
}
