package io.company.brewcraft.pojo;

import static org.junit.jupiter.api.Assertions.*;

import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FreightTest {

    private Freight freight;

    @BeforeEach
    public void init() {
        freight = new Freight();
    }

    @Test
    public void testAllArgConstructor() {
        freight = new Freight(1L, Money.parse("CAD 10"));
        assertEquals(1L, freight.getId());
        assertEquals(Money.parse("CAD 10"), freight.getAmount());
    }

    @Test
    public void testAccessAmount() {
        assertNull(freight.getAmount());
        freight.setAmount(Money.parse("CAD 10"));
        assertEquals(Money.parse("CAD 10"), freight.getAmount());
    }
}
