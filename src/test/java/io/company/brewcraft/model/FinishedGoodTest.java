package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

public class FinishedGoodTest {

    private FinishedGood finishedGood;

    @BeforeEach
    public void init() {
        finishedGood = new FinishedGood();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        Sku sku = new Sku(2L);
        List<FinishedGoodMixturePortion> mixturePortions = List.of(new FinishedGoodMixturePortion(5L));
        List<FinishedGoodMaterialPortion> materialPortions = List.of(new FinishedGoodMaterialPortion(6L));
        FinishedGood parentFinishedGood = new FinishedGood(5L);
        List<FinishedGood> childFinishedGoods = List.of(new FinishedGood(10L));
        LocalDateTime packagedOn = LocalDateTime.of(1995, 1, 1, 1, 1);
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        FinishedGood finishedGood = new FinishedGood(id, sku, mixturePortions, materialPortions, parentFinishedGood, childFinishedGoods, packagedOn, created, lastUpdated, version);

        assertEquals(1L, finishedGood.getId());
        assertEquals(new Sku(2L), finishedGood.getSku());

        FinishedGoodMixturePortion expectedMixturePortion = new FinishedGoodMixturePortion(5L);
        expectedMixturePortion.setFinishedGood(finishedGood);
        assertEquals(List.of(expectedMixturePortion), finishedGood.getMixturePortions());

        FinishedGoodMaterialPortion expectedMaterialPortion = new FinishedGoodMaterialPortion(6L);
        expectedMaterialPortion.setFinishedGood(finishedGood);
        assertEquals(List.of(expectedMaterialPortion), finishedGood.getMaterialPortions());

        FinishedGood expectedParentFinishedGood = new FinishedGood(5L);
        expectedParentFinishedGood.addChildFinishedGood(finishedGood);
        assertEquals(expectedParentFinishedGood, finishedGood.getParentFinishedGood());

        FinishedGood expectedChildFinishedGood = new FinishedGood(10L);
        expectedChildFinishedGood.setParentFinishedGood(finishedGood);
        assertEquals(List.of(expectedChildFinishedGood), finishedGood.getChildFinishedGoods());

        assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGood.getPackagedOn());
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), finishedGood.getCreatedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), finishedGood.getLastUpdated());
        assertEquals(1, finishedGood.getVersion());
    }

    @Test
    public void testGetSetId() {
        finishedGood.setId(1L);
        assertEquals(1L, finishedGood.getId());
    }

    @Test
    public void testGetSetSku() {
        finishedGood.setSku(new Sku(3L));
        assertEquals(new Sku(3L), finishedGood.getSku());
    }

    @Test
    public void testGetSetMixturePortions() {
        FinishedGoodMixturePortion expectedMixturePortion = new FinishedGoodMixturePortion(5L);
        expectedMixturePortion.setFinishedGood(finishedGood);

        finishedGood.setMixturePortions(List.of(expectedMixturePortion));
        assertEquals(List.of(expectedMixturePortion), finishedGood.getMixturePortions());
    }

    @Test
    public void testGetSetMaterialPortions() {
        FinishedGoodMaterialPortion expectedMaterialPortion = new FinishedGoodMaterialPortion(6L);
        expectedMaterialPortion.setFinishedGood(finishedGood);

        finishedGood.setMaterialPortions(List.of(expectedMaterialPortion));
        assertEquals(List.of(expectedMaterialPortion), finishedGood.getMaterialPortions());
    }

    @Test
    public void testGetSetPackagedOn() {
        finishedGood.setPackagedOn(LocalDateTime.of(1995, 1, 1, 1, 1));
        assertEquals(LocalDateTime.of(1995, 1, 1, 1, 1), finishedGood.getPackagedOn());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        finishedGood.setCreatedAt(created);
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), finishedGood.getCreatedAt());
    }

    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        finishedGood.setLastUpdated(lastUpdated);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), finishedGood.getLastUpdated());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        finishedGood.setVersion(version);
        assertEquals(version, finishedGood.getVersion());
    }

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        Long id = 1L;
        Sku sku = new Sku(2L);
        List<FinishedGoodMixturePortion> mixturePortions = List.of(new FinishedGoodMixturePortion(5L));
        List<FinishedGoodMaterialPortion> materialPortions = List.of(new FinishedGoodMaterialPortion(6L));
        FinishedGood parentFinishedGood = new FinishedGood(5L);
        List<FinishedGood> childFinishedGoods = List.of(new FinishedGood(10L));
        LocalDateTime packagedOn = LocalDateTime.of(1995, 1, 1, 1, 1);
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        FinishedGood finishedGood = new FinishedGood(id, sku, mixturePortions, materialPortions, parentFinishedGood, childFinishedGoods, packagedOn, created, lastUpdated, version);

        final String json = "{\"id\":1,\"sku\":{\"id\":2,\"name\":null,\"description\":null,\"product\":null,\"materials\":null,\"quantity\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"mixturePortions\":[{\"id\":5,\"mixture\":null,\"quantity\":null,\"addedAt\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null}],\"materialPortions\":[{\"id\":6,\"materialLot\":null,\"quantity\":null,\"addedAt\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null}],\"parentFinishedGood\":{\"id\":5,\"sku\":null,\"mixturePortions\":null,\"materialPortions\":null,\"parentFinishedGood\":null,\"packagedOn\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"packagedOn\":\"1995-01-01T01:01:00\",\"createdAt\":\"2019-01-02T03:04:00\",\"lastUpdated\":\"2020-01-02T03:04:00\",\"version\":1}";
        JSONAssert.assertEquals(json, finishedGood.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
