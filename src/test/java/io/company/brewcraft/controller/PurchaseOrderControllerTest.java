package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import io.company.brewcraft.dto.AddPurchaseOrderDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.PurchaseOrderDto;
import io.company.brewcraft.dto.SupplierDto;
import io.company.brewcraft.dto.UpdatePurchaseOrderDto;
import io.company.brewcraft.model.PurchaseOrder;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.service.PurchaseOrderService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.util.controller.AttributeFilter;

public class PurchaseOrderControllerTest {

    private PurchaseOrderService mService;

    private PurchaseOrderController controller;

    @BeforeEach
    public void init() {
        mService = mock(PurchaseOrderService.class);
        controller = new PurchaseOrderController(mService, new AttributeFilter());
    }

    @Test
    public void testGetPurchaseOrder_ThrowsEntityNotFoundException_WhenPurchaseOrderDoesNotExist() {
        doReturn(null).when(mService).get(1L);

        assertThrows(EntityNotFoundException.class, () -> controller.getPurchaseOrder(1L));
    }

    @Test
    public void testGetPurchaseOrder_ReturnsPurchaseOrderDto_WhenPurchaseOrderExists() {
        PurchaseOrder po = new PurchaseOrder(
            1L,
            "ORDER_1",
            new Supplier(2L),
            LocalDateTime.of(2000, 1, 1, 0, 0),
            LocalDateTime.of(2001, 1, 1, 0, 0),
            1
        );

        doReturn(po).when(mService).get(1L);

        PurchaseOrderDto dto = controller.getPurchaseOrder(1L);

        PurchaseOrderDto expected = new PurchaseOrderDto(
            1L,
            "ORDER_1",
            new SupplierDto(2L),
            LocalDateTime.of(2000, 1, 1, 0, 0),
            LocalDateTime.of(2001, 1, 1, 0, 0),
            1
        );

        assertEquals(expected, dto);
    }

    @Test
    public void testGetPurchaseOrders_ReturnsPurchaseOrderPageDtoMatchedRequestParameters_WhenAttributeValuesProvided() {
        Page<PurchaseOrder> poPage = new PageImpl<>(List.of(
            new PurchaseOrder(
                1L,
                "ORDER_1",
                new Supplier(2L),
                LocalDateTime.of(2000, 1, 1, 0, 0),
                LocalDateTime.of(2001, 1, 1, 0, 0),
                1
            )
        ));
        doReturn(poPage).when(mService).getPurchaseOrders(
            Set.of(1L), //ids
            Set.of(2L), //excludeIds
            Set.of("ORDER_1"), //orderNumbers
            Set.of(3L), //supplierIds
            new TreeSet<>(List.of("id")), //sortBy
            true, //orderAscending
            10, //page
            20 //size
        );

        final PageDto<PurchaseOrderDto> pageDto = controller.getAllPurchaseOrders(
            Set.of(1L),
            Set.of(2L),
            Set.of("ORDER_1"),
            Set.of(3L),
            new TreeSet<>(List.of("id")),
            true,
            10,
            20,
            new HashSet<>()
        );

        PageDto<PurchaseOrderDto> expected = new PageDto<PurchaseOrderDto>(
            List.of(
                new PurchaseOrderDto(
                    1L,
                    "ORDER_1",
                    new SupplierDto(2L),
                    LocalDateTime.of(2000, 1, 1, 0, 0),
                    LocalDateTime.of(2001, 1, 1, 0, 0),
                    1
                )
            ),
            1,
            1
        );

        assertEquals(expected, pageDto);
    }

    @Test
    public void testGetPurchaseOrder_ReturnsPurchaseOrderDto_WhenServiceReturnPurchaseOrder() {
        PurchaseOrder po = new PurchaseOrder(
            1L,
            "ORDER_1",
            new Supplier(2L),
            LocalDateTime.of(2000, 1, 1, 0, 0),
            LocalDateTime.of(2001, 1, 1, 0, 0),
            1
        );
        doReturn(po).when(mService).get(1L);

        PurchaseOrderDto dto = controller.getPurchaseOrder(1L);

        PurchaseOrderDto expected = new PurchaseOrderDto(
            1L,
            "ORDER_1",
            new SupplierDto(2L),
            LocalDateTime.of(2000, 1, 1, 0, 0),
            LocalDateTime.of(2001, 1, 1, 0, 0),
            1
        );
        assertEquals(expected, dto);
    }

    @Test
    public void testGetPurchaseOrder_ThrowsEntityNotFoundException_WhenServiceReturnsNull() {
        doReturn(null).when(mService).get(1L);
        assertThrows(EntityNotFoundException.class, () -> controller.getPurchaseOrder(1L));
    }

    @Test
    public void testAddPurchaseOrder_ReturnsPurchaseOrderDtoFromService_WhenInputArgIsNotNull() {
        doAnswer(inv -> inv.getArgument(0)).when(mService).add(anyList());
        AddPurchaseOrderDto additionDto = new AddPurchaseOrderDto(
            "ORDER_1",
            2L
        );

        PurchaseOrderDto dto = controller.postPurchaseOrder(additionDto);

        PurchaseOrderDto expected = new PurchaseOrderDto(
            null,
            "ORDER_1",
            new SupplierDto(2L),
            null,
            null,
            null
        );

        assertEquals(expected, dto);
    }

    @Test
    public void testPutPurchaseOrder_ReturnsPurchaseOrderDtoFromService_WhenInputArgIsNotNull() {
        doAnswer(inv -> inv.getArgument(0)).when(mService).put(anyList());
        UpdatePurchaseOrderDto updateDto = new UpdatePurchaseOrderDto(
            "ORDER_1",
            2L,
            1
        );

        PurchaseOrderDto dto = controller.putPurchaseOrder(1L, updateDto);

        PurchaseOrderDto expected = new PurchaseOrderDto(
            null,
            "ORDER_1",
            new SupplierDto(2L),
            null,
            null,
            1
        );

        assertEquals(expected, dto);
    }

    @Test
    public void testPatchPurchaseOrder_ReturnsPurchaseOrderDtoFromService_WhenInputArgIsNotNull() {
        doAnswer(inv -> inv.getArgument(0)).when(mService).patch(anyList());
        UpdatePurchaseOrderDto updateDto = new UpdatePurchaseOrderDto(
            "ORDER_1",
            2L,
            1
        );

        PurchaseOrderDto dto = controller.patchPurchaseOrder(1L, updateDto);

        PurchaseOrderDto expected = new PurchaseOrderDto(
            null,
            "ORDER_1",
            new SupplierDto(2L),
            null,
            null,
            1
        );

        assertEquals(expected, dto);
    }

    @Test
    public void testDeletePurchaseOrders_DeletesPurchaseOrders_WhenPurchaseOrderIdsAreProvided() {
        controller.deletePurchaseOrder(Set.of(1L, 2L, 3L));

        verify(mService, times(1)).delete(Set.of(1L, 2L, 3L));
    }
}
