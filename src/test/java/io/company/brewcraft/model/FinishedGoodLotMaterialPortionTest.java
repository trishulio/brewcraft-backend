package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import javax.measure.Quantity;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import io.company.brewcraft.service.exception.IncompatibleQuantityUnitException;
import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class FinishedGoodLotMaterialPortionTest {

    private FinishedGoodLotMaterialPortion materialPortion;

    @BeforeEach
    public void init() {
        materialPortion = new FinishedGoodLotMaterialPortion();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        MaterialLot mixture = new MaterialLot(2L);
        Quantity<?> quantity = Quantities.getQuantity(100.0, SupportedUnits.GRAM);
        FinishedGoodLot finishedGood = new FinishedGoodLot(3L);
        LocalDateTime addedAt = LocalDateTime.of(2018, 1, 2, 3, 4);
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        FinishedGoodLotMaterialPortion materialPortion = new FinishedGoodLotMaterialPortion(id, mixture, quantity, finishedGood, addedAt, created, lastUpdated, version);

        assertEquals(1L, materialPortion.getId());
        assertEquals(new MaterialLot(2L), materialPortion.getMaterialLot());
        assertEquals(Quantities.getQuantity(100.0, SupportedUnits.GRAM), materialPortion.getQuantity());
        assertEquals(new FinishedGoodLot(3L), materialPortion.getFinishedGoodLot());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), materialPortion.getAddedAt());
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), materialPortion.getCreatedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), materialPortion.getLastUpdated());
        assertEquals(1, materialPortion.getVersion());
    }

    @Test
    public void testGetSetId() {
        materialPortion.setId(1L);
        assertEquals(1L, materialPortion.getId());
    }

    @Test
    public void testGetSetMaterialLot() {
        materialPortion.setMaterialLot(new MaterialLot(3L));
        assertEquals(new MaterialLot(3L), materialPortion.getMaterialLot());
    }

    @Test
    public void testSetMaterialLot_ThrowsException_WhenMaterialLotQuantityIsIncompatibleWithPortionQuantity() {
        MaterialLot materialLot = new MaterialLot(2L);
        materialLot.setQuantity(Quantities.getQuantity("10 l"));

        materialPortion.setQuantity(Quantities.getQuantity("10 kg"));
        assertThrows(IncompatibleQuantityUnitException.class, () -> materialPortion.setMaterialLot(materialLot));
    }

    @Test
    public void testGetSetQuantity() {
        materialPortion.setQuantity(Quantities.getQuantity(100.0, SupportedUnits.GRAM));
        assertEquals(Quantities.getQuantity(100.0, SupportedUnits.GRAM), materialPortion.getQuantity());
    }

    @Test
    public void testSetQuantity_ThrowsException_WhenMaterialLotQuantityIsIncompatibleWithPortionQuantity() {
        MaterialLot materialLot = new MaterialLot(2L);
        materialLot.setQuantity(Quantities.getQuantity("10 kg"));
        materialPortion.setMaterialLot(materialLot);

        assertThrows(IncompatibleQuantityUnitException.class, () -> materialPortion.setQuantity(Quantities.getQuantity("10 l")));
    }

    @Test
    public void testGetSetFinishedGood() {
        materialPortion.setFinishedGoodLot(new FinishedGoodLot(5L));
        assertEquals(new FinishedGoodLot(5L), materialPortion.getFinishedGoodLot());
    }

    @Test
    public void testGetSetAddedAt() {
        LocalDateTime addedAt = LocalDateTime.of(2018, 1, 2, 3, 4);
        materialPortion.setAddedAt(addedAt);
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), materialPortion.getAddedAt());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        materialPortion.setCreatedAt(created);
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), materialPortion.getCreatedAt());
    }

    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        materialPortion.setLastUpdated(lastUpdated);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), materialPortion.getLastUpdated());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        materialPortion.setVersion(version);
        assertEquals(version, materialPortion.getVersion());
    }

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        Long id = 1L;
        MaterialLot mixture = new MaterialLot(2L);
        Quantity<?> quantity = Quantities.getQuantity(100.0, SupportedUnits.GRAM);
        FinishedGoodLot finishedGood = new FinishedGoodLot(3L);
        LocalDateTime addedAt = LocalDateTime.of(2018, 1, 2, 3, 4);
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        FinishedGoodLotMaterialPortion materialPortion = new FinishedGoodLotMaterialPortion(id, mixture, quantity, finishedGood, addedAt, created, lastUpdated, version);

        final String json = "{\"id\":1,\"materialLot\":{\"id\":2,\"index\":null,\"lotNumber\":null,\"quantity\":null,\"invoiceItem\":null,\"storage\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"quantity\":{\"symbol\":\"g\",\"value\":100},\"addedAt\":\"2018-01-02T03:04:00\",\"createdAt\":\"2019-01-02T03:04:00\",\"lastUpdated\":\"2020-01-02T03:04:00\",\"version\":1}";
        JSONAssert.assertEquals(json, materialPortion.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
