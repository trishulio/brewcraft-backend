package io.company.brewcraft.pojo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.Tax;

public class TaxTest {
    private Tax tax;

    @BeforeEach
    public void init() {
        tax = new Tax();
    }

    @Test
    public void testAllArgsConstructor() {
        tax = new Tax(Money.parse("CAD 2"));
        assertEquals(Money.parse("CAD 2"), tax.getAmount());
    }

    @Test
    public void testAccessAmount() {
        assertNull(tax.getAmount());
        tax.setAmount(Money.parse("CAD 2"));
        assertEquals(Money.parse("CAD 2"), tax.getAmount());
    }

    @Test
    public void testTotal_ReturnsNewTaxWithCombinedAmount() {
        List<Tax> taxes = List.of(
            new Tax(Money.parse("CAD 10")),
            new Tax(Money.parse("CAD 20")),
            new Tax(Money.parse("CAD 30"))
        );

        Tax total = Tax.total(taxes);

        assertEquals(new Tax(Money.parse("CAD 60")), total);
    }
}
