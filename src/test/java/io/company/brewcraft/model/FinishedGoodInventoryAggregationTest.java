package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.measure.Quantity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class FinishedGoodInventoryAggregationTest {
    private FinishedGoodInventoryAggregation finishedGoodInventory;

    @BeforeEach
    public void init() {
        finishedGoodInventory = new FinishedGoodInventoryAggregation();
    }

    @Test
    public void testNoArgConstructor() {
        FinishedGoodInventoryAggregation finishedGoodInventory = new FinishedGoodInventoryAggregation();

        assertNull(finishedGoodInventory.getId());
        assertNull(finishedGoodInventory.getSku());
        assertNull(finishedGoodInventory.getQuantity());
        assertNull(finishedGoodInventory.getPackagedOn());
    }

    @Test
    public void testIdConstructor() {
        Long id = 9L;

        FinishedGoodInventoryAggregation finishedGoodInventory = new FinishedGoodInventoryAggregation(id);

        assertEquals(9L, finishedGoodInventory.getId());
        assertNull(finishedGoodInventory.getSku());
        assertNull(finishedGoodInventory.getQuantity());
        assertNull(finishedGoodInventory.getPackagedOn());
    }

    @Test
    public void testConstructor() {
        Long id = 9L;
        Sku sku = new Sku(2L);
        Quantity<?> quantity = Quantities.getQuantity(100.0, SupportedUnits.EACH);
        LocalDateTime packagedOn = LocalDateTime.of(1995, 1, 1, 1, 1);

        FinishedGoodInventoryAggregation finishedGoodInventory = new FinishedGoodInventoryAggregation(id, sku, packagedOn, quantity);

        assertEquals(9L, finishedGoodInventory.getId());
        assertEquals(new Sku(2L), finishedGoodInventory.getSku());
        assertEquals(Quantities.getQuantity(100.0, SupportedUnits.EACH), finishedGoodInventory.getQuantity());
        assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGoodInventory.getPackagedOn());
    }

    @Test
    public void testConstructorAll() {
        Long id = 9L;
        Sku sku = new Sku(2L);
        UnitEntity unit = new UnitEntity("each");
        BigDecimal value = new BigDecimal(100.0);
        LocalDateTime packagedOn = LocalDateTime.of(1995, 1, 1, 1, 1);

        FinishedGoodInventoryAggregation finishedGoodInventory = new FinishedGoodInventoryAggregation(id, sku, packagedOn, unit, value);

        assertEquals(9L, finishedGoodInventory.getId());
        assertEquals(new Sku(2L), finishedGoodInventory.getSku());
        assertEquals(Quantities.getQuantity(100.0, SupportedUnits.EACH), finishedGoodInventory.getQuantity());
        assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGoodInventory.getPackagedOn());
    }

    @Test
    public void testSkuConstructor() {
        Sku sku = new Sku(2L);

        FinishedGoodInventoryAggregation finishedGoodInventory = new FinishedGoodInventoryAggregation(sku);

        assertNull(finishedGoodInventory.getId());
        assertEquals(new Sku(2L), finishedGoodInventory.getSku());
        assertNull(finishedGoodInventory.getQuantity());
        assertNull(finishedGoodInventory.getPackagedOn());
    }

    @Test
    public void testSkuQuantityConstructor() {
        Sku sku = new Sku(2L);
        UnitEntity unit = new UnitEntity("each");
        BigDecimal value = new BigDecimal(100.0);

        FinishedGoodInventoryAggregation finishedGoodInventory = new FinishedGoodInventoryAggregation(sku, unit, value);

        assertNull(finishedGoodInventory.getId());
        assertEquals(new Sku(2L), finishedGoodInventory.getSku());
        assertEquals(Quantities.getQuantity(100.0, SupportedUnits.EACH), finishedGoodInventory.getQuantity());
        assertNull(finishedGoodInventory.getPackagedOn());
    }

    @Test
    public void testGetSetId() {
        finishedGoodInventory.setId(9L);
        assertEquals(9L, finishedGoodInventory.getId());
    }

    @Test
    public void testGetSetSku() {
        finishedGoodInventory.setSku(new Sku(1L));
        assertEquals(new Sku(1L), finishedGoodInventory.getSku());
    }

    @Test
    public void testGetSetQuantity() {
        finishedGoodInventory.setQuantity(Quantities.getQuantity(100.0, SupportedUnits.EACH));
        assertEquals(Quantities.getQuantity(100.0, SupportedUnits.EACH), finishedGoodInventory.getQuantity());
    }

    @Test
    public void testGetSetPackagedOn() {
        finishedGoodInventory.setPackagedOn(LocalDateTime.of(1995, 1, 1, 1, 1));
        assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGoodInventory.getPackagedOn());
    }
}
