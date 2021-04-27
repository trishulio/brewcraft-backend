package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import io.company.brewcraft.dto.*;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.impl.ShipmentService;
import io.company.brewcraft.util.controller.AttributeFilter;
import io.company.brewcraft.utils.SupportedUnits;
import tec.uom.se.quantity.Quantities;

public class ShipmentControllerTest {

    ShipmentController controller;
    ShipmentService mService;
    private AttributeFilter filter;
    
    @BeforeEach
    public void init() {
        mService = mock(ShipmentService.class);
        filter = new AttributeFilter();
        controller = new ShipmentController(mService, filter);
    }
    
    @Test
    public void testGetShipment_ReturnsShipmentDto_WhenServiceReturnsShipment() {
        doReturn(new Shipment(1L)).when(mService).getShipment(anyLong());
        
        ShipmentDto dto = controller.getShipment(1L);
        
        assertEquals(1L, dto.getId());
    }
    
    @Test
    public void testGetShipment_ThrowsEntityNotFoundException_WhenShipmentDoesNotExist() { 
        doReturn(null).when(mService).getShipment(anyLong());
        assertThrows(EntityNotFoundException.class, () -> controller.getShipment(1L), "Shipment not found with Id: 1");
    }
    
    @Test
    public void testGetShipments_ReturnsPageDtoWithAllAttributes_WhenServiceAttributesAreEmptyString() {
        List<MaterialLot> lots = List.of( 
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), new Material(1L), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 2)
        );
        Shipment shipment = new Shipment(
            1L,
            "SHIPMENT_1",
            "DESCRIPTION_1",
            new ShipmentStatus("RECEIVED"),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            lots,
            1
        );
        
        Page<Shipment> mPage = mock(Page.class);
        doReturn(List.of(shipment).stream()).when(mPage).stream();
        doReturn(10).when(mPage).getTotalPages();
        doReturn(1000L).when(mPage).getTotalElements();
        
        doReturn(mPage).when(mService).getShipments(
            Set.of(1L),
            Set.of(2L),
            Set.of("SHIPMENT_1"),
            Set.of("DESC_1"),
            Set.of("RECEIVED"),
            LocalDateTime.of(1999, 1, 1, 12, 0, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0, 0),
            Set.of("col_1"),
            true,
            1,
            10
        );
        
        PageDto<ShipmentDto> dto = controller.getShipments(
                                    Set.of(1L),
                                    Set.of(2L),
                                    Set.of("SHIPMENT_1"),
                                    Set.of("DESC_1"),
                                    Set.of("RECEIVED"),
                                    LocalDateTime.of(1999, 1, 1, 12, 0, 0),
                                    LocalDateTime.of(2000, 1, 1, 12, 0, 0),
                                    LocalDateTime.of(2001, 1, 1, 12, 0, 0),
                                    LocalDateTime.of(2002, 1, 1, 12, 0, 0),
                                    Set.of("col_1"),
                                    true,
                                    1,
                                    10,
                                    Set.of("")
                                );
        assertEquals(1L, shipment.getId());
        assertEquals("SHIPMENT_1", shipment.getShipmentNumber());
        assertEquals("DESCRIPTION_1", shipment.getDescription());
        assertEquals(new ShipmentStatus("RECEIVED"), shipment.getStatus());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), shipment.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), shipment.getDeliveredDate());
        assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), shipment.getCreatedAt());
        assertEquals(LocalDateTime.of(2002, 1, 1, 12, 0), shipment.getLastUpdated());
        assertEquals(1, shipment.getLots().size());
        assertEquals(1, shipment.getVersion());
        MaterialLot lot = shipment.getLots().iterator().next();
        assertEquals(1L, lot.getId());
        assertEquals(Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), lot.getQuantity());
        assertEquals(shipment, lot.getShipment());
        assertEquals(new Material(1L), lot.getMaterial());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0, 0), lot.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0, 0), lot.getLastUpdated());
        assertEquals(2, lot.getVersion());
    }
    
    @Test
    public void testGetShipments_ReturnsPageDtoWithIdFieldOnly_WhenAttributesHaveIdOnly() {
        List<MaterialLot> lots = List.of( 
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), new Material(1L), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 2)
        );
        Shipment shipment = new Shipment(
            1L,
            "SHIPMENT_1",
            "DESCRIPTION_1",
            new ShipmentStatus("RECEIVED"),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            lots,
            1
        );
        
        Page<Shipment> mPage = mock(Page.class);
        doReturn(List.of(shipment).stream()).when(mPage).stream();
        doReturn(10).when(mPage).getTotalPages();
        doReturn(1000L).when(mPage).getTotalElements();
        
        doReturn(mPage).when(mService).getShipments(
            Set.of(1L),
            Set.of(2L),
            Set.of("SHIPMENT_1"),
            Set.of("DESC_1"),
            Set.of("RECEIVED"),
            LocalDateTime.of(1999, 1, 1, 12, 0, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0, 0),
            Set.of("col_1"),
            true,
            1,
            10
        );
        
        PageDto<ShipmentDto> dto = controller.getShipments(
                                        Set.of(1L),
                                        Set.of(2L),
                                        Set.of("SHIPMENT_1"),
                                        Set.of("DESC_1"),
                                        Set.of("RECEIVED"),
                                        LocalDateTime.of(1999, 1, 1, 12, 0, 0),
                                        LocalDateTime.of(2000, 1, 1, 12, 0, 0),
                                        LocalDateTime.of(2001, 1, 1, 12, 0, 0),
                                        LocalDateTime.of(2002, 1, 1, 12, 0, 0),
                                        Set.of("col_1"),
                                        true,
                                        1,
                                        10,
                                        Set.of("id")
                                    );
        assertEquals(new ShipmentDto(1L), dto.getContent().get(0));
    }
    
    @Test
    public void testDeleteShipments_CallsServiceWithSetOfIds_WhenIdsAreNotNull() {
        doReturn(99).when(mService).delete(eq(Set.of(1L, 2L, 3L)));

        int deleteCount = controller.deleteShipments(Set.of(1L, 2L, 3L));
        assertEquals(99, deleteCount);
    }

    @Test
    public void testAddShipment_ReturnsPutShipmentDto_WhenServiceReturnsPutShipment() {
        doAnswer(i -> i.getArgument(0, Shipment.class)).when(mService).add(any(Shipment.class));
        
        AddShipmentDto addDto = new AddShipmentDto(
            "SHIPMENT_1",
            "DESCRIPTION_1",
            "RECEIVED",
            LocalDateTime.of(1999, 1, 1, 12, 0, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0, 0),
            Set.of(
                new AddMaterialLotDto("LOT_1", new QuantityDto("kg", new BigDecimal("10")), 2L, 1L, 3L)
            )
        );
        
        ShipmentDto dto = controller.addShipment(addDto);

        assertEquals(null, dto.getId());
        assertEquals("SHIPMENT_1", dto.getShipmentNumber());
        assertEquals("DESCRIPTION_1", dto.getDescription());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0, 0), dto.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0, 0), dto.getDeliveredDate());
        assertEquals(new ShipmentStatusDto("RECEIVED"), dto.getStatus());
        assertEquals(null, dto.getVersion());
        assertEquals(1, dto.getLots().size());
        MaterialLotDto lot = dto.getLots().iterator().next();
        assertEquals(null, lot.getId());
        assertEquals(new QuantityDto("kg", new BigDecimal("10")), lot.getQuantity());
        assertEquals(new MaterialDto(2L), lot.getMaterial());
        assertEquals(null, lot.getVersion());
    }
    
    @Test
    public void testPutShipment_ReturnsPutShipmentDto_WhenServiceReturnsPutShipment() {
        doAnswer(i -> {
            Shipment shipment = i.getArgument(1, Shipment.class);
            shipment.setId(i.getArgument(0, Long.class));
            return shipment;
        }).when(mService).put(anyLong(), any(Shipment.class));
        
        UpdateShipmentDto updateDto = new UpdateShipmentDto(
            "SHIPMENT_1",
            "DESCRIPTION_1",
            "RECEIVED",
            LocalDateTime.of(1999, 1, 1, 12, 0, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0, 0),
            Set.of(
                new UpdateMaterialLotDto(1L, "LOT_1", new QuantityDto("kg", new BigDecimal("10")), 2L, 1L, 3L, 1)
            ),
            1
        );
        
        ShipmentDto dto = controller.putShipment(2L, updateDto);
        
        assertEquals(2L, dto.getId());
        assertEquals("SHIPMENT_1", dto.getShipmentNumber());
        assertEquals("DESCRIPTION_1", dto.getDescription());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0, 0), dto.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0, 0), dto.getDeliveredDate());
        assertEquals(new ShipmentStatusDto("RECEIVED"), dto.getStatus());
        assertEquals(1, dto.getVersion());
        assertEquals(1, dto.getLots().size());
        MaterialLotDto lot = dto.getLots().iterator().next();
        assertEquals(1L, lot.getId());
        assertEquals(new QuantityDto("kg", new BigDecimal("10")), lot.getQuantity());
        assertEquals(new MaterialDto(2L), lot.getMaterial());
        assertEquals(1, lot.getVersion());
    }
    
    @Test
    public void testPatch_ReturnsPatchShipmentDto_WhenServiceReturnsPatchShipment() {
        doAnswer(i -> {
            Shipment shipment = i.getArgument(1, Shipment.class);
            shipment.setId(i.getArgument(0, Long.class));
            return shipment;
        }).when(mService).patch(anyLong(), any(Shipment.class));
        
        UpdateShipmentDto updateDto = new UpdateShipmentDto(
            "SHIPMENT_1",
            "DESCRIPTION_1",
            "RECEIVED",
            LocalDateTime.of(1999, 1, 1, 12, 0, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0, 0),
            Set.of(
                new UpdateMaterialLotDto(1L, "LOT_1", new QuantityDto("kg", new BigDecimal("10")), 2L, 1L, 3L, 1)
            ),
            1
        );
        
        ShipmentDto dto = controller.patchShipment(2L, updateDto);
        
        assertEquals(2L, dto.getId());
        assertEquals("SHIPMENT_1", dto.getShipmentNumber());
        assertEquals("DESCRIPTION_1", dto.getDescription());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0, 0), dto.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0, 0), dto.getDeliveredDate());
        assertEquals(new ShipmentStatusDto("RECEIVED"), dto.getStatus());
        assertEquals(1, dto.getVersion());
        assertEquals(1, dto.getLots().size());
        MaterialLotDto lot = dto.getLots().iterator().next();
        assertEquals(1L, lot.getId());
        assertEquals(new QuantityDto("kg", new BigDecimal("10")), lot.getQuantity());
        assertEquals(new MaterialDto(2L), lot.getMaterial());
        assertEquals(1, lot.getVersion());
    }
}
