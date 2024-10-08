package io.company.brewcraft.model;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TaxAmountTest {
    private TaxAmount taxAmount;

    @BeforeEach
    public void init() {
        taxAmount = new TaxAmount();
    }

    @Test
    public void testNoArgConstructor() {
        assertNull(taxAmount.getPstAmount());
        assertNull(taxAmount.getGstAmount());
        assertNull(taxAmount.getHstAmount());
    }

    @Test
    public void testAllArgConstructor() {
        taxAmount = new TaxAmount(Money.parse("CAD 10"), Money.parse("CAD 20"), Money.parse("CAD 30"));

        assertEquals(Money.parse("CAD 10"), taxAmount.getPstAmount());
        assertEquals(Money.parse("CAD 20"), taxAmount.getGstAmount());
        assertEquals(Money.parse("CAD 30"), taxAmount.getHstAmount());
        assertEquals(Money.parse("CAD 60"), taxAmount.getTotalTaxAmount());
    }

    @Test
    public void testGstPstConstructor() {
        taxAmount = new TaxAmount(Money.parse("CAD 10"), Money.parse("CAD 20"));

        assertEquals(Money.parse("CAD 10"), taxAmount.getPstAmount());
        assertEquals(Money.parse("CAD 20"), taxAmount.getGstAmount());
        assertEquals(Money.parse("CAD 30"), taxAmount.getTotalTaxAmount());
    }

    @Test
    public void testHstConstructor() {
        taxAmount = new TaxAmount(Money.parse("CAD 10"));

        assertEquals(Money.parse("CAD 10"), taxAmount.getHstAmount());
        assertEquals(Money.parse("CAD 10"), taxAmount.getTotalTaxAmount());
    }

    @Test
    public void testGetSetPstAmount() {
        taxAmount.setPstAmount(Money.parse("CAD 100"));

        assertEquals(Money.parse("CAD 100"), taxAmount.getPstAmount());
        assertEquals(Money.parse("CAD 100"), taxAmount.getTotalTaxAmount());
    }

    @Test
    public void testGetSetGstAmount() {
        taxAmount.setGstAmount(Money.parse("CAD 100"));

        assertEquals(Money.parse("CAD 100"), taxAmount.getGstAmount());
        assertEquals(Money.parse("CAD 100"), taxAmount.getTotalTaxAmount());
    }

    @Test
    public void testGetSetHstAmount() {
        taxAmount.setHstAmount(Money.parse("CAD 100"));

        assertEquals(Money.parse("CAD 100"), taxAmount.getHstAmount());
        assertEquals(Money.parse("CAD 100"), taxAmount.getTotalTaxAmount());
    }

    @Test
    public void testGetTotalTaxAmount_ReturnsTotalMoney() {
        taxAmount = new TaxAmount(Money.parse("CAD 10"), Money.parse("CAD 20"), Money.parse("CAD 30"));

        assertEquals(Money.parse("CAD 60"), taxAmount.getTotalTaxAmount());
    }
}
