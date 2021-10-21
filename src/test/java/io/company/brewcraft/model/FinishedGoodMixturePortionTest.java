package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import javax.measure.Quantity;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class FinishedGoodMixturePortionTest {

    private FinishedGoodMixturePortion mixturePortion;

    @BeforeEach
    public void init() {
        mixturePortion = new FinishedGoodMixturePortion();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        Mixture mixture = new Mixture(2L);
        Quantity<?> quantity = Quantities.getQuantity(100.0, SupportedUnits.GRAM);
        FinishedGood finishedGood = new FinishedGood(3L);
        LocalDateTime addedAt = LocalDateTime.of(2018, 1, 2, 3, 4);
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        FinishedGoodMixturePortion mixturePortion = new FinishedGoodMixturePortion(id, mixture, quantity, finishedGood, addedAt, created, lastUpdated, version);

        assertEquals(1L, mixturePortion.getId());
        assertEquals(new Mixture(2L), mixturePortion.getMixture());
        assertEquals(Quantities.getQuantity(100.0, SupportedUnits.GRAM), mixturePortion.getQuantity());
        assertEquals(new FinishedGood(3L), mixturePortion.getFinishedGood());
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
    public void testGetSetQuantity() {
        mixturePortion.setQuantity(Quantities.getQuantity(100.0, SupportedUnits.GRAM));
        assertEquals(Quantities.getQuantity(100.0, SupportedUnits.GRAM), mixturePortion.getQuantity());
    }
    
    @Test
    public void testGetSetFinishedGood() {
        mixturePortion.setFinishedGood(new FinishedGood(5L));
        assertEquals(new FinishedGood(5L), mixturePortion.getFinishedGood());
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
        FinishedGood finishedGood = new FinishedGood(3L);
        LocalDateTime addedAt = LocalDateTime.of(2018, 1, 2, 3, 4);
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        FinishedGoodMixturePortion mixturePortion = new FinishedGoodMixturePortion(id, mixture, quantity, finishedGood, addedAt, created, lastUpdated, version);

        final String json = "{\"id\":1,\"mixture\":{\"id\":2,\"parentMixture\":null,\"quantity\":null,\"equipment\":null,\"brewStage\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"quantity\":{\"symbol\":\"g\",\"value\":100},\"addedAt\":{\"nano\":0,\"year\":2018,\"monthValue\":1,\"dayOfMonth\":2,\"hour\":3,\"minute\":4,\"second\":0,\"dayOfYear\":2,\"dayOfWeek\":\"TUESDAY\",\"month\":\"JANUARY\",\"chronology\":{\"id\":\"ISO\",\"calendarType\":\"iso8601\"}},\"createdAt\":{\"nano\":0,\"year\":2019,\"monthValue\":1,\"dayOfMonth\":2,\"hour\":3,\"minute\":4,\"second\":0,\"dayOfYear\":2,\"dayOfWeek\":\"WEDNESDAY\",\"month\":\"JANUARY\",\"chronology\":{\"id\":\"ISO\",\"calendarType\":\"iso8601\"}},\"lastUpdated\":{\"nano\":0,\"year\":2020,\"monthValue\":1,\"dayOfMonth\":2,\"hour\":3,\"minute\":4,\"second\":0,\"dayOfYear\":2,\"dayOfWeek\":\"THURSDAY\",\"month\":\"JANUARY\",\"chronology\":{\"id\":\"ISO\",\"calendarType\":\"iso8601\"}},\"version\":1}";
        JSONAssert.assertEquals(json, mixturePortion.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
