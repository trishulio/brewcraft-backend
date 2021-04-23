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
    public void testAllArgsConstructor() {
        money = new MoneyEntity(new Currency(123, "CAD"), new BigDecimal("100"));
        assertEquals(new Currency(123, "CAD"), money.getCurrency());
        assertEquals(new BigDecimal("100"), money.getAmount());
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
