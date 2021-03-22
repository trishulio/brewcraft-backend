package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.company.brewcraft.dto.InvoiceDto;
import io.company.brewcraft.dto.MaterialDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.ShipmentDto;
import io.company.brewcraft.dto.ShipmentItemDto;
import io.company.brewcraft.dto.ShipmentStatusDto;
import io.company.brewcraft.dto.UpdateShipmentDto;
import io.company.brewcraft.dto.UpdateShipmentItemDto;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.impl.ShipmentService;

public class ShipmentControllerTest {

    ShipmentController controller;
    ShipmentService mService;
    
    @BeforeEach
    public void init() {
        mService = mock(ShipmentService.class);
        controller = new ShipmentController(mService);
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
    public void testDeleteShipments_CallsServiceWithSetOfIds_WhenIdsAreNotNull() {
        doReturn(99).when(mService).delete(eq(Set.of(1L, 2L, 3L)));

        int deleteCount = controller.deleteShipments(Set.of(1L, 2L, 3L));
        assertEquals(99, deleteCount);
    }

    @Test
    public void testAddShipment_ReturnsPutShipmentDto_WhenServiceReturnsPutShipment() {
        doAnswer(i -> {
            Shipment shipment = i.getArgument(1, Shipment.class);
            shipment.setInvoice(new Invoice(i.getArgument(0, Long.class)));
            return shipment;
        }).when(mService).add(anyLong(), any(Shipment.class));
        
        UpdateShipmentDto addDto = new UpdateShipmentDto(
            "SHIPMENT_1",
            "LOT_1",
            "DESCRIPTION_1",
            "RECEIVED",
            1L,
            LocalDateTime.of(1999, 1, 1, 12, 0, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0, 0),
            Set.of(
                new UpdateShipmentItemDto(1L, new QuantityDto("kg", new BigDecimal("10.00")), 2L, 1)
            ),
            1
        );
        
        ShipmentDto dto = controller.addShipment(addDto);

        assertEquals(null, dto.getId());
        assertEquals("SHIPMENT_1", dto.getShipmentNumber());
        assertEquals("LOT_1", dto.getLotNumber());
        assertEquals("DESCRIPTION_1", dto.getDescription());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0, 0), dto.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0, 0), dto.getDeliveredDate());
        assertEquals(new ShipmentStatusDto("RECEIVED"), dto.getStatus());
        assertEquals(new InvoiceDto(1L), dto.getInvoice());
        assertEquals(1, dto.getVersion());
        assertEquals(1, dto.getItems().size());
        ShipmentItemDto item = dto.getItems().iterator().next();
        assertEquals(1L, item.getId());
        assertEquals(new QuantityDto("kg", new BigDecimal("10.00")), item.getQuantity());
        assertEquals(new MaterialDto(2L), item.getMaterial());
        assertEquals(1, item.getVersion());
    }
    
    @Test
    public void testPutShipment_ReturnsPutShipmentDto_WhenServiceReturnsPutShipment() {
        doAnswer(i -> {
            Shipment shipment = i.getArgument(2, Shipment.class);
            shipment.setId(i.getArgument(1, Long.class));
            shipment.setInvoice(new Invoice(i.getArgument(0, Long.class)));
            return shipment;
        }).when(mService).put(anyLong(), anyLong(), any(Shipment.class));
        
        UpdateShipmentDto updateDto = new UpdateShipmentDto(
            "SHIPMENT_1",
            "LOT_1",
            "DESCRIPTION_1",
            "RECEIVED",
            1L,
            LocalDateTime.of(1999, 1, 1, 12, 0, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0, 0),
            Set.of(
                new UpdateShipmentItemDto(1L, new QuantityDto("kg", new BigDecimal("10.00")), 2L, 1)
            ),
            1
        );
        
        ShipmentDto dto = controller.putShipment(2L, updateDto);
        
        assertEquals(2L, dto.getId());
        assertEquals("SHIPMENT_1", dto.getShipmentNumber());
        assertEquals("LOT_1", dto.getLotNumber());
        assertEquals("DESCRIPTION_1", dto.getDescription());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0, 0), dto.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0, 0), dto.getDeliveredDate());
        assertEquals(new ShipmentStatusDto("RECEIVED"), dto.getStatus());
        assertEquals(new InvoiceDto(1L), dto.getInvoice());
        assertEquals(1, dto.getVersion());
        assertEquals(1, dto.getItems().size());
        ShipmentItemDto item = dto.getItems().iterator().next();
        assertEquals(1L, item.getId());
        assertEquals(new QuantityDto("kg", new BigDecimal("10.00")), item.getQuantity());
        assertEquals(new MaterialDto(2L), item.getMaterial());
        assertEquals(1, item.getVersion());
    }
    
    @Test
    public void testPatch_ReturnsPatchShipmentDto_WhenServiceReturnsPatchShipment() {
        doAnswer(i -> {
            Shipment shipment = i.getArgument(2, Shipment.class);
            shipment.setId(i.getArgument(1, Long.class));
            shipment.setInvoice(new Invoice(i.getArgument(0, Long.class)));
            return shipment;
        }).when(mService).patch(anyLong(), anyLong(), any(Shipment.class));
        
        UpdateShipmentDto updateDto = new UpdateShipmentDto(
            "SHIPMENT_1",
            "LOT_1",
            "DESCRIPTION_1",
            "RECEIVED",
            1L,
            LocalDateTime.of(1999, 1, 1, 12, 0, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0, 0),
            Set.of(
                new UpdateShipmentItemDto(1L, new QuantityDto("kg", new BigDecimal("10.00")), 2L, 1)
            ),
            1
        );
        
        ShipmentDto dto = controller.patchShipment(2L, updateDto);
        
        assertEquals(2L, dto.getId());
        assertEquals("SHIPMENT_1", dto.getShipmentNumber());
        assertEquals("LOT_1", dto.getLotNumber());
        assertEquals("DESCRIPTION_1", dto.getDescription());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0, 0), dto.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0, 0), dto.getDeliveredDate());
        assertEquals(new ShipmentStatusDto("RECEIVED"), dto.getStatus());
        assertEquals(new InvoiceDto(1L), dto.getInvoice());
        assertEquals(1, dto.getVersion());
        assertEquals(1, dto.getItems().size());
        ShipmentItemDto item = dto.getItems().iterator().next();
        assertEquals(1L, item.getId());
        assertEquals(new QuantityDto("kg", new BigDecimal("10.00")), item.getQuantity());
        assertEquals(new MaterialDto(2L), item.getMaterial());
        assertEquals(1, item.getVersion());
    }
}
