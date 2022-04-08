package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.BaseFinishedGoodLotFinishedGoodLotPortion;
import io.company.brewcraft.model.FinishedGoodLot;
import io.company.brewcraft.model.FinishedGoodLotFinishedGoodLotPortion;
import io.company.brewcraft.model.UpdateFinishedGoodLotFinishedGoodLotPortion;
import io.company.brewcraft.service.FinishedGoodLotFinishedGoodLotPortionService;
import io.company.brewcraft.service.UpdateService;
import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class FinishedGoodLotFinishedGoodLotPortionServiceTest {
    private FinishedGoodLotFinishedGoodLotPortionService service;
    private UpdateService<Long, FinishedGoodLotFinishedGoodLotPortion, BaseFinishedGoodLotFinishedGoodLotPortion, UpdateFinishedGoodLotFinishedGoodLotPortion> mUpdateService;

    @BeforeEach
    public void init() {
        this.mUpdateService = mock(UpdateService.class);
        this.service = new FinishedGoodLotFinishedGoodLotPortionService(this.mUpdateService);
    }

    @Test
    public void testGetPutPortions_ReturnsNewPortionsWithExistingPortionsUpdated_WhenPayloadObjectsHaveIds() {
        final List<FinishedGoodLotFinishedGoodLotPortion> existingPortions = List.of(
            new FinishedGoodLotFinishedGoodLotPortion(1L, new FinishedGoodLot(3L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.EACH), new FinishedGoodLot(5L), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1),
            new FinishedGoodLotFinishedGoodLotPortion(2L, new FinishedGoodLot(4L), Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.EACH), new FinishedGoodLot(6L), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 2)
        );

        final List<UpdateFinishedGoodLotFinishedGoodLotPortion> portionUpdates = List.of(
            new FinishedGoodLotFinishedGoodLotPortion(1L, new FinishedGoodLot(7L), Quantities.getQuantity(new BigDecimal("300"), SupportedUnits.EACH), new FinishedGoodLot(9L), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1),
            new FinishedGoodLotFinishedGoodLotPortion(2L, new FinishedGoodLot(8L), Quantities.getQuantity(new BigDecimal("400"), SupportedUnits.EACH), new FinishedGoodLot(10L), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 2)
        );

        doAnswer(inv -> inv.getArgument(1, List.class)).when(this.mUpdateService).getPutEntities(existingPortions, portionUpdates);

        final List<FinishedGoodLotFinishedGoodLotPortion> updatedPortions = this.service.getPutEntities(existingPortions, portionUpdates);

        final List<UpdateFinishedGoodLotFinishedGoodLotPortion> expected = List.of(
            new FinishedGoodLotFinishedGoodLotPortion(1L, new FinishedGoodLot(7L), Quantities.getQuantity(new BigDecimal("300"), SupportedUnits.EACH), new FinishedGoodLot(9L), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1),
            new FinishedGoodLotFinishedGoodLotPortion(2L, new FinishedGoodLot(8L), Quantities.getQuantity(new BigDecimal("400"), SupportedUnits.EACH), new FinishedGoodLot(10L), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 2)
        );

        assertEquals(expected, updatedPortions);
    }

    @Test
    public void testGetPatchPortions_ReturnsNewPortionsCollectionWithNonNullPropertiesApplied_WhenPayloadObjectsHaveId() {
        final List<FinishedGoodLotFinishedGoodLotPortion> existingPortions = List.of(
            new FinishedGoodLotFinishedGoodLotPortion(1L, new FinishedGoodLot(3L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.EACH), new FinishedGoodLot(5L), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1),
            new FinishedGoodLotFinishedGoodLotPortion(2L, new FinishedGoodLot(4L), Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.EACH), new FinishedGoodLot(6L), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 2)
        );

        final List<UpdateFinishedGoodLotFinishedGoodLotPortion> portionUpdates = List.of(
            new FinishedGoodLotFinishedGoodLotPortion(1L, new FinishedGoodLot(7L), Quantities.getQuantity(new BigDecimal("300"), SupportedUnits.EACH), new FinishedGoodLot(9L), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1),
            new FinishedGoodLotFinishedGoodLotPortion(2L, new FinishedGoodLot(8L), Quantities.getQuantity(new BigDecimal("400"), SupportedUnits.EACH), new FinishedGoodLot(10L), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 2)
        );

        doAnswer(inv -> inv.getArgument(1, List.class)).when(this.mUpdateService).getPatchEntities(existingPortions, portionUpdates);

        final List<FinishedGoodLotFinishedGoodLotPortion> updatedPortions = this.service.getPatchEntities(existingPortions, portionUpdates);

        final List<UpdateFinishedGoodLotFinishedGoodLotPortion> expected = List.of(
            new FinishedGoodLotFinishedGoodLotPortion(1L, new FinishedGoodLot(7L), Quantities.getQuantity(new BigDecimal("300"), SupportedUnits.EACH), new FinishedGoodLot(9L), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1),
            new FinishedGoodLotFinishedGoodLotPortion(2L, new FinishedGoodLot(8L), Quantities.getQuantity(new BigDecimal("400"), SupportedUnits.EACH), new FinishedGoodLot(10L), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 2)
        );

        assertEquals(expected, updatedPortions);
    }

    @Test
    public void testAddCollection_ReturnsCollectionOfBasePortions_WhenInputIsNotNull() {
        final List<BaseFinishedGoodLotFinishedGoodLotPortion> portionUpdates = List.of(
            new FinishedGoodLotFinishedGoodLotPortion(1L, new FinishedGoodLot(3L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.EACH), new FinishedGoodLot(5L), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1),
            new FinishedGoodLotFinishedGoodLotPortion(2L, new FinishedGoodLot(4L), Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.EACH), new FinishedGoodLot(6L), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 2)
        );

        doAnswer(inv -> inv.getArgument(0, List.class)).when(this.mUpdateService).getAddEntities(portionUpdates);

        final List<FinishedGoodLotFinishedGoodLotPortion> updatedPortions = this.service.getAddEntities(portionUpdates);

        final List<BaseFinishedGoodLotFinishedGoodLotPortion> expected = List.of(
            new FinishedGoodLotFinishedGoodLotPortion(1L, new FinishedGoodLot(3L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.EACH), new FinishedGoodLot(5L), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1),
            new FinishedGoodLotFinishedGoodLotPortion(2L, new FinishedGoodLot(4L), Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.EACH), new FinishedGoodLot(6L), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 2)
        );

        assertEquals(expected, updatedPortions);
    }
}
