package io.company.brewcraft.pojo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Tax;
import io.company.brewcraft.model.TaxRate;

public class TaxTest {
    private Tax tax;

    @BeforeEach
    public void init() {
        tax = new Tax();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(tax.getPstRate());
        assertNull(tax.getGstRate());
        assertNull(tax.getHstRate());
    }

    @Test
    public void testHstConstructor() {
        tax = new Tax(new TaxRate(new BigDecimal("1")));

        assertEquals(new TaxRate(new BigDecimal("1")), tax.getHstRate());
        assertNull(tax.getPstRate());
        assertNull(tax.getGstRate());
    }

    @Test
    public void testGstPstConstructor() {
        tax = new Tax(new TaxRate(new BigDecimal("1")), new TaxRate(new BigDecimal("2")));

        assertEquals(new TaxRate(new BigDecimal("1")), tax.getPstRate());
        assertEquals(new TaxRate(new BigDecimal("2")), tax.getGstRate());
        assertNull(tax.getHstRate());
    }
}
