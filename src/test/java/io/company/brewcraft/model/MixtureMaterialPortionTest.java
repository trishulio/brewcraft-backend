package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

public class MixtureMaterialPortionTest {

    private MixtureMaterialPortion mixtureMaterialPortion;

    @BeforeEach
    public void init() {
        mixtureMaterialPortion = new MixtureMaterialPortion();
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

        MixtureMaterialPortion mixtureMaterialPortion = new MixtureMaterialPortion(id, materialLot, quantity, mixture, addedAt, created, lastUpdated, version);

        assertEquals(1L, mixtureMaterialPortion.getId());
        assertEquals(new MaterialLot(2L), mixtureMaterialPortion.getMaterialLot());
        assertEquals(Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), mixtureMaterialPortion.getQuantity());
        assertEquals(new Mixture(2L), mixtureMaterialPortion.getMixture());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), mixtureMaterialPortion.getAddedAt());
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), mixtureMaterialPortion.getCreatedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), mixtureMaterialPortion.getLastUpdated());
        assertEquals(1, mixtureMaterialPortion.getVersion());
    }

    @Test
    public void testGetSetId() {
        mixtureMaterialPortion.setId(1L);
        assertEquals(1L, mixtureMaterialPortion.getId());
    }

    @Test
    public void testGetSetMaterialLot() {
        mixtureMaterialPortion.setMaterialLot(new MaterialLot(2L));

        assertEquals(new MaterialLot(2L), mixtureMaterialPortion.getMaterialLot());
    }

    @Test
    public void testGetSetQuantity() {
        mixtureMaterialPortion.setQuantity(Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM));
        assertEquals(Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), mixtureMaterialPortion.getQuantity());
    }

    @Test
    public void testGetSetMixture() {
        mixtureMaterialPortion.setMixture(new Mixture(2L));
        assertEquals(new Mixture(2L), mixtureMaterialPortion.getMixture());
    }

    @Test
    public void testGetSetAddedAt() {
        mixtureMaterialPortion.setAddedAt(LocalDateTime.of(2019, 1, 2, 3, 4));
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), mixtureMaterialPortion.getAddedAt());
    }

    @Test
    public void testGetSetCreated() {
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        mixtureMaterialPortion.setCreatedAt(created);
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), mixtureMaterialPortion.getCreatedAt());
    }

    @Test
    public void testGetSetLastUpdated() {
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        mixtureMaterialPortion.setLastUpdated(lastUpdated);
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), mixtureMaterialPortion.getLastUpdated());
    }

    @Test
    public void testGetSetVersion() {
        Integer version = 1;
        mixtureMaterialPortion.setVersion(version);
        assertEquals(version, mixtureMaterialPortion.getVersion());
    }

    @Test
    public void testToString_ReturnsJsonifiedString() throws JSONException {
        Long id = 1L;
        MaterialLot materialLot = new MaterialLot(2L);
        Quantity<?> quantity = Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM);
        Mixture mixture = new Mixture(2L);
        LocalDateTime addedAt = LocalDateTime.of(2018, 1, 2, 3, 4);
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        Integer version = 1;

        MixtureMaterialPortion mixtureMaterialPortion = new MixtureMaterialPortion(id, materialLot, quantity, mixture, addedAt, created, lastUpdated, version);

        final String json = "{\"id\":1,\"materialLot\":{\"id\":2,\"lotNumber\":null,\"quantity\":null,\"invoiceItem\":null,\"storage\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"quantity\":{\"symbol\":\"kg\",\"value\":100},\"addedAt\":\"2018-01-02T03:04:00\",\"createdAt\":\"2019-01-02T03:04:00\",\"lastUpdated\":\"2020-01-02T03:04:00\",\"version\":1,\"mixture\":{\"id\":2,\"parentMixtures\":null,\"quantity\":null,\"equipment\":null,\"brewStage\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null}}";
        JSONAssert.assertEquals(json, mixtureMaterialPortion.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
