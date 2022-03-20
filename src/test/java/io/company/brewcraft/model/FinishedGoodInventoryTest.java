package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import javax.measure.Quantity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class FinishedGoodInventoryTest {

    private FinishedGoodInventory finishedGoodInventory;

    @BeforeEach
    public void init() {
        finishedGoodInventory = new FinishedGoodInventory();
    }

    @Test
    public void testConstructor() {
        Long id = 9L;
        Sku sku = new Sku(2L);
        List<FinishedGoodLotMixturePortion> mixturePortions = List.of(new FinishedGoodLotMixturePortion(5L));
        List<FinishedGoodLotMaterialPortion> materialPortions = List.of(new FinishedGoodLotMaterialPortion(6L));
        List<FinishedGoodLotFinishedGoodLotPortion> finishedGoodLotPortions = List.of(new FinishedGoodLotFinishedGoodLotPortion(10L));
        Quantity<?> quantity = Quantities.getQuantity(100.0, SupportedUnits.EACH);
        LocalDateTime packagedOn = LocalDateTime.of(1995, 1, 1, 1, 1);

        FinishedGoodInventory finishedGoodInventory = new FinishedGoodInventory(id, sku, mixturePortions, materialPortions, finishedGoodLotPortions, quantity, packagedOn);

        assertEquals(9L, finishedGoodInventory.getId());
        assertEquals(new Sku(2L), finishedGoodInventory.getSku());
        assertEquals(List.of(new FinishedGoodLotMixturePortion(5L)), finishedGoodInventory.getMixturePortions());
        assertEquals(List.of(new FinishedGoodLotMaterialPortion(6L)), finishedGoodInventory.getMaterialPortions());
        assertEquals(List.of(new FinishedGoodLotFinishedGoodLotPortion(10L)), finishedGoodInventory.getFinishedGoodLotPortions());
        assertEquals(Quantities.getQuantity(100.0, SupportedUnits.EACH), finishedGoodInventory.getQuantity());
        assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGoodInventory.getPackagedOn());
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
    public void testGetSetMixturePortions() {
        finishedGoodInventory.setMixturePortions(List.of(new FinishedGoodLotMixturePortion(5L)));
        assertEquals(List.of(new FinishedGoodLotMixturePortion(5L)), finishedGoodInventory.getMixturePortions());
    }

    @Test
    public void testGetSetMaterialPortions() {
        finishedGoodInventory.setFinishedGoodLotPortions(List.of(new FinishedGoodLotFinishedGoodLotPortion(10L)));
        assertEquals(List.of(new FinishedGoodLotFinishedGoodLotPortion(10L)), finishedGoodInventory.getFinishedGoodLotPortions());
    }

    @Test
    public void testGetSetFinishedGoodLotPortions() {
        finishedGoodInventory.setMaterialPortions(List.of(new FinishedGoodLotMaterialPortion(6L)));
        assertEquals(List.of(new FinishedGoodLotMaterialPortion(6L)), finishedGoodInventory.getMaterialPortions());
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
