package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import javax.measure.Quantity;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class FinishedGoodLotTest {

    private FinishedGoodLot finishedGoodLot;

    @BeforeEach
    public void init() {
        finishedGoodLot = new FinishedGoodLot();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        Sku sku = new Sku(2L);
        List<FinishedGoodLotMixturePortion> mixturePortions = List.of(new FinishedGoodLotMixturePortion(5L));
        List<FinishedGoodLotMaterialPortion> materialPortions = List.of(new FinishedGoodLotMaterialPortion(6L));
        List<FinishedGoodLotFinishedGoodLotPortion> finishedGoodLotPortions = List.of(new FinishedGoodLotFinishedGoodLotPortion(10L));
        Quantity<?> quantity = Quantities.getQuantity(100.0, SupportedUnits.EACH);
        LocalDateTime packagedOn = LocalDateTime.of(1995, 1, 1, 1, 1);
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        FinishedGoodLot finishedGoodLot = new FinishedGoodLot(id, sku, mixturePortions, materialPortions, finishedGoodLotPortions, quantity, packagedOn, created, lastUpdated, version);

        assertEquals(1L, finishedGoodLot.getId());
        assertEquals(new Sku(2L), finishedGoodLot.getSku());

        FinishedGoodLotMixturePortion expectedMixturePortion = new FinishedGoodLotMixturePortion(5L);
        expectedMixturePortion.setFinishedGoodLot(finishedGoodLot);
        assertEquals(List.of(expectedMixturePortion), finishedGoodLot.getMixturePortions());

        FinishedGoodLotMaterialPortion expectedMaterialPortion = new FinishedGoodLotMaterialPortion(6L);
        expectedMaterialPortion.setFinishedGoodLot(finishedGoodLot);
        assertEquals(List.of(expectedMaterialPortion), finishedGoodLot.getMaterialPortions());

        FinishedGoodLotFinishedGoodLotPortion expectedFinishedGootLotPortion = new FinishedGoodLotFinishedGoodLotPortion(10L);
        expectedFinishedGootLotPortion.setFinishedGoodLotTarget(finishedGoodLot);
        assertEquals(List.of(expectedFinishedGootLotPortion), finishedGoodLot.getFinishedGoodLotPortions());

        assertEquals(Quantities.getQuantity(100.0, SupportedUnits.EACH), finishedGoodLot.getQuantity());
        assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGoodLot.getPackagedOn());
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), finishedGoodLot.getCreatedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), finishedGoodLot.getLastUpdated());
        assertEquals(1, finishedGoodLot.getVersion());
    }

    @Test
    public void testGetSetId() {
        finishedGoodLot.setId(1L);
        assertEquals(1L, finishedGoodLot.getId());
    }

    @Test
    public void testGetSetSku() {
        finishedGoodLot.setSku(new Sku(3L));
        assertEquals(new Sku(3L), finishedGoodLot.getSku());
    }

    @Test
    public void testGetSetMixturePortions() {
        FinishedGoodLotMixturePortion expectedMixturePortion = new FinishedGoodLotMixturePortion(5L);
        expectedMixturePortion.setFinishedGoodLot(finishedGoodLot);

        finishedGoodLot.setMixturePortions(List.of(expectedMixturePortion));
        assertEquals(List.of(expectedMixturePortion), finishedGoodLot.getMixturePortions());
    }

    @Test
    public void testGetSetMaterialPortions() {
        FinishedGoodLotFinishedGoodLotPortion expectedFinishedGootLotPortion = new FinishedGoodLotFinishedGoodLotPortion(10L);
        expectedFinishedGootLotPortion.setFinishedGoodLot(finishedGoodLot);

        finishedGoodLot.setFinishedGoodLotPortions(List.of(expectedFinishedGootLotPortion));
        assertEquals(List.of(expectedFinishedGootLotPortion), finishedGoodLot.getFinishedGoodLotPortions());
    }

    @Test
    public void testGetSetFinishedGoodLotPortions() {
        FinishedGoodLotMaterialPortion expectedMaterialPortion = new FinishedGoodLotMaterialPortion(6L);
        expectedMaterialPortion.setFinishedGoodLot(finishedGoodLot);

        finishedGoodLot.setMaterialPortions(List.of(expectedMaterialPortion));
        assertEquals(List.of(expectedMaterialPortion), finishedGoodLot.getMaterialPortions());
    }

    @Test
    public void testGetSetQuantity() {
        finishedGoodLot.setQuantity(Quantities.getQuantity(100.0, SupportedUnits.EACH));
        assertEquals(Quantities.getQuantity(100.0, SupportedUnits.EACH), finishedGoodLot.getQuantity());
    }

    @Test
    public void testGetSetPackagedOn() {
        finishedGoodLot.setPackagedOn(LocalDateTime.of(1995, 1, 1, 1, 1));
        assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGoodLot.getPackagedOn());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        finishedGoodLot.setCreatedAt(created);
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), finishedGoodLot.getCreatedAt());
    }

    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        finishedGoodLot.setLastUpdated(lastUpdated);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), finishedGoodLot.getLastUpdated());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        finishedGoodLot.setVersion(version);
        assertEquals(version, finishedGoodLot.getVersion());
    }

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        Long id = 1L;
        Sku sku = new Sku(2L);
        List<FinishedGoodLotMixturePortion> mixturePortions = List.of(new FinishedGoodLotMixturePortion(5L));
        List<FinishedGoodLotMaterialPortion> materialPortions = List.of(new FinishedGoodLotMaterialPortion(6L));
        List<FinishedGoodLotFinishedGoodLotPortion> finishedGoodLotPortions = List.of(new FinishedGoodLotFinishedGoodLotPortion(7L));
        Quantity<?> quantity = Quantities.getQuantity(100.0, SupportedUnits.EACH);
        LocalDateTime packagedOn = LocalDateTime.of(1995, 1, 1, 1, 1);
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        FinishedGoodLot finishedGood = new FinishedGoodLot(id, sku, mixturePortions, materialPortions, finishedGoodLotPortions, quantity, packagedOn, created, lastUpdated, version);

        final String json = "{\"id\":1,\"sku\":{\"id\":2,\"number\":null,\"name\":null,\"description\":null,\"product\":null,\"materials\":null,\"quantity\":null,\"isPrimary\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"mixturePortions\":[{\"id\":5,\"mixture\":null,\"quantity\":null,\"addedAt\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null}],\"materialPortions\":[{\"id\":6,\"materialLot\":null,\"quantity\":null,\"addedAt\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null}],\"finishedGoodLotPortions\":[{\"id\":7,\"quantity\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null}],\"quantity\":{\"symbol\":\"each\",\"value\":100},\"packagedOn\":\"1995-01-01T01:01:00\",\"createdAt\":\"2019-01-02T03:04:00\",\"lastUpdated\":\"2020-01-02T03:04:00\",\"version\":1}";
        JSONAssert.assertEquals(json, finishedGood.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
