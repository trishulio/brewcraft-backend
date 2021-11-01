package io.company.brewcraft.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.OptimisticLockException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.MixtureMaterialPortion;
import io.company.brewcraft.model.StockLot;
import io.company.brewcraft.repository.MixtureMaterialPortionRepository;
import io.company.brewcraft.service.MixtureMaterialPortionService;
import io.company.brewcraft.service.MixtureMaterialPortionServiceImpl;
import io.company.brewcraft.service.StockLotService;
import io.company.brewcraft.service.exception.MaterialLotQuantityNotAvailableException;
import io.company.brewcraft.util.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class MixtureMaterialPortionServiceImplTest {

    private MixtureMaterialPortionService materialPortionService;

    private StockLotService stockLotService;

    private MixtureMaterialPortionRepository materialPortionRepository;

    @BeforeEach
    public void init() {
        materialPortionRepository = mock(MixtureMaterialPortionRepository.class);
        stockLotService = mock(StockLotService.class);

        doAnswer(i -> i.getArgument(0, MixtureMaterialPortion.class)).when(materialPortionRepository).saveAndFlush(any(MixtureMaterialPortion.class));
        doAnswer(i -> {
            Collection<MixtureMaterialPortion> coll = i.getArgument(0, Collection.class);
            coll.forEach(s -> {
                s.setMaterialLot(new MaterialLot(4L));
                s.setMixture(new Mixture(5L));
            });
            return null;
        }).when(materialPortionRepository).refresh(anyCollection());

        materialPortionService = new MixtureMaterialPortionServiceImpl(materialPortionRepository, stockLotService);
    }

    @Test
    public void testGetMaterialPortion_ReturnsMaterialPortion() {
        doReturn(Optional.of(new MixtureMaterialPortion(1L))).when(materialPortionRepository).findById(1L);

        MixtureMaterialPortion materialPortion = materialPortionService.getMaterialPortion(1L);

        assertEquals(new MixtureMaterialPortion(1L), materialPortion);
    }

    @Test
    public void testGetMaterialPortion_ReturnsNull_WhenMaterialPortionDoesNotExist() {
        doReturn(Optional.empty()).when(materialPortionRepository).findById(1L);
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
    public void testAddMaterialPortion_AddsMaterialPortion() {
        MixtureMaterialPortion materialPortion = new MixtureMaterialPortion(1L, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), new Mixture(5L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(List.of(new StockLot(4L, null, Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), null, null, null, null))).when(stockLotService).getAllByIds(Set.of(4L));
        doReturn(List.of(materialPortion)).when(materialPortionRepository).saveAll(List.of(materialPortion));

        MixtureMaterialPortion addedMaterialPortion = materialPortionService.addMaterialPortion(materialPortion);

        assertEquals(1L, addedMaterialPortion.getId());
        assertEquals(new MaterialLot(4L), addedMaterialPortion.getMaterialLot());
        assertEquals(Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), addedMaterialPortion.getQuantity());
        assertEquals(new Mixture(5L), addedMaterialPortion.getMixture());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), addedMaterialPortion.getAddedAt());
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), addedMaterialPortion.getCreatedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), addedMaterialPortion.getLastUpdated());
        assertEquals(1, addedMaterialPortion.getVersion());

        verify(materialPortionRepository, times(1)).saveAll(List.of(materialPortion));
        verify(materialPortionRepository, times(1)).flush();
        verify(materialPortionRepository, times(1)).refresh(List.of(addedMaterialPortion));
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
        MixtureMaterialPortion materialPortion = new MixtureMaterialPortion(1L, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), new Mixture(5L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(List.of(new StockLot(4L, null, Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), null, null, null, null))).when(stockLotService).getAllByIds(Set.of(4L));
        doReturn(List.of(materialPortion)).when(materialPortionRepository).saveAll(List.of(materialPortion));

        List<MixtureMaterialPortion> addedMaterialPortions = materialPortionService.addMaterialPortions(List.of(materialPortion));

        assertEquals(1, addedMaterialPortions.size());
        assertEquals(1L, addedMaterialPortions.get(0).getId());
        assertEquals(new MaterialLot(4L), addedMaterialPortions.get(0).getMaterialLot());
        assertEquals(Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), addedMaterialPortions.get(0).getQuantity());
        assertEquals(new Mixture(5L), addedMaterialPortions.get(0).getMixture());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), addedMaterialPortions.get(0).getAddedAt());
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), addedMaterialPortions.get(0).getCreatedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), addedMaterialPortions.get(0).getLastUpdated());
        assertEquals(1, addedMaterialPortions.get(0).getVersion());

        verify(materialPortionRepository, times(1)).saveAll(List.of(materialPortion));
        verify(materialPortionRepository, times(1)).flush();
        verify(materialPortionRepository, times(1)).refresh(addedMaterialPortions);
    }

    @Test
    public void testPutMaterialPortion_ThrowsWhenRequestedQuantityUnitIsNotCompatibleWithStockLotUnit() {
        MixtureMaterialPortion materialPortion = new MixtureMaterialPortion(null, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.LITRE), new Mixture(5L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(List.of(new StockLot(4L, null, Quantities.getQuantity(new BigDecimal("0"), SupportedUnits.KILOGRAM), null, null, null, null))).when(stockLotService).getAllByIds(Set.of(4L));

        assertThrows(RuntimeException.class, () -> materialPortionService.putMaterialPortion(1L, materialPortion));
    }

    @Test
    public void testPutMaterialPortion_ThrowsWhenMaterialPortionExistsAndQuantityIsUnavailable() {
        MixtureMaterialPortion existing = new MixtureMaterialPortion(1L, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), null, null, LocalDateTime.of(2019, 1, 2, 3, 4), null, 1);

        MixtureMaterialPortion update = new MixtureMaterialPortion(null, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), new Mixture(5L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(Optional.of(existing)).when(materialPortionRepository).findById(1L);
        doReturn(List.of(new StockLot(4L, null, Quantities.getQuantity(new BigDecimal("0"), SupportedUnits.KILOGRAM), null, null, null, null))).when(stockLotService).getAllByIds(Set.of(4L));

        assertThrows(MaterialLotQuantityNotAvailableException.class, () -> materialPortionService.putMaterialPortion(1L, update));
    }

    @Test
    public void testPutMaterialPortion_ThrowsWhenMaterialPortionDoesNotExistAndQuantityIsUnavailable() {
        MixtureMaterialPortion update = new MixtureMaterialPortion(null, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), new Mixture(5L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(Optional.empty()).when(materialPortionRepository).findById(1L);
        doReturn(List.of(new StockLot(4L, null, Quantities.getQuantity(new BigDecimal("0"), SupportedUnits.KILOGRAM), null, null, null, null))).when(stockLotService).getAllByIds(Set.of(4L));

        assertThrows(MaterialLotQuantityNotAvailableException.class, () -> materialPortionService.putMaterialPortion(1L, update));
    }

    @Test
    public void testPutMaterialPortion_OverridesWhenMaterialPortionExists_SameLot() {
        MixtureMaterialPortion existing = new MixtureMaterialPortion(1L, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), null, null, LocalDateTime.of(2019, 1, 2, 3, 4), null, 1);

        MixtureMaterialPortion update = new MixtureMaterialPortion(null, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), new Mixture(5L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(Optional.of(existing)).when(materialPortionRepository).findById(1L);
        doReturn(List.of(new StockLot(4L, null, Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), null, null, null, null))).when(stockLotService).getAllByIds(Set.of(4L));

        MixtureMaterialPortion materialPortion = materialPortionService.putMaterialPortion(1L, update);

        assertEquals(1L, materialPortion.getId());
        assertEquals(new MaterialLot(4L), materialPortion.getMaterialLot());
        assertEquals(Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), materialPortion.getQuantity());
        assertEquals(new Mixture(5L), materialPortion.getMixture());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), materialPortion.getAddedAt());
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), materialPortion.getCreatedAt());
        assertEquals(null, materialPortion.getLastUpdated());
        assertEquals(1, materialPortion.getVersion());

        verify(materialPortionRepository, times(1)).saveAndFlush(materialPortion);
        verify(materialPortionRepository, times(1)).refresh(anyList());
    }

    @Test
    public void testPutMaterialPortion_OverridesWhenMaterialPortionExists_DifferentLot() {
        MixtureMaterialPortion existing = new MixtureMaterialPortion(1L, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), null, null, LocalDateTime.of(2019, 1, 2, 3, 4), null, 1);

        MixtureMaterialPortion update = new MixtureMaterialPortion(null, new MaterialLot(5L), Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), new Mixture(5L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doAnswer(i -> {
            Collection<MixtureMaterialPortion> coll = i.getArgument(0, Collection.class);
            coll.forEach(s -> {
                s.setMaterialLot(new MaterialLot(5L));
                s.setMixture(new Mixture(5L));
            });
            return null;
        }).when(materialPortionRepository).refresh(anyCollection());
        doReturn(Optional.of(existing)).when(materialPortionRepository).findById(1L);
        doReturn(List.of(new StockLot(5L, null, Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), null, null, null, null))).when(stockLotService).getAllByIds(Set.of(5L));

        MixtureMaterialPortion materialPortion = materialPortionService.putMaterialPortion(1L, update);

        assertEquals(1L, materialPortion.getId());
        assertEquals(new MaterialLot(5L), materialPortion.getMaterialLot());
        assertEquals(Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), materialPortion.getQuantity());
        assertEquals(new Mixture(5L), materialPortion.getMixture());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), materialPortion.getAddedAt());
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), materialPortion.getCreatedAt());
        assertEquals(null, materialPortion.getLastUpdated());
        assertEquals(1, materialPortion.getVersion());

        verify(materialPortionRepository, times(1)).saveAndFlush(materialPortion);
        verify(materialPortionRepository, times(1)).refresh(anyList());
    }

    @Test
    public void testPutMaterialPortion_AddsNewMaterialPortion_WhenNoMaterialPortionExists() {
        MixtureMaterialPortion update = new MixtureMaterialPortion(null, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), new Mixture(5L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(Optional.empty()).when(materialPortionRepository).findById(1L);
        doReturn(List.of(new StockLot(4L, null, Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), null, null, null, null))).when(stockLotService).getAllByIds(Set.of(4L));

        MixtureMaterialPortion materialPortion = materialPortionService.putMaterialPortion(1L, update);

        assertEquals(1L, materialPortion.getId());
        assertEquals(new MaterialLot(4L), materialPortion.getMaterialLot());
        assertEquals(Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), materialPortion.getQuantity());
        assertEquals(new Mixture(5L), materialPortion.getMixture());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), materialPortion.getAddedAt());
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), materialPortion.getCreatedAt());
        assertEquals(LocalDateTime.of(2020, 1, 2, 3, 4), materialPortion.getLastUpdated());
        assertEquals(1, materialPortion.getVersion());

        verify(materialPortionRepository, times(1)).saveAndFlush(materialPortion);
        verify(materialPortionRepository, times(1)).refresh(anyList());
    }

    @Test
    public void testPutMaterialPortion_ThrowsOptimisticLockingException_WhenExistingVersionDoesNotMatchUpdateVersion() {
        MixtureMaterialPortion existing = new MixtureMaterialPortion(1L);
        existing.setVersion(1);

        doReturn(Optional.of(existing)).when(materialPortionRepository).findById(1L);

        MixtureMaterialPortion update = new MixtureMaterialPortion(null, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), new Mixture(5L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 2);

        assertThrows(OptimisticLockException.class, () -> materialPortionService.putMaterialPortion(1L, update));
    }

    @Test
    public void testPatcbMaterialPortion_ThrowsWhenRequestedQuantityUnitIsNotCompatibleWithStockLotUnit() {
        MixtureMaterialPortion materialPortion = new MixtureMaterialPortion(null, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.LITRE), new Mixture(5L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(List.of(new StockLot(4L, null, Quantities.getQuantity(new BigDecimal("0"), SupportedUnits.KILOGRAM), null, null, null, null))).when(stockLotService).getAllByIds(Set.of(4L));

        assertThrows(RuntimeException.class, () -> materialPortionService.patchMaterialPortion(1L, materialPortion));
    }

    @Test
    public void testPatchMaterialPortion_ThrowsWhenQuantityIsUnavailable() {
        MixtureMaterialPortion existing = new MixtureMaterialPortion(1L, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), null, null, LocalDateTime.of(2019, 1, 2, 3, 4), null, 1);

        MixtureMaterialPortion update = new MixtureMaterialPortion(null, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), new Mixture(5L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(Optional.of(existing)).when(materialPortionRepository).findById(1L);
        doReturn(List.of(new StockLot(4L, null, Quantities.getQuantity(new BigDecimal("0"), SupportedUnits.KILOGRAM), null, null, null, null))).when(stockLotService).getAllByIds(Set.of(4L));

        assertThrows(MaterialLotQuantityNotAvailableException.class, () -> materialPortionService.patchMaterialPortion(1L, update));
    }

    @Test
    public void testPatchMaterialPortion_PatchesExistingMaterialPortion_SameLot() {
        MixtureMaterialPortion existing = new MixtureMaterialPortion(1L, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), null, null, LocalDateTime.of(2019, 1, 2, 3, 4), null, 1);

        MixtureMaterialPortion update = new MixtureMaterialPortion(null, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), new Mixture(5L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(Optional.of(existing)).when(materialPortionRepository).findById(1L);
        doReturn(List.of(new StockLot(4L, null, Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), null, null, null, null))).when(stockLotService).getAllByIds(Set.of(4L));

        MixtureMaterialPortion materialPortion = materialPortionService.patchMaterialPortion(1L, update);

        assertEquals(1L, materialPortion.getId());
        assertEquals(new MaterialLot(4L), materialPortion.getMaterialLot());
        assertEquals(Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), materialPortion.getQuantity());
        assertEquals(new Mixture(5L), materialPortion.getMixture());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), materialPortion.getAddedAt());
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), materialPortion.getCreatedAt());
        assertEquals(null, materialPortion.getLastUpdated());
        assertEquals(1, materialPortion.getVersion());

        verify(materialPortionRepository, times(1)).saveAndFlush(materialPortion);
        verify(materialPortionRepository, times(1)).refresh(anyList());
    }

    @Test
    public void testPatchMaterialPortion_PatchesExistingMaterialPortion_DifferentLot() {
        MixtureMaterialPortion existing = new MixtureMaterialPortion(1L, new MaterialLot(4L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), null, null, LocalDateTime.of(2019, 1, 2, 3, 4), null, 1);

        MixtureMaterialPortion update = new MixtureMaterialPortion(null, new MaterialLot(5L), Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), new Mixture(5L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(Optional.of(existing)).when(materialPortionRepository).findById(1L);
        doReturn(List.of(new StockLot(5L, null, Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), null, null, null, null))).when(stockLotService).getAllByIds(Set.of(5L));

        MixtureMaterialPortion materialPortion = materialPortionService.patchMaterialPortion(1L, update);

        assertEquals(1L, materialPortion.getId());
        assertEquals(new MaterialLot(5L), materialPortion.getMaterialLot());
        assertEquals(Quantities.getQuantity(new BigDecimal("200"), SupportedUnits.KILOGRAM), materialPortion.getQuantity());
        assertEquals(new Mixture(5L), materialPortion.getMixture());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), materialPortion.getAddedAt());
        assertEquals(LocalDateTime.of(2019, 1, 2, 3, 4), materialPortion.getCreatedAt());
        assertEquals(null, materialPortion.getLastUpdated());
        assertEquals(1, materialPortion.getVersion());

        verify(materialPortionRepository, times(1)).saveAndFlush(materialPortion);
        verify(materialPortionRepository, times(1)).refresh(anyList());
    }

    @Test
    public void testPatch_ThrowsOptimisticLockingException_WhenExistingVersionAndUpdateVersionsAreDifferent() {
        MixtureMaterialPortion existing = new MixtureMaterialPortion(1L);
        existing.setVersion(1);

        doReturn(Optional.of(existing)).when(materialPortionRepository).findById(1L);

        MixtureMaterialPortion update = new MixtureMaterialPortion(1L);
        existing.setVersion(2);

        assertThrows(OptimisticLockException.class, () -> materialPortionService.patchMaterialPortion(1L, update));
    }

    @Test
    public void testExists_ReturnsTrue_WhenRepositoryReturnsTrue() {
        doReturn(true).when(materialPortionRepository).existsById(1L);

        boolean exists = materialPortionService.materialPortionExists(1L);

        assertTrue(exists);
    }

    @Test
    public void testExists_ReturnsFalse_WhenRepositoryReturnsFalse() {
        doReturn(false).when(materialPortionRepository).existsById(1L);

        boolean exists = materialPortionService.materialPortionExists(1L);

        assertFalse(exists);
    }

    @Test
    public void testDelete_CallsDeleteByIdOnRepository() {
        materialPortionService.deleteMaterialPortion(1L);

        verify(materialPortionRepository, times(1)).deleteById(1L);
    }

}
