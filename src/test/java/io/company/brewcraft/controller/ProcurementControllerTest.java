package io.company.brewcraft.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.PageImpl;

import io.company.brewcraft.dto.PageDto;
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
import io.company.brewcraft.service.impl.procurement.ProcurementService;

public class ProcurementControllerTest {

    private ProcurementController controller;

    private ProcurementService mService;
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
        mService = mock(ProcurementService.class);
        mCrudController = mock(CrudControllerService.class);
        controller = new ProcurementController(mCrudController, mService);
    }

    @Test
    public void testGetAll_ReturnsPageFromServiceAndConvertsWithCrudController() {
        doReturn(new PageImpl<>(List.of(new Procurement(new ProcurementId(1L, 10L))))).when(mService).getAll(
            Set.of(1L), // shipmentIds
            Set.of(2L), // shipmentExcludeIds
            Set.of("SHIPMENT_NUMBER"), // shipmentNumbers
            Set.of("SHIPMENT_DESCRIPTION"), // shipmentDescriptions
            Set.of(3L), //shipmentStatusIds
            LocalDateTime.of(2000, 1, 1, 0, 0), // deliveryDueDateFrom
            LocalDateTime.of(2001, 1, 1, 0, 0), // deliveryDueDateTo
            LocalDateTime.of(2000, 1, 1, 0, 0), // deliveredDueDateFrom
            LocalDateTime.of(2001, 1, 1, 0, 0), // deliveredDueDateTo
            Set.of(10L), // invoiceIds
            Set.of(2L), // invoiceExcludeIds
            Set.of("Invoice Number"), // invoiceNumbers
            Set.of("invoice description"), // invoiceDescriptions
            Set.of("item description"), // itemDescriptions
            LocalDateTime.of(1999, 1, 1, 12, 0), // generatedOnFrom
            LocalDateTime.of(2000, 1, 1, 12, 0), // generatedOnTo
            LocalDateTime.of(2001, 1, 1, 12, 0), // receivedOnFrom
            LocalDateTime.of(2002, 1, 1, 12, 0), // receivedOnTo
            LocalDateTime.of(2003, 1, 1, 12, 0), // paymentDueDateFrom
            LocalDateTime.of(2004, 1, 1, 12, 0), // paymentDueDateTo
            Set.of(3L), // purchaseOrderIds
            Set.of(4L), // materialIds
            new BigDecimal("1"), // amtFrom
            new BigDecimal("2"), // amtTo
            new BigDecimal("3"), // freightAmtFrom
            new BigDecimal("4"), // freightAmtTo
            Set.of(99L), // invoiceStatusIds
            Set.of(4L), // supplierIds
            new TreeSet<>(List.of("id")), // sort
            true, // order_asc
            1, // page
            10 // size
        );

        doReturn(new PageDto<>(List.of(new ProcurementDto(new ProcurementIdDto(1L, 10L))), 1, 1)).when(mCrudController).getAll(new PageImpl<>(List.of(new Procurement(new ProcurementId(1L, 10L)))), Set.of("attr"));

        PageDto<ProcurementDto> procurements = controller.getAll(
            Set.of(1L), // shipmentIds
            Set.of(2L), // shipmentExcludeIds
            Set.of("SHIPMENT_NUMBER"), // shipmentNumbers
            Set.of("SHIPMENT_DESCRIPTION"), // shipmentDescriptions
            Set.of(3L), //shipmentStatusIds
            LocalDateTime.of(2000, 1, 1, 0, 0), // deliveryDueDateFrom
            LocalDateTime.of(2001, 1, 1, 0, 0), // deliveryDueDateTo
            LocalDateTime.of(2000, 1, 1, 0, 0), // deliveredDueDateFrom
            LocalDateTime.of(2001, 1, 1, 0, 0), // deliveredDueDateTo
            Set.of(10L), // invoiceIds
            Set.of(2L), // invoiceExcludeIds
            Set.of("Invoice Number"), // invoiceNumbers
            Set.of("invoice description"), // invoiceDescriptions
            Set.of("item description"), // itemDescriptions
            LocalDateTime.of(1999, 1, 1, 12, 0), // generatedOnFrom
            LocalDateTime.of(2000, 1, 1, 12, 0), // generatedOnTo
            LocalDateTime.of(2001, 1, 1, 12, 0), // receivedOnFrom
            LocalDateTime.of(2002, 1, 1, 12, 0), // receivedOnTo
            LocalDateTime.of(2003, 1, 1, 12, 0), // paymentDueDateFrom
            LocalDateTime.of(2004, 1, 1, 12, 0), // paymentDueDateTo
            Set.of(3L), // purchaseOrderIds
            Set.of(4L), // materialIds
            new BigDecimal("1"), // amtFrom
            new BigDecimal("2"), // amtTo
            new BigDecimal("3"), // freightAmtFrom
            new BigDecimal("4"), // freightAmtTo
            Set.of(99L), // invoiceStatusIds
            Set.of(4L), // supplierIds
            new TreeSet<>(List.of("id")), // sort
            true, // order_asc
            1, // page
            10, // size
            Set.of("attr")
        );

        assertEquals(new PageDto<>(List.of(new ProcurementDto(new ProcurementIdDto(1L, 10L))), 1, 1), procurements);
    }

    @Test
    public void testGet_ReturnsSingleProcurementFromCrudController() {
        doReturn(new ProcurementDto(new ProcurementIdDto(1L, 10L))).when(mCrudController).get(new ProcurementId(1L, 10L), Set.of(""));

        ProcurementDto dto = controller.get(1L, 10L, Set.of(""));

        ProcurementDto expected = new ProcurementDto(new ProcurementIdDto(1L, 10L));
        assertEquals(expected, dto);
    }

    @Test
    public void testAdd_ReturnsDtoFromCrudAdd() {
        doAnswer(inv -> {
            List<AddProcurementDto> addDtos = inv.getArgument(0, List.class);
            assertEquals(List.of(new AddProcurementDto()), addDtos);
            return List.of(new ProcurementDto(new ProcurementIdDto(1L, 10L)));
        }).when(mCrudController).add(anyList());

        List<ProcurementDto> dtos = controller.add(List.of(new AddProcurementDto()));

        List<ProcurementDto> expected = List.of(new ProcurementDto(new ProcurementIdDto(1L, 10L)));
        assertEquals(expected, dtos);
    }

    @Test
    public void testPut_ReturnsDtoFromCrudPut() {
        doAnswer(inv -> {
            List<UpdateProcurementDto> updateDtos = inv.getArgument(0, List.class);
            assertEquals(List.of(new UpdateProcurementDto()), updateDtos);
            return List.of(new ProcurementDto(new ProcurementIdDto(1L, 10L)));
        }).when(mCrudController).put(anyList());

        List<ProcurementDto> dtos = controller.put(List.of(new UpdateProcurementDto()));

        List<ProcurementDto> expected = List.of(new ProcurementDto(new ProcurementIdDto(1L, 10L)));
        assertEquals(expected, dtos);
    }

    @Test
    public void testPatch_ReturnsDtoFromCrudPatch() {
        doAnswer(inv -> {
            List<UpdateProcurementDto> updateDtos = inv.getArgument(0, List.class);
            assertEquals(List.of(new UpdateProcurementDto()), updateDtos);
            return List.of(new ProcurementDto(new ProcurementIdDto(1L, 10L)));
        }).when(mCrudController).patch(anyList());

        List<ProcurementDto> dtos = controller.patch(List.of(new UpdateProcurementDto()));

        List<ProcurementDto> expected = List.of(new ProcurementDto(new ProcurementIdDto(1L, 10L)));
        assertEquals(expected, dtos);
    }

    @Test
    public void testDelete_ReturnsCountFromCrudDelete() {
        ArgumentCaptor<Set<ProcurementId>> captor = ArgumentCaptor.forClass(Set.class);
        doReturn(3).when(mCrudController).delete(captor.capture());

        long count = controller.delete(Set.of(new ProcurementIdDto(1L, 1L), new ProcurementIdDto(2L, 2L), new ProcurementIdDto(3L, 3L)));

        assertEquals(3, count);
        assertThat(captor.getValue()).hasSameElementsAs(Set.of(new ProcurementId(1L, 1L), new ProcurementId(2L, 2L), new ProcurementId(3L, 3L)));
    }
}
