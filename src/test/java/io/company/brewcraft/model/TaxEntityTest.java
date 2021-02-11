package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaxEntityTest {

    private TaxEntity tax;

    @BeforeEach
    public void init() {
        tax = new TaxEntity();
    }

    @Test
    public void testIdArgConstructor_SetsId() {
        tax = new TaxEntity(1L);
        assertEquals(1L, tax.getId());
    }

    @Test
    public void testAllArgsConstructor() {
        tax = new TaxEntity(1L, new MoneyEntity(2L));
        assertEquals(1L, tax.getId());
        assertEquals(new MoneyEntity(2L), tax.getAmount());
    }

    @Test
    public void testAccessId() {
        assertNull(tax.getId());
        tax.setId(1L);
        assertEquals(1L, tax.getId());
    }

    @Test
    public void testAccessAmount() {
        assertNull(tax.getAmount());
        tax.setAmount(new MoneyEntity(1L));
        assertEquals(new MoneyEntity(1L), tax.getAmount());
    }
}
