package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.BaseMaterialLot;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.model.UpdateMaterialLot;
import io.company.brewcraft.service.UpdateService;
import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class MaterialLotServiceTest {

    private MaterialLotService service;
    private UpdateService<Long, MaterialLot, BaseMaterialLot<?>, UpdateMaterialLot<?>> mUpdateService;

    @BeforeEach
    public void init() {
        this.mUpdateService = mock(UpdateService.class);
        this.service = new MaterialLotService(this.mUpdateService);
    }

    @Test
    public void testGetPutLots_ReturnsNewLotsWithExistingLotsUpdated_WhenPayloadObjectsHaveIds() {
        final List<MaterialLot> existingLots = List.of(
            new MaterialLot(1L),
            new MaterialLot(2L)
        );

        final List<UpdateMaterialLot<?>> lotUpdates = List.of(
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1),
            new MaterialLot(2L, "LOT_2", Quantities.getQuantity(new BigDecimal("20"), SupportedUnits.KILOGRAM), new InvoiceItem(2L), new Storage(6L), LocalDateTime.of(2999, 1, 1, 12, 0, 0), LocalDateTime.of(3000, 1, 1, 12, 0, 0), 1)
        );

        doAnswer(inv -> inv.getArgument(1, List.class)).when(this.mUpdateService).getPutEntities(existingLots, lotUpdates);

        final List<MaterialLot> updatedLots = this.service.getPutEntities(existingLots, lotUpdates);

        final List<UpdateMaterialLot<?>> expected = List.of(
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1),
            new MaterialLot(2L, "LOT_2", Quantities.getQuantity(new BigDecimal("20"), SupportedUnits.KILOGRAM), new InvoiceItem(2L), new Storage(6L), LocalDateTime.of(2999, 1, 1, 12, 0, 0), LocalDateTime.of(3000, 1, 1, 12, 0, 0), 1)
        );

        assertEquals(expected, updatedLots);
    }

    @Test
    public void testGetPatchLots_ReturnsNewLotsCollectionWithNonNullPropertiesApplied_WhenPayloadObjectsHaveId() {
        final List<MaterialLot> existingLots = List.of(
            new MaterialLot(1L),
            new MaterialLot(2L)
        );

        final List<UpdateMaterialLot<?>> lotUpdates = List.of(
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1),
            new MaterialLot(2L, "LOT_2", Quantities.getQuantity(new BigDecimal("20"), SupportedUnits.KILOGRAM), new InvoiceItem(2L), new Storage(6L), LocalDateTime.of(2999, 1, 1, 12, 0, 0), LocalDateTime.of(3000, 1, 1, 12, 0, 0), 1)
        );

        doAnswer(inv -> inv.getArgument(1, List.class)).when(this.mUpdateService).getPatchEntities(existingLots, lotUpdates);

        final List<MaterialLot> updatedLots = this.service.getPatchEntities(existingLots, lotUpdates);

        final List<UpdateMaterialLot<?>> expected = List.of(
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1),
            new MaterialLot(2L, "LOT_2", Quantities.getQuantity(new BigDecimal("20"), SupportedUnits.KILOGRAM), new InvoiceItem(2L), new Storage(6L), LocalDateTime.of(2999, 1, 1, 12, 0, 0), LocalDateTime.of(3000, 1, 1, 12, 0, 0), 1)
        );

        assertEquals(expected, updatedLots);
    }

    @Test
    public void testAddCollection_ReturnsCollectionOfBaseLots_WhenInputIsNotNull() {
        final List<BaseMaterialLot<?>> lotUpdates = List.of(
            new MaterialLot(1L),
            new MaterialLot(2L)
        );

        doAnswer(inv -> inv.getArgument(0, List.class)).when(this.mUpdateService).getAddEntities(lotUpdates);

        final List<MaterialLot> updatedLots = this.service.getAddEntities(lotUpdates);

        final List<BaseMaterialLot<?>> expected = List.of(
            new MaterialLot(1L),
            new MaterialLot(2L)
        );

        assertEquals(expected, updatedLots);
    }
}
