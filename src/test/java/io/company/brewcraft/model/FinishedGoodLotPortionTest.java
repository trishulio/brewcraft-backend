package io.company.brewcraft.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import javax.measure.Quantity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class FinishedGoodLotPortionTest {

    private FinishedGoodLotPortion finishedGoodLotPortion;

    @BeforeEach
    public void init() {
        finishedGoodLotPortion = new FinishedGoodLotPortion();
    }

    @Test
    public void testConstructor() {
        Long id = 1L;
        FinishedGoodLot finishedGoodLotDto = new FinishedGoodLot(3L);
        Quantity<?> quantity = Quantities.getQuantity(100.0, SupportedUnits.EACH);
        LocalDateTime created = LocalDateTime.of(2019, 1, 2, 3, 4);
        LocalDateTime lastUpdated = LocalDateTime.of(2020, 1, 2, 3, 4);
        int version = 1;

        FinishedGoodLotPortion finishedGoodLotPortion = new FinishedGoodLotPortion(id, finishedGoodLotDto, quantity, created, lastUpdated, version);

        assertEquals(1L, finishedGoodLotPortion.getId());
        assertEquals(new FinishedGoodLot(3L), finishedGoodLotPortion.getFinishedGoodLot());
        assertEquals(Quantities.getQuantity(100, SupportedUnits.EACH), finishedGoodLotPortion.getQuantity());
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
    public void testGetSetFinishedGoodLot() {
        finishedGoodLotPortion.setFinishedGoodLot(new FinishedGoodLot(3L));

        assertEquals(new FinishedGoodLot(3L), finishedGoodLotPortion.getFinishedGoodLot());
    }

    @Test
    public void testGetSetQuantity() {
        finishedGoodLotPortion.setQuantity(Quantities.getQuantity(100.0, SupportedUnits.EACH));
        assertEquals(Quantities.getQuantity(100.0, SupportedUnits.EACH), finishedGoodLotPortion.getQuantity());
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
}
