package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import io.company.brewcraft.dto.AddInvoiceDto;
import io.company.brewcraft.dto.InvoiceDto;
import io.company.brewcraft.dto.InvoiceItemDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.SupplierDto;
import io.company.brewcraft.dto.UpdateInvoiceDto;
import io.company.brewcraft.dto.UpdateInvoiceItemDto;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.pojo.Invoice;
import io.company.brewcraft.pojo.InvoiceItem;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.util.controller.AttributeFilter;

@SuppressWarnings("unchecked")
public class InvoiceControllerTest {

    private InvoiceController controller;

    private InvoiceService mService;
    private AttributeFilter mFilter;

    @BeforeEach
    public void init() {
        mService = mock(InvoiceService.class);
        mFilter = new AttributeFilter();

        controller = new InvoiceController(mService, mFilter);
    }

    @Test
    public void testGetInvoices_CallsSuppliersWithArguments_AndReturnsPageDtoOfInvoiceDtosMappedFromInvoicesPage() {
        List<Invoice> mInvoices = List.of(
            new Invoice(12345L,
                new Supplier(),
                LocalDateTime.MAX,
                LocalDateTime.of(1997, 1, 1, 1, 10),
                LocalDateTime.of(1998, 2, 2, 2, 20),
                InvoiceStatus.PENDING,
                List.of(new InvoiceItem(6789L)),
                1
            )
        );
        Page<Invoice> mPage = mock(Page.class);
        doReturn(mInvoices.stream()).when(mPage).stream();
        doReturn(100).when(mPage).getTotalPages();
        doReturn(1000L).when(mPage).getTotalElements();
        doReturn(mPage).when(mService).getInvoices(Set.of(111L), LocalDateTime.MIN, LocalDateTime.MAX, Set.of(InvoiceStatus.PENDING), Set.of(12345L), Set.of("id"), true, 1, 50);

        PageDto<InvoiceDto> dto = controller.getInvoices(Set.of(111L), LocalDateTime.MIN, LocalDateTime.MAX, Set.of(InvoiceStatus.PENDING), Set.of(12345L), Set.of("id"), true, 1, 50, Set.of("id", "supplier", "date", "status", "items"));

        assertEquals(100, dto.getTotalPages());
        assertEquals(1000L, dto.getTotalElements());

        assertEquals(1, dto.getContent().size());
        assertEquals(12345L, dto.getContent().get(0).getId());
        assertEquals(new SupplierDto(), dto.getContent().get(0).getSupplier());
        assertEquals(LocalDateTime.MAX, dto.getContent().get(0).getDate());
        assertEquals(InvoiceStatus.PENDING, dto.getContent().get(0).getStatus());
        assertEquals(1, dto.getContent().get(0).getItems().size());
        assertEquals(new InvoiceItemDto(6789L), dto.getContent().get(0).getItems().get(0));
    }
    
    @Test
    @Disabled // TODO: This is service logic
    public void testGetInvoices_SetDateFromToMin_WhenToIsNotNullAndFromIsNull() {
        List<Invoice> mInvoices = List.of(
            new Invoice(12345L,
                new Supplier(),
                LocalDateTime.MAX,
                LocalDateTime.of(1997, 1, 1, 1, 10),
                LocalDateTime.of(1998, 2, 2, 2, 20),
                InvoiceStatus.PENDING,
                List.of(new InvoiceItem(6789L)),
                1
            )
        );
        Page<Invoice> mPage = mock(Page.class);
        doReturn(mInvoices.stream()).when(mPage).stream();
        doReturn(100).when(mPage).getTotalPages();
        doReturn(1000L).when(mPage).getTotalElements();
        doReturn(mPage).when(mService).getInvoices(Set.of(111L), null, LocalDateTime.MAX, Set.of(InvoiceStatus.PENDING), Set.of(12345L), Set.of("id"), true, 1, 50);

        PageDto<InvoiceDto> dto = controller.getInvoices(Set.of(111L), LocalDateTime.MIN, LocalDateTime.MAX, Set.of(InvoiceStatus.PENDING), Set.of(12345L), Set.of("id"), true, 1, 50, Set.of("id", "supplier", "date", "status", "items"));

        assertEquals(100, dto.getTotalPages());
        assertEquals(1000L, dto.getTotalElements());

        assertEquals(1, dto.getContent().size());
        assertEquals(12345L, dto.getContent().get(0).getId());
        assertEquals(new SupplierDto(), dto.getContent().get(0).getSupplier());
        assertEquals(LocalDateTime.MAX, dto.getContent().get(0).getDate());
        assertEquals(InvoiceStatus.PENDING, dto.getContent().get(0).getStatus());
        assertEquals(1, dto.getContent().get(0).getItems().size());
        assertEquals(new InvoiceItemDto(6789L), dto.getContent().get(0).getItems().get(0));        
    }

    @Test
    @Disabled // TODO: This is service logic
    public void testGetInvoices_SetDateToAsMax_WhenToIsNullAndFromIsNotNull() {
        List<Invoice> mInvoices = List.of(
            new Invoice(12345L,
                new Supplier(),
                LocalDateTime.MAX,
                LocalDateTime.of(1997, 1, 1, 1, 10),
                LocalDateTime.of(1998, 2, 2, 2, 20),
                InvoiceStatus.PENDING,
                List.of(new InvoiceItem(6789L)),
                1
            )
        );
        Page<Invoice> mPage = mock(Page.class);
        doReturn(mInvoices.stream()).when(mPage).stream();
        doReturn(100).when(mPage).getTotalPages();
        doReturn(1000L).when(mPage).getTotalElements();
        doReturn(mPage).when(mService).getInvoices(Set.of(111L), LocalDateTime.MIN, null, Set.of(InvoiceStatus.PENDING), Set.of(12345L), Set.of("id"), true, 1, 50);

        PageDto<InvoiceDto> dto = controller.getInvoices(Set.of(111L), LocalDateTime.MIN, LocalDateTime.MAX, Set.of(InvoiceStatus.PENDING), Set.of(12345L), Set.of("id"), true, 1, 50, Set.of("id", "supplier", "date", "status", "items"));

        assertEquals(100, dto.getTotalPages());
        assertEquals(1000L, dto.getTotalElements());

        assertEquals(1, dto.getContent().size());
        assertEquals(12345L, dto.getContent().get(0).getId());
        assertEquals(new SupplierDto(), dto.getContent().get(0).getSupplier());
        assertEquals(LocalDateTime.MAX, dto.getContent().get(0).getDate());
        assertEquals(InvoiceStatus.PENDING, dto.getContent().get(0).getStatus());
        assertEquals(1, dto.getContent().get(0).getItems().size());
        assertEquals(new InvoiceItemDto(6789L), dto.getContent().get(0).getItems().get(0));        
    }

    @Test
    public void testGetInvoices_UsesDateToFromAsNull_WhenBothAreNull() {
        List<Invoice> mInvoices = List.of(
            new Invoice(12345L,
                new Supplier(),
                LocalDateTime.MAX,
                LocalDateTime.of(1997, 1, 1, 1, 10),
                LocalDateTime.of(1998, 2, 2, 2, 20),
                InvoiceStatus.PENDING,
                List.of(new InvoiceItem(6789L)),
                1
            )
        );
        Page<Invoice> mPage = mock(Page.class);
        doReturn(mInvoices.stream()).when(mPage).stream();
        doReturn(100).when(mPage).getTotalPages();
        doReturn(1000L).when(mPage).getTotalElements();
        doReturn(mPage).when(mService).getInvoices(Set.of(111L), null, null, Set.of(InvoiceStatus.PENDING), Set.of(12345L), Set.of("id"), true, 1, 50);

        PageDto<InvoiceDto> dto = controller.getInvoices(Set.of(111L), null, null, Set.of(InvoiceStatus.PENDING), Set.of(12345L), Set.of("id"), true, 1, 50, Set.of("id", "supplier", "date", "status", "items"));

        assertEquals(100, dto.getTotalPages());
        assertEquals(1000L, dto.getTotalElements());

        assertEquals(1, dto.getContent().size());
        assertEquals(12345L, dto.getContent().get(0).getId());
        assertEquals(new SupplierDto(), dto.getContent().get(0).getSupplier());
        assertEquals(LocalDateTime.MAX, dto.getContent().get(0).getDate());
        assertEquals(InvoiceStatus.PENDING, dto.getContent().get(0).getStatus());
        assertEquals(1, dto.getContent().get(0).getItems().size());
        assertEquals(new InvoiceItemDto(6789L), dto.getContent().get(0).getItems().get(0));     
    }

    @Test
    public void testGetInvoices_SetsAllInvoiceDtoFieldsNullExceptSupplierAndIdFields_WhenAttributesHasOnlySupplierAndIdField() {
        List<Invoice> mInvoices = List.of(
            new Invoice(12345L,
                new Supplier(),
                LocalDateTime.MAX,
                LocalDateTime.of(1997, 1, 1, 1, 10),
                LocalDateTime.of(1998, 2, 2, 2, 20),
                InvoiceStatus.PENDING,
                List.of(new InvoiceItem(6789L)),
                1
            )
        );
        Page<Invoice> mPage = mock(Page.class);
        doReturn(mInvoices.stream()).when(mPage).stream();
        doReturn(100).when(mPage).getTotalPages();
        doReturn(1000L).when(mPage).getTotalElements();
        doReturn(mPage).when(mService).getInvoices(Set.of(111L), LocalDateTime.MAX, LocalDateTime.MIN, Set.of(InvoiceStatus.PENDING), Set.of(12345L), Set.of("id"), true, 1, 50);

        PageDto<InvoiceDto> dto = controller.getInvoices(Set.of(111L), LocalDateTime.MAX, LocalDateTime.MIN, Set.of(InvoiceStatus.PENDING), Set.of(12345L), Set.of("id"), true, 1, 50, Set.of("id", "supplier"));

        assertEquals(100, dto.getTotalPages());
        assertEquals(1000L, dto.getTotalElements());

        assertEquals(1, dto.getContent().size());
        assertEquals(12345L, dto.getContent().get(0).getId());
        assertEquals(new SupplierDto(), dto.getContent().get(0).getSupplier());
        assertNull(dto.getContent().get(0).getDate());
        assertNull(dto.getContent().get(0).getStatus());
        assertNull(dto.getContent().get(0).getItems());
    }

    @Test
    public void testGetInvoice_ReturnsInvoiceDto_WhenServiceReturnsInvoice() {
        Invoice mInvoice = new Invoice(12345L,
            new Supplier(),
            LocalDateTime.MAX,
            LocalDateTime.of(1997, 1, 1, 1, 10),
            LocalDateTime.of(1998, 2, 2, 2, 20),
            InvoiceStatus.PENDING,
            List.of(new InvoiceItem(6789L)),
            1
        );
        doReturn(mInvoice).when(mService).getInvoice(12345L);

        InvoiceDto dto = controller.getInvoice(12345L, Set.of("id", "supplier", "date", "status", "items"));

        assertEquals(12345L, dto.getId());
        assertEquals(new SupplierDto(), dto.getSupplier());
        assertEquals(LocalDateTime.MAX, dto.getDate());
        assertEquals(InvoiceStatus.PENDING, dto.getStatus());
        assertEquals(1, dto.getItems().size());
        assertEquals(new InvoiceItemDto(6789L), dto.getItems().get(0));
    }

    @Test
    public void testGetInvoice_SetsDateFieldToNull_WhenDateFieldIsMissingFromAttributes() {
        Invoice mInvoice = new Invoice(12345L,
            new Supplier(),
            LocalDateTime.MAX,
            LocalDateTime.of(1997, 1, 1, 1, 10),
            LocalDateTime.of(1998, 2, 2, 2, 20),
            InvoiceStatus.PENDING,
            List.of(new InvoiceItem(6789L)),
            1
        );
        doReturn(mInvoice).when(mService).getInvoice(12345L);

        InvoiceDto dto = controller.getInvoice(12345L, Set.of("id", "supplier", "status", "items"));

        assertEquals(12345L, dto.getId());
        assertEquals(new SupplierDto(), dto.getSupplier());
        assertEquals(InvoiceStatus.PENDING, dto.getStatus());
        assertEquals(1, dto.getItems().size());
        assertEquals(new InvoiceItemDto(6789L), dto.getItems().get(0));
        assertNull(dto.getDate());
    }

    @Test
    public void testGetInvoice_ThrowsEntityNotFoundException_WhenServiceReturnsNull() {
        doReturn(null).when(mService).getInvoice(12345L);
        assertThrows(EntityNotFoundException.class, () -> controller.getInvoice(12345L, Set.of("id", "supplier", "status", "items")), "Invoice not found with id: 12345");
    }

    @Test
    public void testDeleteInvoice_CallsSuppliersWithId() {
        doNothing().when(mService).delete(12345L);
        controller.deleteInvoice(12345L);

        verify(mService, times(1)).delete(12345L);
    }

    @Test
    public void testDeleteInvoices_ThrowsEntityNotFoundException_WhenSupplierThrowsEntityNotFoundException() {
        doThrow(new EntityNotFoundException("Invoice", "12345")).when(mService).delete(12345L);
        assertThrows(EntityNotFoundException.class, () -> controller.deleteInvoice(12345L), "Invoice not found with id: 12345");
    }

    @Test
    public void testAddInvoice_ReturnsInvoiceDtoAfterAddingToService() {
        Invoice mRetInvoice = new Invoice(12345L, new Supplier(), LocalDateTime.MIN, LocalDateTime.of(1997, 1, 1, 10, 10), LocalDateTime.of(1998, 2, 2, 20, 20), InvoiceStatus.FINAL, List.of(new InvoiceItem(12345L)), 1);
        Invoice mAddInvoice = new Invoice(null, null, LocalDateTime.MIN, null, null, InvoiceStatus.FINAL, List.of(new InvoiceItem(12345L)), null);
        doReturn(mRetInvoice).when(mService).add(12345L, mAddInvoice);

        AddInvoiceDto payload = new AddInvoiceDto(LocalDateTime.MIN, InvoiceStatus.FINAL, List.of(new UpdateInvoiceItemDto(12345L)));
        InvoiceDto dto = controller.addInvoice(12345L, payload, Set.of("id", "supplier", "date", "status", "items"));

        assertEquals(12345L, dto.getId());
        assertEquals(new SupplierDto(), dto.getSupplier());
        assertEquals(LocalDateTime.MIN, dto.getDate());
        assertEquals(InvoiceStatus.FINAL, dto.getStatus());
        assertEquals(List.of(new InvoiceItemDto(12345L)), dto.getItems());
    }

    @Test
    public void testAddInvoice_SetsAllFieldsAsNullExceptId_WhenAttributeHasIdOnly() {
        Invoice mRetInvoice = new Invoice(12345L, new Supplier(), LocalDateTime.MIN, LocalDateTime.of(1997, 1, 1, 10, 10), LocalDateTime.of(1998, 2, 2, 20, 20), InvoiceStatus.FINAL, List.of(new InvoiceItem(12345L)), 1);
        Invoice mAddInvoice = new Invoice(null, null, LocalDateTime.MIN, null, null, InvoiceStatus.FINAL, List.of(new InvoiceItem(12345L)), null);
        doReturn(mRetInvoice).when(mService).add(12345L, mAddInvoice);

        AddInvoiceDto payload = new AddInvoiceDto(LocalDateTime.MIN, InvoiceStatus.FINAL, List.of(new UpdateInvoiceItemDto(12345L)));
        InvoiceDto dto = controller.addInvoice(12345L, payload, Set.of("id"));

        assertEquals(12345L, dto.getId());
        assertNull(dto.getSupplier());
        assertNull(dto.getDate());
        assertNull(dto.getStatus());
        assertNull(dto.getItems());
    }

    @Test
    public void testUpdateInvoice_ReturnsUpdatedDto_WhenServiceReturnsUpdatedInvoice() {
        Invoice mRetInvoice = new Invoice(12345L, new Supplier(), LocalDateTime.MIN, LocalDateTime.of(1997, 1, 1, 10, 10), LocalDateTime.of(1998, 2, 2, 20, 20), InvoiceStatus.FINAL, List.of(new InvoiceItem(12345L)), 1);
        Invoice mUpdateInvoice = new Invoice(null, null, LocalDateTime.MIN, null, null, InvoiceStatus.FINAL, List.of(new InvoiceItem(12345L)), 1);
        doReturn(mRetInvoice).when(mService).update(12345L, 67890L, mUpdateInvoice);

        UpdateInvoiceDto payload = new UpdateInvoiceDto(LocalDateTime.MIN, InvoiceStatus.FINAL, List.of(new UpdateInvoiceItemDto(12345L)), 1);
        InvoiceDto dto = controller.updateInvoice(12345L, 67890L, payload, Set.of("id", "supplier", "date", "status", "items"));

        assertEquals(12345L, dto.getId());
        assertEquals(new SupplierDto(), dto.getSupplier());
        assertEquals(LocalDateTime.MIN, dto.getDate());
        assertEquals(InvoiceStatus.FINAL, dto.getStatus());
        assertEquals(List.of(new InvoiceItemDto(12345L)), dto.getItems());
    }

    @Test
    public void testUpdateInvoice_SetsAllFieldsAsNullExceptId_WhenAttributesHasId() {
        Invoice mRetInvoice = new Invoice(12345L, new Supplier(), LocalDateTime.MIN, LocalDateTime.of(1997, 1, 1, 10, 10), LocalDateTime.of(1998, 2, 2, 20, 20), InvoiceStatus.FINAL, List.of(new InvoiceItem(12345L)), 1);
        Invoice mUpdateInvoice = new Invoice(null, null, LocalDateTime.MIN, null, null, InvoiceStatus.FINAL, List.of(new InvoiceItem(12345L)), 1);
        doReturn(mRetInvoice).when(mService).update(12345L, 67890L, mUpdateInvoice);

        UpdateInvoiceDto payload = new UpdateInvoiceDto(LocalDateTime.MIN, InvoiceStatus.FINAL, List.of(new UpdateInvoiceItemDto(12345L)), 1);
        InvoiceDto dto = controller.updateInvoice(12345L, 67890L, payload, Set.of("id"));

        assertEquals(12345L, dto.getId());
        assertNull(dto.getSupplier());
        assertNull(dto.getDate());
        assertNull(dto.getStatus());
        assertNull(dto.getItems());
    }
}
