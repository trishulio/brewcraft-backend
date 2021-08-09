package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import javax.persistence.OptimisticLockException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.BaseMaterialLot;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.model.UpdateMaterialLot;
import io.company.brewcraft.util.SupportedUnits;
import io.company.brewcraft.util.UtilityProvider;
import io.company.brewcraft.util.validator.ValidationException;
import io.company.brewcraft.util.validator.Validator;
import tec.uom.se.quantity.Quantities;

public class MaterialLotServiceTest {

    private MaterialLotService service;

    private UtilityProvider mUtilProvider;

    @BeforeEach
    public void init() {
        this.mUtilProvider = mock(UtilityProvider.class);
        doReturn(new Validator()).when(this.mUtilProvider).getValidator();

        this.service = new MaterialLotService(this.mUtilProvider);
    }

    @Test
    public void testGetAddLots_ReturnsListOfMaterialLotsWithBaseShipmentValues_WhenLotsAreNotNull() {
        final List<BaseMaterialLot<?>> additionLots = List.of(
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)
        );

        final List<MaterialLot> lots = this.service.getAddLots(additionLots);

        final List<MaterialLot> expected = List.of(
            new MaterialLot(
                null,
                "LOT_1",
                Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM),
                new InvoiceItem(1L),
                new Storage(3L),
                null,
                null,
                null
            )
        );

        assertEquals(expected, lots);
    }

    @Test
    public void testGetAddLots_ReturnsNull_WhenLotsAreNull() {
        assertNull(this.service.getAddLots(null));
    }

    @Test
    public void testGetPutLots_ReturnsUpdatedList_WhenUpdateIsNotNull() {
        final List<MaterialLot> existingLots = List.of(
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)
        );

        final List<UpdateMaterialLot<?>> updateLots = List.of(
            new MaterialLot(null, "LOT_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 2),
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("20"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 12, 31, 12, 0, 0), LocalDateTime.of(2000, 12, 31, 12, 0, 0), 1)
        );

        final List<MaterialLot> lots = this.service.getPutLots(existingLots, updateLots);

        final List<MaterialLot> expectedLots = List.of(
            new MaterialLot(null, "LOT_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), null, null, null),
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("20"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)
        );

        assertEquals(expectedLots, lots);
    }

    @Test
    public void testGetPutLots_ReturnsEmptyList_WhenUpdateListIsEmpty() {
        final List<MaterialLot> existingLots = List.of(
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)
        );

        assertEquals(Collections.emptyList(), this.service.getPutLots(existingLots, List.of()));
    }

    @Test
    public void testGetPutLots_ReturnsNull_WhenUpdateListIsNull() {
        final List<MaterialLot> existingLots = List.of(
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)
        );

        assertNull(this.service.getPutLots(existingLots, null));
    }

    @Test
    public void testGetPutLots_ThrowsError_WhenUpdateLotsDontHaveExistingId() {
        final List<MaterialLot> existingLots = List.of(
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)
        );

        final List<UpdateMaterialLot<?>> updateLots = List.of(
            new MaterialLot(2L, "LOT_1", Quantities.getQuantity(new BigDecimal("20"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 12, 31, 12, 0, 0), LocalDateTime.of(2000, 12, 31, 12, 0, 0), 2)
        );

        assertThrows(ValidationException.class, () -> this.service.getPutLots(existingLots, updateLots), "1. No existing lot found with Id: 2\n");
    }

    @Test
    public void testGetPutLots_ThrowsOptimisticLockingException_WhenExistingLotVersionIsDifferentFromUpdateVersion() {
        final List<MaterialLot> existingLots = List.of(
            new MaterialLot(1L),
            new MaterialLot(2L)
        );
        existingLots.get(0).setVersion(1);
        existingLots.get(1).setVersion(1);

        final List<MaterialLot> lotUpdates = List.of(
            new MaterialLot(1L),
            new MaterialLot(2L)
        );

        lotUpdates.get(0).setVersion(1);
        lotUpdates.get(1).setVersion(2);

        assertThrows(OptimisticLockException.class, () -> this.service.getPutLots(existingLots, lotUpdates), "Cannot update entity with Id: 2 of version: 1 with payload of version: 2");
    }

    @Test
    public void testGetPatchLots_ReturnsPatchedList_WhenLotsAreNotNull() {
        final List<MaterialLot> existingLots = List.of(
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)
        );

        final List<UpdateMaterialLot<?>> updateLots = List.of(
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("20"), SupportedUnits.KILOGRAM), null, new Storage(3L), LocalDateTime.of(1999, 12, 31, 12, 0, 0), LocalDateTime.of(2000, 12, 31, 12, 0, 0), 1)
        );

        final List<MaterialLot> lots = this.service.getPatchLots(existingLots, updateLots);

        final List<MaterialLot> expected = List.of(new MaterialLot(
            1L,
            "LOT_1",
            Quantities.getQuantity(new BigDecimal("20"), SupportedUnits.KILOGRAM),
            new InvoiceItem(1L),
            new Storage(3L),
            LocalDateTime.of(1999, 1, 1, 12, 0, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0, 0),
            1
        ));

        assertEquals(expected, lots);
    }

    @Test
    public void testGetPatchLots_ThrowsValidationException_WhenUpdateLotsDontHaveExistingId() {
        final List<MaterialLot> existingLots = List.of(
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("10"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 1)
        );

        final List<UpdateMaterialLot<?>> updateLots = List.of(
            new MaterialLot(2L, "LOT_1", Quantities.getQuantity(new BigDecimal("20"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 12, 31, 12, 0, 0), LocalDateTime.of(2000, 12, 31, 12, 0, 0), null)
        );

        assertThrows(ValidationException.class, () -> this.service.getPatchLots(existingLots, updateLots), "1. No existing lot found with Id: 2\\n");
    }

    @Test
    public void testGetPatchLots_ReturnsExistingLots_WhenUpdateLotsAreNull() {
        final List<MaterialLot> existingLots = List.of(new MaterialLot(1L));
        assertEquals(List.of(new MaterialLot(1L)), this.service.getPatchLots(existingLots, null));
    }

    @Test
    public void testGetPatchLots_ThrowsOptimisticLockingException_WhenExistingLotVersionIsDifferentFromUpdateVersion() {
        final List<MaterialLot> existingLots = List.of(
            new MaterialLot(1L),
            new MaterialLot(2L)
        );
        existingLots.get(0).setVersion(1);
        existingLots.get(1).setVersion(1);

        final List<MaterialLot> lotUpdates = List.of(
            new MaterialLot(1L),
            new MaterialLot(2L)
        );

        lotUpdates.get(0).setVersion(1);
        lotUpdates.get(1).setVersion(2);

        assertThrows(OptimisticLockException.class, () -> this.service.getPatchLots(existingLots, lotUpdates), "Cannot update entity with Id: 2 of version: 1 with payload of version: 2");
    }
}
