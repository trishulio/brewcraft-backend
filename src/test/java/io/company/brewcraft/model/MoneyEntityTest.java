package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MoneyEntityTest {

    private MoneyEntity money;

    @BeforeEach
    public void init() {
        money = new MoneyEntity();
    }

    @Test
    public void testIdArgConstructor_SetsId() {
        money = new MoneyEntity(1L);
        assertEquals(1L, money.getId());
    }

    @Test
    public void testAllArgsConstructor() {
        money = new MoneyEntity(1L, new Currency(123, "CAD"), new BigDecimal("100"));
        assertEquals(1L, money.getId());
        assertEquals(new Currency(123, "CAD"), money.getCurrency());
        assertEquals(new BigDecimal("100"), money.getAmount());
    }

    @Test
    public void testAccessId() {
        assertNull(money.getId());
        money.setId(1L);
        assertEquals(1L, money.getId());
    }

    @Test
    public void testAccessAmount() {
        assertNull(money.getAmount());
        money.setAmount(new BigDecimal("100"));
        assertEquals(new BigDecimal("100"), money.getAmount());
    }

    @Test
    public void testAccessCurrency() {
        assertNull(money.getCurrency());
        money.setCurrency(new Currency(123, "CAD"));
        assertEquals(new Currency(123, "CAD"), money.getCurrency());
    }
}
