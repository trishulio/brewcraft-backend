package io.company.brewcraft.model;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaxRateTest {
    private TaxRate taxRate;

    @BeforeEach
    public void init() {
        taxRate = new TaxRate();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(taxRate.getValue());
    }

    @Test
    public void testAllArgConstructor() {
        taxRate = new TaxRate(new BigDecimal("1"));

        assertEquals(new BigDecimal("1"), taxRate.getValue());
    }

    @Test
    public void testGetTaxAmount_ReturnsNull_WhenArgIsNull() {
        taxRate.setValue(new BigDecimal("2"));

        assertNull(taxRate.getTaxAmount(null));
    }

    @Test
    public void testGetTaxAmount_ReturnsTaxAmount_WhenArgIsNotNull() {
        taxRate.setValue(new BigDecimal("2"));

        assertEquals(Money.parse("CAD 200"), taxRate.getTaxAmount(Money.parse("CAD 100")));
    }
}
