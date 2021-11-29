package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.BaseMixtureMaterialPortion;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.MixtureMaterialPortion;
import io.company.brewcraft.model.StockLot;
import io.company.brewcraft.model.UpdateMixtureMaterialPortion;
import io.company.brewcraft.service.MixtureMaterialPortionAccessor;
import io.company.brewcraft.service.MixtureMaterialPortionService;
import io.company.brewcraft.service.MixtureMaterialPortionServiceImpl;
import io.company.brewcraft.service.RepoService;
import io.company.brewcraft.service.StockLotService;
import io.company.brewcraft.service.UpdateService;
import io.company.brewcraft.service.exception.MaterialLotQuantityNotAvailableException;
import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class MixtureMaterialPortionServiceImplTest {

    private MixtureMaterialPortionService materialPortionService;

    private StockLotService stockLotService;

    private RepoService<Long, MixtureMaterialPortion, MixtureMaterialPortionAccessor> repoService;

    private UpdateService<Long, MixtureMaterialPortion, BaseMixtureMaterialPortion, UpdateMixtureMaterialPortion> updateService;

    @BeforeEach
    public void init() {
        this.updateService = mock(UpdateService.class);
        this.repoService = mock(RepoService.class);
        this.stockLotService = mock(StockLotService.class);

        doAnswer(mixtureMaterialPortion -> mixtureMaterialPortion.getArgument(0)).when(this.repoService).saveAll(anyList());

        materialPortionService = new MixtureMaterialPortionServiceImpl(repoService, updateService, stockLotService);
    }

    @Test
    public void testGetMaterialPortion_ReturnsMaterialPortion() {
        doReturn(new MixtureMaterialPortion(1L)).when(this.repoService).get(1L);

        MixtureMaterialPortion materialPortion = materialPortionService.getMaterialPortion(1L);

        assertEquals(new MixtureMaterialPortion(1L), materialPortion);
    }

    @Test
    public void testGetMaterialPortion_ReturnsNull_WhenMaterialPortionDoesNotExist() {
        doReturn(null).when(this.repoService).get(1L);
        MixtureMaterialPortion materialPortion = materialPortionService.getMaterialPortion(1L);

        assertNull(materialPortion);
    }

    @Test
    @Disabled("TODO: Find a good strategy to test get method with long list of specifications")
    public void testGetMaterialPortion() {
        fail("Not tested");
    }

    @Test
    public void testAddMaterialPortion_ThrowsWhenRequestedQuantityUnitIsNotCompatibleWithStockLotUnit() {
        MixtureMaterialPortion materialPortion = new MixtureMaterialPortion(1L, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.LITRE), new Mixture(5L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(List.of(new StockLot(4L, null, Quantities.getQuantity(new BigDecimal("0"), SupportedUnits.KILOGRAM), null, null, null, null))).when(stockLotService).getAllByIds(Set.of(4L));

        assertThrows(RuntimeException.class, () -> materialPortionService.addMaterialPortion(materialPortion));
    }

    @Test
    public void testAddMaterialPortion_ThrowsWhenQuantityIsUnavailable() {
        MixtureMaterialPortion materialPortion = new MixtureMaterialPortion(1L, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), new Mixture(5L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(List.of(new StockLot(4L, null, Quantities.getQuantity(new BigDecimal("0"), SupportedUnits.KILOGRAM), null, null, null, null))).when(stockLotService).getAllByIds(Set.of(4L));

        assertThrows(MaterialLotQuantityNotAvailableException.class, () -> materialPortionService.addMaterialPortion(materialPortion));
    }

    @Test
    public void testAddMaterialPortions_ThrowsWhenRequestedQuantityUnitIsNotCompatibleWithStockLotUnit() {
        MixtureMaterialPortion materialPortion = new MixtureMaterialPortion(1L, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.LITRE), new Mixture(5L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(List.of(new StockLot(4L, null, Quantities.getQuantity(new BigDecimal("0"), SupportedUnits.KILOGRAM), null, null, null, null))).when(stockLotService).getAllByIds(Set.of(4L));

        assertThrows(RuntimeException.class, () -> materialPortionService.addMaterialPortions(List.of(materialPortion)));
    }

    @Test
    public void testAddMaterialPortions_ThrowsWhenQuantityIsUnavailable() {
        MixtureMaterialPortion materialPortion = new MixtureMaterialPortion(1L, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), new Mixture(5L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(List.of(new StockLot(4L, null, Quantities.getQuantity(new BigDecimal("0"), SupportedUnits.KILOGRAM), null, null, null, null))).when(stockLotService).getAllByIds(Set.of(4L));

        assertThrows(MaterialLotQuantityNotAvailableException.class, () -> materialPortionService.addMaterialPortions(List.of(materialPortion)));
    }

    @Test
    public void testAddMaterialPortions_AddsMaterialPortions() {
        doReturn(List.of(new StockLot(4L, null, Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), null, null, null, null))).when(stockLotService).getAllByIds(any());
        doAnswer(mixtureMaterialPortion -> mixtureMaterialPortion.getArgument(0)).when(this.updateService).getAddEntities(any());

        final BaseMixtureMaterialPortion mixtureMaterialPortion1 = new MixtureMaterialPortion(1L, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), new Mixture(5L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        final List<MixtureMaterialPortion> added = this.materialPortionService.addMaterialPortions(List.of(mixtureMaterialPortion1));

        final List<MixtureMaterialPortion> expected = List.of(
            new MixtureMaterialPortion(1L, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), new Mixture(5L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1)
        );

        assertEquals(expected, added);
        verify(this.repoService, times(1)).saveAll(added);
    }

    @Test
    public void testPutMaterialPortions_ThrowsWhenRequestedQuantityUnitIsNotCompatibleWithStockLotUnit() {
        MixtureMaterialPortion materialPortion = new MixtureMaterialPortion(1L, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.LITRE), new Mixture(5L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(List.of(new StockLot(4L, null, Quantities.getQuantity(new BigDecimal("0"), SupportedUnits.KILOGRAM), null, null, null, null))).when(stockLotService).getAllByIds(Set.of(4L));

        assertThrows(RuntimeException.class, () -> materialPortionService.putMaterialPortions(List.of(materialPortion)));
    }

    @Test
    public void testPutMaterialPortions_ThrowsWhenMaterialPortionExistsAndQuantityIsUnavailable() {
        MixtureMaterialPortion existing = new MixtureMaterialPortion(1L, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), null, null, LocalDateTime.of(2019, 1, 2, 3, 4), null, 1);

        MixtureMaterialPortion update = new MixtureMaterialPortion(1L, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), new Mixture(5L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(List.of(existing)).when(this.repoService).getByIds(List.of(update));
        doReturn(List.of(new StockLot(4L, null, Quantities.getQuantity(new BigDecimal("0"), SupportedUnits.KILOGRAM), null, null, null, null))).when(stockLotService).getAllByIds(Set.of(4L));

        assertThrows(MaterialLotQuantityNotAvailableException.class, () -> materialPortionService.putMaterialPortions(List.of(update)));
    }

    @Test
    public void testPutMaterialPortions_ThrowsWhenMaterialPortionDoesNotExistAndQuantityIsUnavailable() {
        MixtureMaterialPortion update = new MixtureMaterialPortion(1L, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), new Mixture(5L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(List.of()).when(this.repoService).getByIds(List.of(update));
        doReturn(List.of(new StockLot(4L, null, Quantities.getQuantity(new BigDecimal("0"), SupportedUnits.KILOGRAM), null, null, null, null))).when(stockLotService).getAllByIds(Set.of(4L));

        assertThrows(MaterialLotQuantityNotAvailableException.class, () -> materialPortionService.putMaterialPortions(List.of(update)));
    }

    @Test
    public void testPutMaterialPortions_OverridesWhenMaterialPortionExists_SameLot() {
        MixtureMaterialPortion existing = new MixtureMaterialPortion(1L, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), null, null, LocalDateTime.of(2019, 1, 2, 3, 4), null, 1);

        MixtureMaterialPortion update = new MixtureMaterialPortion(1L, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), new Mixture(5L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(List.of(existing)).when(this.repoService).getByIds(List.of(update));
        doReturn(List.of(new StockLot(4L, null, Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), null, null, null, null))).when(stockLotService).getAllByIds(Set.of(4L));
        doAnswer(inv -> inv.getArgument(1)).when(this.updateService).getPutEntities(any(), any());

        MixtureMaterialPortion materialPortion = materialPortionService.putMaterialPortions(List.of(update)).get(0);

        assertEquals(1L, materialPortion.getId());
        assertEquals(new MaterialLot(4L), materialPortion.getMaterialLot());
        assertEquals(Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), materialPortion.getQuantity());
        assertEquals(new Mixture(5L), materialPortion.getMixture());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), materialPortion.getAddedAt());
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), materialPortion.getCreatedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), materialPortion.getLastUpdated());
        assertEquals(1, materialPortion.getVersion());

        verify(this.repoService, times(1)).saveAll(List.of(materialPortion));
    }

    @Test
    public void testPutMaterialPortions_OverridesWhenMaterialPortionExists_DifferentLot() {
        MixtureMaterialPortion existing = new MixtureMaterialPortion(1L, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), null, null, LocalDateTime.of(2019, 1, 2, 3, 4), null, 1);

        MixtureMaterialPortion update = new MixtureMaterialPortion(1L, new MaterialLot(5L), Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), new Mixture(5L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(List.of(existing)).when(this.repoService).getByIds(List.of(update));
        doReturn(List.of(new StockLot(5L, null, Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), null, null, null, null))).when(stockLotService).getAllByIds(Set.of(5L));
        doAnswer(inv -> inv.getArgument(1)).when(this.updateService).getPutEntities(any(), any());

        MixtureMaterialPortion materialPortion = materialPortionService.putMaterialPortions(List.of(update)).get(0);

        assertEquals(1L, materialPortion.getId());
        assertEquals(new MaterialLot(5L), materialPortion.getMaterialLot());
        assertEquals(Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), materialPortion.getQuantity());
        assertEquals(new Mixture(5L), materialPortion.getMixture());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), materialPortion.getAddedAt());
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), materialPortion.getCreatedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), materialPortion.getLastUpdated());
        assertEquals(1, materialPortion.getVersion());

        verify(this.repoService, times(1)).saveAll(List.of(materialPortion));
    }

    @Test
    public void testPutMaterialPortions_AddsNewMaterialPortion_WhenNoMaterialPortionExists() {
        MixtureMaterialPortion update = new MixtureMaterialPortion(1L, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), new Mixture(5L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(List.of()).when(this.repoService).getByIds(List.of(update));
        doReturn(List.of(new StockLot(4L, null, Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), null, null, null, null))).when(stockLotService).getAllByIds(Set.of(4L));
        doAnswer(inv -> inv.getArgument(1)).when(this.updateService).getPutEntities(any(), any());

        MixtureMaterialPortion materialPortion = materialPortionService.putMaterialPortions(List.of(update)).get(0);

        assertEquals(1L, materialPortion.getId());
        assertEquals(new MaterialLot(4L), materialPortion.getMaterialLot());
        assertEquals(Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), materialPortion.getQuantity());
        assertEquals(new Mixture(5L), materialPortion.getMixture());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), materialPortion.getAddedAt());
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), materialPortion.getCreatedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), materialPortion.getLastUpdated());
        assertEquals(1, materialPortion.getVersion());

        verify(this.repoService, times(1)).saveAll(List.of(materialPortion));
    }

    @Test
    public void testPatcbMaterialPortions_ThrowsWhenRequestedQuantityUnitIsNotCompatibleWithStockLotUnit() {
        MixtureMaterialPortion materialPortion = new MixtureMaterialPortion(1L, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.LITRE), new Mixture(5L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(List.of(new StockLot(4L, null, Quantities.getQuantity(new BigDecimal("0"), SupportedUnits.KILOGRAM), null, null, null, null))).when(stockLotService).getAllByIds(Set.of(4L));

        assertThrows(RuntimeException.class, () -> materialPortionService.patchMaterialPortions(List.of(materialPortion)));
    }

    @Test
    public void testPatchMaterialPortions_ThrowsWhenQuantityIsUnavailable() {
        MixtureMaterialPortion existing = new MixtureMaterialPortion(1L, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), null, null, LocalDateTime.of(2019, 1, 2, 3, 4), null, 1);

        MixtureMaterialPortion update = new MixtureMaterialPortion(1L, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), new Mixture(5L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(List.of(existing)).when(this.repoService).getByIds(List.of(update));
        doReturn(List.of(new StockLot(4L, null, Quantities.getQuantity(new BigDecimal("0"), SupportedUnits.KILOGRAM), null, null, null, null))).when(stockLotService).getAllByIds(Set.of(4L));

        assertThrows(MaterialLotQuantityNotAvailableException.class, () -> materialPortionService.patchMaterialPortions(List.of(update)));
    }

    @Test
    public void testPatchMaterialPortions_PatchesExistingMaterialPortion_SameLot() {
        MixtureMaterialPortion existing = new MixtureMaterialPortion(1L, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), null, null, LocalDateTime.of(2019, 1, 2, 3, 4), null, 1);

        MixtureMaterialPortion update = new MixtureMaterialPortion(1L, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), new Mixture(5L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(List.of(existing)).when(this.repoService).getByIds(List.of(update));
        doReturn(List.of(new StockLot(4L, null, Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), null, null, null, null))).when(stockLotService).getAllByIds(Set.of(4L));
        doAnswer(inv -> inv.getArgument(1)).when(this.updateService).getPatchEntities(any(), any());

        MixtureMaterialPortion materialPortion = materialPortionService.patchMaterialPortions(List.of(update)).get(0);

        assertEquals(1L, materialPortion.getId());
        assertEquals(new MaterialLot(4L), materialPortion.getMaterialLot());
        assertEquals(Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), materialPortion.getQuantity());
        assertEquals(new Mixture(5L), materialPortion.getMixture());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), materialPortion.getAddedAt());
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), materialPortion.getCreatedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), materialPortion.getLastUpdated());
        assertEquals(1, materialPortion.getVersion());

        verify(this.repoService, times(1)).saveAll(List.of(materialPortion));
    }

    @Test
    public void testPatchMaterialPortions_PatchesExistingMaterialPortion_DifferentLot() {
        MixtureMaterialPortion existing = new MixtureMaterialPortion(1L, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), null, null, LocalDateTime.of(2019, 1, 2, 3, 4), null, 1);

        MixtureMaterialPortion update = new MixtureMaterialPortion(1L, new MaterialLot(5L), Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), new Mixture(5L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(List.of(existing)).when(this.repoService).getByIds(List.of(update));
        doReturn(List.of(new StockLot(5L, null, Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), null, null, null, null))).when(stockLotService).getAllByIds(Set.of(5L));
        doAnswer(inv -> inv.getArgument(1)).when(this.updateService).getPatchEntities(any(), any());

        MixtureMaterialPortion materialPortion = materialPortionService.patchMaterialPortions(List.of(update)).get(0);

        assertEquals(1L, materialPortion.getId());
        assertEquals(new MaterialLot(5L), materialPortion.getMaterialLot());
        assertEquals(Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), materialPortion.getQuantity());
        assertEquals(new Mixture(5L), materialPortion.getMixture());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), materialPortion.getAddedAt());
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), materialPortion.getCreatedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), materialPortion.getLastUpdated());
        assertEquals(1, materialPortion.getVersion());

        verify(this.repoService, times(1)).saveAll(List.of(materialPortion));
    }

    @Test
    public void testExists_ReturnsTrue_WhenRepositoryReturnsTrue() {
        doReturn(true).when(repoService).exists(1L);

        boolean exists = materialPortionService.materialPortionExists(1L);

        assertTrue(exists);
    }

    @Test
    public void testExists_ReturnsFalse_WhenRepositoryReturnsFalse() {
        doReturn(false).when(repoService).exists(1L);

        boolean exists = materialPortionService.materialPortionExists(1L);

        assertFalse(exists);
    }

    @Test
    public void testDelete_CallsDeleteByIdOnRepository() {
        materialPortionService.deleteMaterialPortions(Set.of(1L, 2L));

        verify(repoService, times(1)).delete(Set.of(1L, 2L));
    }

}
