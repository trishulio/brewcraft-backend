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
        LocalDateTime addedAt = LocalDateTime.of(2018, 1, 2, 3, 4);
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        Integer version = 1;

        MaterialPortion materialPortion = new MaterialPortion(id, materialLot, quantity, addedAt, created, lastUpdated, version);

        assertEquals(1L, materialPortion.getId());
        assertEquals(new MaterialLot(2L), materialPortion.getMaterialLot());
        assertEquals(Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), materialPortion.getQuantity());
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
        MaterialLot materialLot = new MaterialLot(2L);
        Quantity<?> quantity = Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM);
        LocalDateTime addedAt = LocalDateTime.of(2018, 1, 2, 3, 4);
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        Integer version = 1;

        MaterialPortion materialPortion = new MaterialPortion(id, materialLot, quantity, addedAt, created, lastUpdated, version);

        final String json = "{\"id\":1,\"materialLot\":{\"id\":2,\"lotNumber\":null,\"quantity\":null,\"invoiceItem\":null,\"storage\":null,\"createdAt\":null,\"lastUpdated\":null,\"version\":null},\"quantity\":{\"symbol\":\"kg\",\"value\":100},\"addedAt\":\"2018-01-02T03:04:00\",\"createdAt\":\"2019-01-02T03:04:00\",\"lastUpdated\":\"2020-01-02T03:04:00\",\"version\":1}";
        JSONAssert.assertEquals(json, materialPortion.toString(), JSONCompareMode.NON_EXTENSIBLE);
    }
}
