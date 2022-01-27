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

public class FinishedGoodLotMixturePortionTest {

    private FinishedGoodLotMixturePortion mixturePortion;

    @BeforeEach
    public void init() {
        mixturePortion = new FinishedGoodLotMixturePortion();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        Mixture mixture = new Mixture(2L);
        Quantity<?> quantity = Quantities.getQuantity(100.0, SupportedUnits.GRAM);
        FinishedGoodLot finishedGood = new FinishedGoodLot(3L);
        LocalDateTime addedAt = LocalDateTime.of(2018, 1, 2, 3, 4);
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        FinishedGoodLotMixturePortion mixturePortion = new FinishedGoodLotMixturePortion(id, mixture, quantity, finishedGood, addedAt, created, lastUpdated, version);

        assertEquals(1L, mixturePortion.getId());
        assertEquals(new Mixture(2L), mixturePortion.getMixture());
        assertEquals(Quantities.getQuantity(100.0, SupportedUnits.GRAM), mixturePortion.getQuantity());
        assertEquals(new FinishedGoodLot(3L), mixturePortion.getFinishedGoodLot());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), mixturePortion.getAddedAt());
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), mixturePortion.getCreatedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), mixturePortion.getLastUpdated());
        assertEquals(1, mixturePortion.getVersion());
    }

    @Test
    public void testGetSetId() {
        mixturePortion.setId(1L);
        assertEquals(1L, mixturePortion.getId());
    }

    @Test
    public void testGetSetMixture() {
        mixturePortion.setMixture(new Mixture(3L));
        assertEquals(new Mixture(3L), mixturePortion.getMixture());
    }

    @Test
    public void testSetMixture_ThrowsException_WhenMixtureQuantityIsIncompatibleWithPortionQuantity() {
        Mixture mixture = new Mixture(3L);
        mixture.setQuantity(Quantities.getQuantity("10 l"));
        mixturePortion.setMixture(mixture);

        assertThrows(IncompatibleQuantityUnitException.class, () -> mixturePortion.setQuantity(Quantities.getQuantity("10 kg")));
    }

    @Test
    public void testGetSetQuantity() {
        mixturePortion.setQuantity(Quantities.getQuantity(100.0, SupportedUnits.GRAM));
        assertEquals(Quantities.getQuantity(100.0, SupportedUnits.GRAM), mixturePortion.getQuantity());
    }

    @Test
    public void testSetQuantity_ThrowsException_WhenMixtureQuantityIsIncompatibleWithPortionQuantity() {
        Mixture mixture = new Mixture(3L);
        mixture.setQuantity(Quantities.getQuantity("10 l"));

        mixturePortion.setQuantity(Quantities.getQuantity("10 kg"));
        assertThrows(IncompatibleQuantityUnitException.class, () -> mixturePortion.setMixture(mixture));
    }

    @Test
    public void testGetSetFinishedGood() {
        mixturePortion.setFinishedGoodLot(new FinishedGoodLot(5L));
        assertEquals(new FinishedGoodLot(5L), mixturePortion.getFinishedGoodLot());
    }

    @Test
    public void testGetSetAddedAt() {
        LocalDateTime addedAt = LocalDateTime.of(2018, 1, 2, 3, 4);
        mixturePortion.setAddedAt(addedAt);
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), mixturePortion.getAddedAt());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        mixturePortion.setCreatedAt(created);
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), mixturePortion.getCreatedAt());
    }

    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        mixturePortion.setLastUpdated(lastUpdated);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), mixturePortion.getLastUpdated());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        mixturePortion.setVersion(version);
        assertEquals(version, mixturePortion.getVersion());
    }

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        Long id = 1L;
        Mixture mixture = new Mixture(2L);
        Quantity<?> quantity = Quantities.getQuantity(100.0, SupportedUnits.GRAM);
        FinishedGoodLot finishedGood = new FinishedGoodLot(3L);
        LocalDateTime addedAt = LocalDateTime.of(2018, 1, 2, 3, 4);
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        FinishedGoodLotMixturePortion mixturePortion = new FinishedGoodLotMixturePortion(id, mixture, quantity, finishedGood, addedAt, created, lastUpdated, version);

        final String json = "{\"id\":1,\"mixture\":{\"id\":2,\"parentMixtures\":null,\"quantity\":null,\"equipment\":null,\"brewStage\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"quantity\":{\"symbol\":\"g\",\"value\":100},\"addedAt\":\"2018-01-02T03:04:00\",\"createdAt\":\"2019-01-02T03:04:00\",\"lastUpdated\":\"2020-01-02T03:04:00\",\"version\":1}";
        JSONAssert.assertEquals(json, mixturePortion.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
