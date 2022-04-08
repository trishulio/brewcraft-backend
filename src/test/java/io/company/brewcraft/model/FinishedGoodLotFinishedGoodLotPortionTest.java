package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import javax.measure.Quantity;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class FinishedGoodLotFinishedGoodLotPortionTest {
    private FinishedGoodLotFinishedGoodLotPortion finishedGoodLotPortion;

    @BeforeEach
    public void init() {
        finishedGoodLotPortion = new FinishedGoodLotFinishedGoodLotPortion();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        FinishedGoodLot finishedGoodLotTarget = new FinishedGoodLot(2L);
        Quantity<?> quantity = Quantities.getQuantity(100.0, SupportedUnits.GRAM);
        FinishedGoodLot finishedGoodLot = new FinishedGoodLot(3L);
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        FinishedGoodLotFinishedGoodLotPortion finishedGoodLotPortion = new FinishedGoodLotFinishedGoodLotPortion(id, finishedGoodLot, quantity, finishedGoodLotTarget, created, lastUpdated, version);

        assertEquals(1L, finishedGoodLotPortion.getId());
        assertEquals(new FinishedGoodLot(2L), finishedGoodLotPortion.getFinishedGoodLotTarget());
        assertEquals(Quantities.getQuantity(100.0, SupportedUnits.GRAM), finishedGoodLotPortion.getQuantity());
        assertEquals(new FinishedGoodLot(3L), finishedGoodLotPortion.getFinishedGoodLot());
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), finishedGoodLotPortion.getCreatedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), finishedGoodLotPortion.getLastUpdated());
        assertEquals(1, finishedGoodLotPortion.getVersion());
    }

    @Test
    public void testGetSetId() {
        finishedGoodLotPortion.setId(1L);
        assertEquals(1L, finishedGoodLotPortion.getId());
    }

    @Test
    public void testGetSetFinishedGoodLotTarget() {
        finishedGoodLotPortion.setFinishedGoodLotTarget(new FinishedGoodLot(3L));
        assertEquals(new FinishedGoodLot(3L), finishedGoodLotPortion.getFinishedGoodLotTarget());
    }

    @Test
    public void testGetSetQuantity() {
        finishedGoodLotPortion.setQuantity(Quantities.getQuantity(100.0, SupportedUnits.GRAM));
        assertEquals(Quantities.getQuantity(100.0, SupportedUnits.GRAM), finishedGoodLotPortion.getQuantity());
    }

    @Test
    public void testGetSetFinishedGood() {
        finishedGoodLotPortion.setFinishedGoodLot(new FinishedGoodLot(5L));
        assertEquals(new FinishedGoodLot(5L), finishedGoodLotPortion.getFinishedGoodLot());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        finishedGoodLotPortion.setCreatedAt(created);
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), finishedGoodLotPortion.getCreatedAt());
    }

    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        finishedGoodLotPortion.setLastUpdated(lastUpdated);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), finishedGoodLotPortion.getLastUpdated());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        finishedGoodLotPortion.setVersion(version);
        assertEquals(version, finishedGoodLotPortion.getVersion());
    }

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        Long id = 1L;
        FinishedGoodLot finishedGoodLotTarget = new FinishedGoodLot(2L);
        Quantity<?> quantity = Quantities.getQuantity(100.0, SupportedUnits.GRAM);
        FinishedGoodLot finishedGoodLot = new FinishedGoodLot(3L);
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        FinishedGoodLotFinishedGoodLotPortion finishedGoodLotPortion = new FinishedGoodLotFinishedGoodLotPortion(id, finishedGoodLot, quantity, finishedGoodLotTarget, created, lastUpdated, version);

        final String json = "{\"id\":1,\"quantity\":{\"symbol\":\"g\",\"value\":100},\"createdAt\":\"2019-01-02T03:04:00\",\"lastUpdated\":\"2020-01-02T03:04:00\",\"version\":1}";
        JSONAssert.assertEquals(json, finishedGoodLotPortion.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
