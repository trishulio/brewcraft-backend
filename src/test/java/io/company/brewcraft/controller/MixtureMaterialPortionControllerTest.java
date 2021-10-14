package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Page;

import io.company.brewcraft.dto.AddMixtureMaterialPortionDto;
import io.company.brewcraft.dto.MaterialLotDto;
import io.company.brewcraft.dto.MixtureMaterialPortionDto;
import io.company.brewcraft.dto.MixtureDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.UpdateMixtureMaterialPortionDto;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Mixture;
import io.company.brewcraft.model.MixtureMaterialPortion;
import io.company.brewcraft.service.MixtureMaterialPortionService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.util.SupportedUnits;
import io.company.brewcraft.util.controller.AttributeFilter;
import tec.uom.se.quantity.Quantities;

public class MixtureMaterialPortionControllerTest {

    private MixtureMaterialPortionController materialPortionController;

    private MixtureMaterialPortionService materialPortionService;

    @BeforeEach
    public void init() {
        materialPortionService = mock(MixtureMaterialPortionService.class);

        materialPortionController = new MixtureMaterialPortionController(materialPortionService, new AttributeFilter());
    }

    @Test
    public void testGetMaterialPortions() {
        MixtureMaterialPortion materialPortion = new MixtureMaterialPortion(1L, new MaterialLot(2L), Quantities.getQuantity(new BigDecimal("10.00"), SupportedUnits.KILOGRAM), new Mixture(3L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        List<MixtureMaterialPortion> materialPortionList = List.of(materialPortion);
        Page<MixtureMaterialPortion> mPage = mock(Page.class);
        doReturn(materialPortionList.stream()).when(mPage).stream();
        doReturn(100).when(mPage).getTotalPages();
        doReturn(1000L).when(mPage).getTotalElements();
        doReturn(mPage).when(materialPortionService).getMaterialPortions(null, null, null, 1, 10, new TreeSet<>(List.of("id")), true);

        PageDto<MixtureMaterialPortionDto> dto = materialPortionController.getMaterialPortions(null, null, null, new TreeSet<>(List.of("id")), true, 1, 10);

        assertEquals(100, dto.getTotalPages());
        assertEquals(1000L, dto.getTotalElements());
        assertEquals(1, dto.getContent().size());
        MixtureMaterialPortionDto materialPortionDto = dto.getContent().get(0);

        assertEquals(1L, materialPortionDto.getId());
        assertEquals(new MaterialLotDto(2L), materialPortionDto.getMaterialLot());
        assertEquals(new QuantityDto("kg", new BigDecimal("10.00")), materialPortionDto.getQuantity());
        assertEquals(new MixtureDto(3L), materialPortionDto.getMixture());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), materialPortionDto.getAddedAt());
        assertEquals(1, materialPortionDto.getVersion());
    }

    @Test
    public void testGetMaterialPortion() {
        MixtureMaterialPortion materialPortion = new MixtureMaterialPortion(1L, new MaterialLot(2L), Quantities.getQuantity(new BigDecimal("10.00"), SupportedUnits.KILOGRAM), new Mixture(3L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(materialPortion).when(materialPortionService).getMaterialPortion(1L);

        MixtureMaterialPortionDto materialPortionDto = materialPortionController.getMaterialPortion(1L);

        assertEquals(1L, materialPortionDto.getId());
        assertEquals(new MaterialLotDto(2L), materialPortionDto.getMaterialLot());
        assertEquals(new QuantityDto("kg", new BigDecimal("10.00")), materialPortionDto.getQuantity());
        assertEquals(new MixtureDto(3L), materialPortionDto.getMixture());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), materialPortionDto.getAddedAt());
        assertEquals(1, materialPortionDto.getVersion());
    }

    @Test
    public void testGetMaterialPortion_ThrowsEntityNotFoundException_WhenServiceReturnsNull() {
        when(materialPortionService.getMaterialPortion(1L)).thenReturn(null);
        assertThrows(EntityNotFoundException.class, () -> materialPortionController.getMaterialPortion(1L),
                "Material Portion not found with id: 1");
    }

    @Test
    public void testAddMaterialPortions() {
        AddMixtureMaterialPortionDto addMaterialPortionDto = new AddMixtureMaterialPortionDto(2L, new QuantityDto("kg", new BigDecimal("100")), 1L, LocalDateTime.of(2018, 1, 2, 3, 4));

        MixtureMaterialPortion materialPortion = new MixtureMaterialPortion(null, new MaterialLot(2L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), new Mixture(1L), LocalDateTime.of(2018, 1, 2, 3, 4), null, null, null);
        MixtureMaterialPortion addedMaterialPortion = new MixtureMaterialPortion(1L, new MaterialLot(2L), Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), new Mixture(3L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        doReturn(List.of(addedMaterialPortion)).when(materialPortionService).addMaterialPortions(List.of(materialPortion));

        List<MixtureMaterialPortionDto> materialPortionDtos = materialPortionController.addMMaterialPortions(List.of(addMaterialPortionDto));

        assertEquals(1L, materialPortionDtos.get(0).getId());
        assertEquals(new MaterialLotDto(2L), materialPortionDtos.get(0).getMaterialLot());
        assertEquals(new QuantityDto("kg", new BigDecimal("100")), materialPortionDtos.get(0).getQuantity());
        assertEquals(new MixtureDto(3L), materialPortionDtos.get(0).getMixture());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), materialPortionDtos.get(0).getAddedAt());
        assertEquals(1, materialPortionDtos.get(0).getVersion());
    }

    @Test
    public void testPutMaterialPortion() {
        UpdateMixtureMaterialPortionDto updateMaterialPortionDto = new UpdateMixtureMaterialPortionDto(2L, new QuantityDto("kg", new BigDecimal("100")), 1L, LocalDateTime.of(2018, 1, 2, 3, 4), 1);

        MixtureMaterialPortion materialPortion = new MixtureMaterialPortion(1L, new MaterialLot(2L), Quantities.getQuantity(new BigDecimal("10.00"), SupportedUnits.KILOGRAM), new Mixture(3L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<MixtureMaterialPortion> putMaterialPortionCaptor = ArgumentCaptor.forClass(MixtureMaterialPortion.class);

        doReturn(materialPortion).when(materialPortionService).putMaterialPortion(eq(1L), putMaterialPortionCaptor.capture());

        MixtureMaterialPortionDto materialPortionDto = materialPortionController.putMaterialPortion(1L, updateMaterialPortionDto);

        // Assert put material portion
        assertEquals(null, putMaterialPortionCaptor.getValue().getId());
        assertEquals(new MaterialLot(2L), putMaterialPortionCaptor.getValue().getMaterialLot());
        assertEquals(Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), putMaterialPortionCaptor.getValue().getQuantity());
        assertEquals(new Mixture(1L), putMaterialPortionCaptor.getValue().getMixture());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), putMaterialPortionCaptor.getValue().getAddedAt());
        assertEquals(null, putMaterialPortionCaptor.getValue().getCreatedAt());
        assertEquals(null, putMaterialPortionCaptor.getValue().getLastUpdated());
        assertEquals(1, putMaterialPortionCaptor.getValue().getVersion());

        // Assert returned material portion
        assertEquals(1L, materialPortionDto.getId());
        assertEquals(new MaterialLotDto(2L), materialPortionDto.getMaterialLot());
        assertEquals(new QuantityDto("kg", new BigDecimal("10.00")), materialPortionDto.getQuantity());
        assertEquals(new MixtureDto(3L), materialPortionDto.getMixture());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), materialPortionDto.getAddedAt());
        assertEquals(1, materialPortionDto.getVersion());
    }

    @Test
    public void testPatchMaterialPortion() {
        UpdateMixtureMaterialPortionDto updateMaterialPortionDto = new UpdateMixtureMaterialPortionDto(2L, new QuantityDto("kg", new BigDecimal("100")), 1L, LocalDateTime.of(2018, 1, 2, 3, 4), 1);

        MixtureMaterialPortion materialPortion = new MixtureMaterialPortion(1L, new MaterialLot(2L), Quantities.getQuantity(new BigDecimal("10.00"), SupportedUnits.KILOGRAM), new Mixture(3L), LocalDateTime.of(2018, 1, 2, 3, 4), LocalDateTime.of(2019, 1, 2, 3, 4), LocalDateTime.of(2020, 1, 2, 3, 4), 1);

        ArgumentCaptor<MixtureMaterialPortion> patchMaterialPortionCaptor = ArgumentCaptor.forClass(MixtureMaterialPortion.class);

        doReturn(materialPortion).when(materialPortionService).patchMaterialPortion(eq(1L), patchMaterialPortionCaptor.capture());

        MixtureMaterialPortionDto materialPortionDto = materialPortionController.patchMaterialPortion(1L, updateMaterialPortionDto);

        // Assert patch material portion
        assertEquals(null, patchMaterialPortionCaptor.getValue().getId());
        assertEquals(new MaterialLot(2L), patchMaterialPortionCaptor.getValue().getMaterialLot());
        assertEquals(Quantities.getQuantity(new BigDecimal("100"), SupportedUnits.KILOGRAM), patchMaterialPortionCaptor.getValue().getQuantity());
        assertEquals(new Mixture(1L), patchMaterialPortionCaptor.getValue().getMixture());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), patchMaterialPortionCaptor.getValue().getAddedAt());
        assertEquals(null, patchMaterialPortionCaptor.getValue().getCreatedAt());
        assertEquals(null, patchMaterialPortionCaptor.getValue().getLastUpdated());
        assertEquals(1, patchMaterialPortionCaptor.getValue().getVersion());

        // Assert returned material portion
        assertEquals(1L, materialPortionDto.getId());
        assertEquals(new MaterialLotDto(2L), materialPortionDto.getMaterialLot());
        assertEquals(new QuantityDto("kg", new BigDecimal("10.00")), materialPortionDto.getQuantity());
        assertEquals(new MixtureDto(3L), materialPortionDto.getMixture());
        assertEquals(LocalDateTime.of(2018, 1, 2, 3, 4), materialPortionDto.getAddedAt());
        assertEquals(1, materialPortionDto.getVersion());
    }

    @Test
    public void testDeleteMaterialPortion() {
        materialPortionController.deleteMaterialPortion(1L);

        verify(materialPortionService, times(1)).deleteMaterialPortion(1L);
    }
}
