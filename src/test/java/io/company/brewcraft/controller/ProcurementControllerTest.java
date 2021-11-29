package io.company.brewcraft.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import io.company.brewcraft.dto.procurement.AddProcurementDto;
import io.company.brewcraft.dto.procurement.ProcurementDto;
import io.company.brewcraft.dto.procurement.ProcurementIdDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementDto;
import io.company.brewcraft.model.procurement.BaseProcurement;
import io.company.brewcraft.model.procurement.BaseProcurementItem;
import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.model.procurement.ProcurementId;
import io.company.brewcraft.model.procurement.UpdateProcurement;
import io.company.brewcraft.model.procurement.UpdateProcurementItem;

public class ProcurementControllerTest {

    private ProcurementController controller;

    private CrudControllerService<
        ProcurementId,
        Procurement,
        BaseProcurement<? extends BaseProcurementItem>,
        UpdateProcurement<? extends UpdateProcurementItem>,
        ProcurementDto,
        AddProcurementDto,
        UpdateProcurementDto
    > mCrudController;

    @BeforeEach
    public void init() {
        mCrudController = mock(CrudControllerService.class);
        controller = new ProcurementController(mCrudController, null);
    }

    @Test
    public void testGet_ReturnsSingleProcurementFromCrudController() {
        doReturn(new ProcurementDto(new ProcurementIdDto(1L, 10L, 100L))).when(mCrudController).get(new ProcurementId(1L, 10L, 100L), Set.of(""));

        ProcurementDto dto = controller.get(1L, 10L, 100L, Set.of(""));

        ProcurementDto expected = new ProcurementDto(new ProcurementIdDto(1L, 10L, 100L));
        assertEquals(expected, dto);
    }

    @Test
    public void testAdd_ReturnsDtoFromCrudAdd() {
        doAnswer(inv -> {
            List<AddProcurementDto> addDtos = inv.getArgument(0, List.class);
            assertEquals(List.of(new AddProcurementDto()), addDtos);
            return List.of(new ProcurementDto(new ProcurementIdDto(1L, 10L, 100L)));
        }).when(mCrudController).add(anyList());

        List<ProcurementDto> dtos = controller.add(List.of(new AddProcurementDto()));

        List<ProcurementDto> expected = List.of(new ProcurementDto(new ProcurementIdDto(1L, 10L, 100L)));
        assertEquals(expected, dtos);
    }

    @Test
    public void testPut_ReturnsDtoFromCrudPut() {
        doAnswer(inv -> {
            List<UpdateProcurementDto> updateDtos = inv.getArgument(0, List.class);
            assertEquals(List.of(new UpdateProcurementDto()), updateDtos);
            return List.of(new ProcurementDto(new ProcurementIdDto(1L, 10L, 100L)));
        }).when(mCrudController).put(anyList());

        List<ProcurementDto> dtos = controller.put(List.of(new UpdateProcurementDto()));

        List<ProcurementDto> expected = List.of(new ProcurementDto(new ProcurementIdDto(1L, 10L, 100L)));
        assertEquals(expected, dtos);
    }

    @Test
    public void testPatch_ReturnsDtoFromCrudPatch() {
        doAnswer(inv -> {
            List<UpdateProcurementDto> updateDtos = inv.getArgument(0, List.class);
            assertEquals(List.of(new UpdateProcurementDto()), updateDtos);
            return List.of(new ProcurementDto(new ProcurementIdDto(1L, 10L, 100L)));
        }).when(mCrudController).patch(anyList());

        List<ProcurementDto> dtos = controller.patch(List.of(new UpdateProcurementDto()));

        List<ProcurementDto> expected = List.of(new ProcurementDto(new ProcurementIdDto(1L, 10L, 100L)));
        assertEquals(expected, dtos);
    }

    @Test
    public void testDelete_ReturnsCountFromCrudDelete() {
        ArgumentCaptor<Set<ProcurementId>> captor = ArgumentCaptor.forClass(Set.class);
        doReturn(3).when(mCrudController).delete(captor.capture());

        int count = controller.delete(Set.of(new ProcurementIdDto(1L, 1L, 1L), new ProcurementIdDto(2L, 2L, 2L), new ProcurementIdDto(3L, 3L, 3L)));

        assertEquals(3, count);
        assertThat(captor.getValue()).hasSameElementsAs(Set.of(new ProcurementId(1L, 1L, 1L), new ProcurementId(2L, 2L, 2L), new ProcurementId(3L, 3L, 3L)));
    }
}
