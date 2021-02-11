package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FreightEntityTest {

    private FreightEntity freight;

    @BeforeEach
    public void init() {
        freight = new FreightEntity();
    }

    @Test
    public void testIdArgConstructor() {
        freight = new FreightEntity(1L);
        assertEquals(1L, freight.getId());
    }

    @Test
    public void testAllArgConstructor() {
        freight = new FreightEntity(2L, new MoneyEntity(3L));
        assertEquals(2L, freight.getId());
        assertEquals(new MoneyEntity(3L), freight.getAmount());
    }

    @Test
    public void testAccessId() {
        assertNull(freight.getId());
        freight.setId(2L);
        assertEquals(2L, freight.getId());
    }

    @Test
    public void testAccessAmount() {
        assertNull(freight.getAmount());
        freight.setAmount(new MoneyEntity(3L));
        assertEquals(new MoneyEntity(3L), freight.getAmount());
    }
}
