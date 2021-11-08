package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FinishedGoodInventoryTest {

    private FinishedGoodInventory finishedGoodInventory;

    @BeforeEach
    public void init() {
        finishedGoodInventory = new FinishedGoodInventory();
    }

    @Test
    public void testConstructor() {
        Long id = 9L;
        Sku sku = new Sku(1L);
        Long quantity = 50L;

        FinishedGoodInventory finishedGoodInventory = new FinishedGoodInventory(id, sku, quantity);

        assertEquals(9L, finishedGoodInventory.getId());
        assertEquals(new Sku(1L), finishedGoodInventory.getSku());
        assertEquals(50L, finishedGoodInventory.getQuantity());
    }

    @Test
    public void testGetSetId() {
        finishedGoodInventory.setId(9L);;
        assertEquals(9L, finishedGoodInventory.getId());
    }

    @Test
    public void testGetSetSku() {
        finishedGoodInventory.setSku(new Sku(1L));
        assertEquals(new Sku(1L), finishedGoodInventory.getSku());
    }

    @Test
    public void testGetSetQuantity() {
        finishedGoodInventory.setQuantity(50L);
        assertEquals(50L, finishedGoodInventory.getQuantity());
    }
}
