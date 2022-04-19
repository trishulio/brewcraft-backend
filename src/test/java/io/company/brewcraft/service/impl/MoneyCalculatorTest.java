package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.money.CurrencyMismatchException;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.service.MoneyCalculator;
import io.company.brewcraft.service.MoneySupplier;

public class MoneyCalculatorTest {
    private MoneyCalculator calculator;

    @BeforeEach
    public void init() {
        calculator = MoneyCalculator.INSTANCE;
    }

    @Test
    public void testTotal_ReturnsNull_WhenCollectionIsNull() {
        Money total = calculator.total(null);
        assertNull(total);
    }

    @Test
    public void testTotal_ReturnsNull_WhenCollectionIsEmpty() {
        Money total = calculator.total(Collections.emptyList());
        assertNull(total);

    }

    @Test
    public void testTotal_IgnoresNullSuppliersInCollection() {
        List<MoneySupplier> suppliers = new ArrayList<>();
        suppliers.add(() -> Money.parse("CAD 10"));
        suppliers.add(null);

        Money total = calculator.total(suppliers);
        assertEquals(Money.parse("CAD 10"), total);
    }

    @Test
    public void testTotal_IgnoresSupplierInCollectionWithNullAmounts() {
        List<MoneySupplier> suppliers = List.of(
            () -> null,
            () -> Money.parse("CAD 10"),
            () -> null
        );

        Money total = calculator.total(suppliers);
        assertEquals(Money.parse("CAD 10"), total);
    }

    @Test
    public void testTotal_ReturnsTotalOfTheSameCurrency() {
        List<MoneySupplier> suppliers = List.of(
            () -> Money.parse("CAD 10"),
            () -> Money.parse("CAD 20"),
            () -> Money.parse("CAD 30")
        );

        Money total = calculator.total(suppliers);
        assertEquals(Money.parse("CAD 60"), total);
    }

    @Test
    public void testTotal_ThrowsCurrencyMistMatchException_WhenDifferentCurrenciesAreSupplied() {
        List<MoneySupplier> suppliers = List.of(
            () -> Money.parse("CAD 10"),
            () -> Money.parse("USD 20"),
            () -> Money.parse("INR 30")
        );

        assertThrows(CurrencyMismatchException.class, () -> calculator.total(suppliers));
    }

    @Test
    public void todo() {
        fail();
    }
}
