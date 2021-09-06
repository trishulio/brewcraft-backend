package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import io.company.brewcraft.dto.AddMaterialLotDto;
import io.company.brewcraft.dto.AddShipmentDto;
import io.company.brewcraft.dto.InvoiceItemDto;
import io.company.brewcraft.dto.MaterialLotDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.QuantityDto;
import io.company.brewcraft.dto.ShipmentDto;
import io.company.brewcraft.dto.ShipmentStatusDto;
import io.company.brewcraft.dto.StorageDto;
import io.company.brewcraft.dto.UpdateMaterialLotDto;
import io.company.brewcraft.dto.UpdateShipmentDto;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.ShipmentStatus;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.impl.ShipmentService;
import io.company.brewcraft.util.SupportedUnits;
import io.company.brewcraft.util.controller.AttributeFilter;
import tec.uom.se.quantity.Quantities;

@SuppressWarnings("unchecked")
public class ShipmentControllerTest {

    ShipmentController controller;
    ShipmentService mService;
    private AttributeFilter filter;

    @BeforeEach
    public void init() {
        this.mService = mock(ShipmentService.class);
        this.filter = new AttributeFilter();
        this.controller = new ShipmentController(this.mService, this.filter);
    }

    @Test
    public void testGetShipment_ReturnsShipmentDto_WhenServiceReturnsShipment() {
        doReturn(new Shipment(1L)).when(this.mService).get(anyLong());

        final ShipmentDto dto = this.controller.getShipment(1L);

        assertEquals(1L, dto.getId());
    }

    @Test
    public void testGetShipment_ThrowsEntityNotFoundException_WhenShipmentDoesNotExist() {
        doReturn(null).when(this.mService).get(anyLong());
        assertThrows(EntityNotFoundException.class, () -> this.controller.getShipment(1L), "Shipment not found with Id: 1");
    }

    @Test
    public void testGetShipments_ReturnsPageDtoWithAllAttributes_WhenServiceAttributesAreEmptyString() {
        final List<MaterialLot> lots = List.of(
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 2)
        );
        final Shipment shipment = new Shipment(
            1L,
            "SHIPMENT_1",
            "DESCRIPTION_1",
            new ShipmentStatus(99L),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            lots,
            1
        );

        final Page<Shipment> mPage = mock(Page.class);
        doReturn(List.of(shipment).stream()).when(mPage).stream();
        doReturn(10).when(mPage).getTotalPages();
        doReturn(1000L).when(mPage).getTotalElements();

        doReturn(mPage).when(this.mService).getShipments(
            Set.of(1L),
            Set.of(2L),
            Set.of("SHIPMENT_1"),
            Set.of("DESC_1"),
            Set.of(99L),
            LocalDateTime.of(1999, 1, 1, 12, 0, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0, 0),
            new TreeSet<>(List.of("col_1")),
            true,
            1,
            10
        );

        final PageDto<ShipmentDto> dto = this.controller.getShipments(
                                    Set.of(1L),
                                    Set.of(2L),
                                    Set.of("SHIPMENT_1"),
                                    Set.of("DESC_1"),
                                    Set.of(99L),
                                    LocalDateTime.of(1999, 1, 1, 12, 0, 0),
                                    LocalDateTime.of(2000, 1, 1, 12, 0, 0),
                                    LocalDateTime.of(2001, 1, 1, 12, 0, 0),
                                    LocalDateTime.of(2002, 1, 1, 12, 0, 0),
                                    new TreeSet<>(List.of("col_1")),
                                    true,
                                    1,
                                    10,
                                    Set.of()
                                );
        final ShipmentDto shipmentDto = dto.getContent().get(0);
        assertEquals(1L, shipmentDto.getId());
        assertEquals("SHIPMENT_1", shipmentDto.getShipmentNumber());
        assertEquals("DESCRIPTION_1", shipmentDto.getDescription());
        assertEquals(new ShipmentStatusDto(99L), shipmentDto.getStatus());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0), shipmentDto.getDeliveryDueDate());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0), shipmentDto.getDeliveredDate());
        assertEquals(LocalDateTime.of(2001, 1, 1, 12, 0), shipmentDto.getCreatedAt());
        assertEquals(LocalDateTime.of(2002, 1, 1, 12, 0), shipmentDto.getLastUpdated());
        assertEquals(1, shipmentDto.getLots().size());
        assertEquals(1, shipmentDto.getVersion());
        final MaterialLotDto lot = shipmentDto.getLots().iterator().next();
        assertEquals(1L, lot.getId());
        assertEquals(new QuantityDto("kg", new BigDecimal("1")), lot.getQuantity());
        assertEquals(LocalDateTime.of(1999, 1, 1, 12, 0, 0), lot.getCreatedAt());
        assertEquals(LocalDateTime.of(2000, 1, 1, 12, 0, 0), lot.getLastUpdated());
        assertEquals(2, lot.getVersion());
    }

    @Test
    public void testGetShipments_ReturnsPageDtoWithIdFieldOnly_WhenAttributesHaveIdOnly() {
        final List<MaterialLot> lots = List.of(
            new MaterialLot(1L, "LOT_1", Quantities.getQuantity(new BigDecimal("1"), SupportedUnits.KILOGRAM), new InvoiceItem(1L), new Storage(3L), LocalDateTime.of(1999, 1, 1, 12, 0, 0), LocalDateTime.of(2000, 1, 1, 12, 0, 0), 2)
        );
        final Shipment shipment = new Shipment(
            1L,
            "SHIPMENT_1",
            "DESCRIPTION_1",
            new ShipmentStatus(99L),
            LocalDateTime.of(1999, 1, 1, 12, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0),
            lots,
            1
        );

        final Page<Shipment> mPage = mock(Page.class);
        doReturn(List.of(shipment).stream()).when(mPage).stream();
        doReturn(10).when(mPage).getTotalPages();
        doReturn(1000L).when(mPage).getTotalElements();

        doReturn(mPage).when(this.mService).getShipments(
            Set.of(1L),
            Set.of(2L),
            Set.of("SHIPMENT_1"),
            Set.of("DESC_1"),
            Set.of(99L),
            LocalDateTime.of(1999, 1, 1, 12, 0, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0, 0),
            LocalDateTime.of(2001, 1, 1, 12, 0, 0),
            LocalDateTime.of(2002, 1, 1, 12, 0, 0),
            new TreeSet<>(List.of("col_1")),
            true,
            1,
            10
        );

        final PageDto<ShipmentDto> dto = this.controller.getShipments(
                                        Set.of(1L),
                                        Set.of(2L),
                                        Set.of("SHIPMENT_1"),
                                        Set.of("DESC_1"),
                                        Set.of(99L),
                                        LocalDateTime.of(1999, 1, 1, 12, 0, 0),
                                        LocalDateTime.of(2000, 1, 1, 12, 0, 0),
                                        LocalDateTime.of(2001, 1, 1, 12, 0, 0),
                                        LocalDateTime.of(2002, 1, 1, 12, 0, 0),
                                        new TreeSet<>(List.of("col_1")),
                                        true,
                                        1,
                                        10,
                                        Set.of("id")
                                    );
        assertEquals(new ShipmentDto(1L), dto.getContent().get(0));
    }

    @Test
    public void testDeleteShipments_CallsServiceWithSetOfIds_WhenIdsAreNotNull() {
        doReturn(99).when(this.mService).delete(eq(Set.of(1L, 2L, 3L)));

        final int deleteCount = this.controller.deleteShipments(Set.of(1L, 2L, 3L));
        assertEquals(99, deleteCount);
    }

    @Test
    public void testAddShipment_ReturnsPutShipmentDto_WhenServiceReturnsPutShipment() {
        doAnswer(i -> i.getArgument(0)).when(this.mService).add(anyList());

        final AddShipmentDto addDto = new AddShipmentDto(
            "SHIPMENT_1",
            "DESCRIPTION_1",
            99L,
            LocalDateTime.of(1999, 1, 1, 12, 0, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0, 0),
            Set.of(
                new AddMaterialLotDto("LOT_1", new QuantityDto("kg", new BigDecimal("10")), 1L, 3L)
            )
        );

        final ShipmentDto dto = this.controller.addShipment(addDto);

        final ShipmentDto expected = new ShipmentDto(
            null,
            "SHIPMENT_1",
            "DESCRIPTION_1",
            new ShipmentStatusDto(99L),
            LocalDateTime.of(1999, 1, 1, 12, 0, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0, 0),
            null,
            null,
            List.of(
                new MaterialLotDto(null, "LOT_1", new QuantityDto("kg", new BigDecimal("10")), new InvoiceItemDto(1L), new StorageDto(3L), null, null, null)
            ),
            null
        );

        assertEquals(expected, dto);
    }

    @Test
    public void testPutShipment_ReturnsPutShipmentDto_WhenServiceReturnsPutShipment() {
        doAnswer(i -> i.getArgument(0)).when(this.mService).put(anyList());

        final UpdateShipmentDto updateDto = new UpdateShipmentDto(
            "SHIPMENT_1",
            "DESCRIPTION_1",
            99L,
            LocalDateTime.of(1999, 1, 1, 12, 0, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0, 0),
            Set.of(
                new UpdateMaterialLotDto(1L, "LOT_1", new QuantityDto("kg", new BigDecimal("10")), 1L, 3L, 1)
            ),
            1
        );

        final ShipmentDto dto = this.controller.putShipment(2L, updateDto);

        final ShipmentDto expected = new ShipmentDto(
            2L,
            "SHIPMENT_1",
            "DESCRIPTION_1",
            new ShipmentStatusDto(99L),
            LocalDateTime.of(1999, 1, 1, 12, 0, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0, 0),
            null,
            null,
            List.of(
                new MaterialLotDto(1L, "LOT_1", new QuantityDto("kg", new BigDecimal("10")), new InvoiceItemDto(1L), new StorageDto(3L), null, null, 1)
            ),
            1
        );

        assertEquals(expected, dto);
    }

    @Test
    public void testPatch_ReturnsPatchShipmentDto_WhenServiceReturnsPatchShipment() {
        doAnswer(i -> i.getArgument(0)).when(this.mService).patch(anyList());

        final UpdateShipmentDto updateDto = new UpdateShipmentDto(
            "SHIPMENT_1",
            "DESCRIPTION_1",
            99L,
            LocalDateTime.of(1999, 1, 1, 12, 0, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0, 0),
            Set.of(
                new UpdateMaterialLotDto(1L, "LOT_1", new QuantityDto("kg", new BigDecimal("10")), 1L, 3L, 1)
            ),
            1
        );

        final ShipmentDto dto = this.controller.patchShipment(2L, updateDto);

        final ShipmentDto expected = new ShipmentDto(
            2L,
            "SHIPMENT_1",
            "DESCRIPTION_1",
            new ShipmentStatusDto(99L),
            LocalDateTime.of(1999, 1, 1, 12, 0, 0),
            LocalDateTime.of(2000, 1, 1, 12, 0, 0),
            null,
            null,
            List.of(
                new MaterialLotDto(1L, "LOT_1", new QuantityDto("kg", new BigDecimal("10")), new InvoiceItemDto(1L), new StorageDto(3L), null, null, 1)
            ),
            1
        );

        assertEquals(expected, dto);
    }
}
