package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.measure.Quantity;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class MaterialPortionTest {

    private MaterialPortion materialPortion;

    @BeforeEach
    public void init() {
        materialPortion = new MaterialPortion();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        MaterialLot materialLot = new MaterialLot(2L);
        Quantity<?> quantity = Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM);
        Mixture mixture = new Mixture(2L);
        LocalDateTime addedAt = LocalDateTime.of(2018, 1, 2, 3, 4);
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        Integer version = 1;

        MaterialPortion materialPortion = new MaterialPortion(id, materialLot, quantity, mixture, addedAt, created, lastUpdated, version);

        assertEquals(1L, materialPortion.getId());
        assertEquals(new MaterialLot(2L), materialPortion.getMaterialLot());
        assertEquals(Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), materialPortion.getQuantity());
        assertEquals(new Mixture(2L), materialPortion.getMixture());
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
        materialPortion.setMaterialLot(new MaterialLot(2L));

        assertEquals(new MaterialLot(2L), materialPortion.getMaterialLot());
    }

    @Test
    public void testGetSetQuantity() {
        materialPortion.setQuantity(Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM));
        assertEquals(Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), materialPortion.getQuantity());
    }
    

    @Test
    public void testGetSetMixture() {
        materialPortion.setMixture(new Mixture(2L));
        assertEquals(new Mixture(2L), materialPortion.getMixture());
    }

    @Test
    public void testGetSetAddedAt() {
        materialPortion.setAddedAt(LocalDateTime.of(2019, 1, 2, 3, 4));
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), materialPortion.getAddedAt());
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
        Mixture mixture = new Mixture(2L);
        Measure measure = new Measure(3L);
        BigDecimal value = new BigDecimal("100");
        LocalDateTime recordedAt = LocalDateTime.of(2018, 1, 2, 3, 4);
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        Integer version = 1;

        MixtureRecording mixtureRecording = new MixtureRecording(id, mixture, measure, value, recordedAt, created, lastUpdated, version);

        final String json = "{\"id\":1,\"mixture\":{\"id\":2,\"parentMixture\":null,\"quantity\":null,\"equipment\":null,\"brewStage\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"measure\":{\"id\":3,\"name\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"value\":100,\"recordedAt\":{\"nano\":0,\"year\":2018,\"monthValue\":1,\"dayOfMonth\":2,\"hour\":3,\"minute\":4,\"second\":0,\"dayOfWeek\":\"TUESDAY\",\"dayOfYear\":2,\"month\":\"JANUARY\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"createdAt\":{\"nano\":0,\"year\":2019,\"monthValue\":1,\"dayOfMonth\":2,\"hour\":3,\"minute\":4,\"second\":0,\"dayOfWeek\":\"WEDNESDAY\",\"dayOfYear\":2,\"month\":\"JANUARY\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"lastUpdated\":{\"nano\":0,\"year\":2020,\"monthValue\":1,\"dayOfMonth\":2,\"hour\":3,\"minute\":4,\"second\":0,\"dayOfWeek\":\"THURSDAY\",\"dayOfYear\":2,\"month\":\"JANUARY\",\"chronology\":{\"calendarType\":\"iso8601\",\"id\":\"ISO\"}},\"version\":1}";
        JSONAssert.assertEquals(json, mixtureRecording.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
