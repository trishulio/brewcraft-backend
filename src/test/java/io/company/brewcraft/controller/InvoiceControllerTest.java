package io.company.brewcraft.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;

import io.company.brewcraft.dto.AddInvoiceDto;
import io.company.brewcraft.dto.InvoiceDto;
import io.company.brewcraft.dto.InvoiceItemDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.SupplierDto;
import io.company.brewcraft.dto.UpdateInvoiceDto;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.service.exception.EmptyPayloadException;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.util.controller.AttributeFilter;
import io.company.brewcraft.util.validator.ValidationException;

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
        List<Invoice> mInvoices = List.of(new Invoice(12345L, new Supplier(), new java.sql.Date(123), InvoiceStatus.PENDING, List.of(new InvoiceItem(6789L)), 1));
        Page<Invoice> mPage = mock(Page.class);
        doReturn(mInvoices.stream()).when(mPage).stream();
        doReturn(100).when(mPage).getTotalPages();
        doReturn(1000L).when(mPage).getTotalElements();
        doReturn(mPage).when(mService).getInvoices(new Date(123), new Date(456), List.of(InvoiceStatus.PENDING), List.of(12345L), List.of("id"), true, 1, 50);

        PageDto<InvoiceDto> dto = controller.getInvoices(new Date(123), new Date(456), List.of(InvoiceStatus.PENDING), List.of(12345L), List.of("id"), true, 1, 50, Set.of("id", "supplier", "date", "status", "items"));

        assertEquals(100, dto.getTotalPages());
        assertEquals(1000L, dto.getTotalElements());

        assertEquals(1, dto.getContent().size());
        assertEquals(12345L, dto.getContent().get(0).getId());
        assertEquals(new SupplierDto(), dto.getContent().get(0).getSupplier());
        assertEquals(new Date(123), dto.getContent().get(0).getDate());
        assertEquals(InvoiceStatus.PENDING, dto.getContent().get(0).getStatus());
        assertEquals(1, dto.getContent().get(0).getItems().size());
        assertEquals(new InvoiceItemDto(6789L), dto.getContent().get(0).getItems().get(0));
    }

    @Test
    public void testGetInvoices_ReturnsInvoiceDtoPage_WhenBothDateToAndFromAreNull() {
        List<Invoice> mInvoices = List.of(new Invoice(12345L, new Supplier(), new java.sql.Date(123), InvoiceStatus.PENDING, List.of(new InvoiceItem(6789L)), 1));
        Page<Invoice> mPage = mock(Page.class);
        doReturn(mInvoices.stream()).when(mPage).stream();
        doReturn(100).when(mPage).getTotalPages();
        doReturn(1000L).when(mPage).getTotalElements();
        doReturn(mPage).when(mService).getInvoices(null, null, List.of(InvoiceStatus.PENDING), List.of(12345L), List.of("id"), true, 1, 50);

        PageDto<InvoiceDto> dto = controller.getInvoices(null, null, List.of(InvoiceStatus.PENDING), List.of(12345L), List.of("id"), true, 1, 50, Set.of("id", "supplier", "date", "status", "items"));

        assertEquals(100, dto.getTotalPages());
        assertEquals(1000L, dto.getTotalElements());

        assertEquals(1, dto.getContent().size());
        assertEquals(12345L, dto.getContent().get(0).getId());
        assertEquals(new SupplierDto(), dto.getContent().get(0).getSupplier());
        assertEquals(new Date(123), dto.getContent().get(0).getDate());
        assertEquals(InvoiceStatus.PENDING, dto.getContent().get(0).getStatus());
        assertEquals(1, dto.getContent().get(0).getItems().size());
        assertEquals(new InvoiceItemDto(6789L), dto.getContent().get(0).getItems().get(0));
    }

    @Test
    public void testGetInvoices_ThrowsValidationException_WhenDateFromIsNotNullButDateToIsNull() {
        assertThrows(ValidationException.class, () -> {
            controller.getInvoices(new Date(123), null, List.of(InvoiceStatus.PENDING), List.of(12345L), List.of("id"), true, 1, 50, Set.of("id", "supplier", "date", "status", "items"));
        }, "1.Date to and from both need to be null or non-null together\n");
    }

    @Test
    public void testGetInvoices_ThrowsValidationException_WhenDateFromIsNullButDateToIsNotNull() {
        assertThrows(ValidationException.class, () -> {
            controller.getInvoices(null, new Date(123), List.of(InvoiceStatus.PENDING), List.of(12345L), List.of("id"), true, 1, 50, Set.of("id", "supplier", "date", "status", "items"));
        }, "1.Date to and from both need to be null or non-null together\n");
    }

    @Test
    public void testGetInvoices_ThrowsValidationException_WhenAttributesIsEmpty() {
        assertThrows(ValidationException.class, () -> {
            controller.getInvoices(new Date(123), new Date(456), List.of(InvoiceStatus.PENDING), List.of(12345L), List.of("id"), true, 1, 50, Set.of());
        }, "1.Attributes param cannot be empty\n");
    }

    @Test
    public void testGetInvoices_ThrowsValidationExceptionWithAllErrorMessages_WhenMultipleParametersAreInvalid() {
        assertThrows(ValidationException.class, () -> {
            controller.getInvoices(null, null, List.of(InvoiceStatus.PENDING), List.of(12345L), List.of("id"), true, 1, 50, Set.of());
        }, "1.Date to and from both need to be null or non-null together\n2.Attributes param cannot be empty\n");
    }

    @Test
    public void testGetInvoices_SetsAllInvoiceDtoFieldsNullExceptSupplierAndIdFields_WhenAttributesHasOnlySupplierAndIdField() {
        List<Invoice> mInvoices = List.of(new Invoice(12345L, new Supplier(), new java.sql.Date(123), InvoiceStatus.PENDING, List.of(new InvoiceItem(6789L)), 1));
        Page<Invoice> mPage = mock(Page.class);
        doReturn(mInvoices.stream()).when(mPage).stream();
        doReturn(100).when(mPage).getTotalPages();
        doReturn(1000L).when(mPage).getTotalElements();
        doReturn(mPage).when(mService).getInvoices(new Date(123), new Date(456), List.of(InvoiceStatus.PENDING), List.of(12345L), List.of("id"), true, 1, 50);

        PageDto<InvoiceDto> dto = controller.getInvoices(new Date(123), new Date(456), List.of(InvoiceStatus.PENDING), List.of(12345L), List.of("id"), true, 1, 50, Set.of("id", "supplier"));

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
        Invoice mInvoice = new Invoice(12345L, new Supplier(), new java.sql.Date(123), InvoiceStatus.PENDING, List.of(new InvoiceItem(6789L)), 1);
        doReturn(mInvoice).when(mService).getInvoice(12345L);

        InvoiceDto dto = controller.getInvoice(12345L, Set.of("id", "supplier", "date", "status", "items"));

        assertEquals(12345L, dto.getId());
        assertEquals(new SupplierDto(), dto.getSupplier());
        assertEquals(new Date(123), dto.getDate());
        assertEquals(InvoiceStatus.PENDING, dto.getStatus());
        assertEquals(1, dto.getItems().size());
        assertEquals(new InvoiceItemDto(6789L), dto.getItems().get(0));
    }

    @Test
    public void testGetInvoice_SetsDateFieldToNull_WhenDateFieldIsMissingFromAttributes() {
        Invoice mInvoice = new Invoice(12345L, new Supplier(), new java.sql.Date(123), InvoiceStatus.PENDING, List.of(new InvoiceItem(6789L)), 1);
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
        Invoice mRetInvoice = new Invoice(12345L, new Supplier(), new java.sql.Date(123), InvoiceStatus.FINAL, List.of(), 1);
        Invoice mAddInvoice = new Invoice(null, new Supplier(), new java.sql.Date(123), InvoiceStatus.FINAL, List.of(), null);
        doReturn(mRetInvoice).when(mService).add(mAddInvoice);

        AddInvoiceDto payload = new AddInvoiceDto(new SupplierDto(), new Date(123), InvoiceStatus.FINAL, List.of());
        InvoiceDto dto = controller.addInvoice(payload, Set.of("id", "supplier", "date", "status", "items"));

        assertEquals(12345L, dto.getId());
        assertEquals(new SupplierDto(), dto.getSupplier());
        assertEquals(new Date(123), dto.getDate());
        assertEquals(InvoiceStatus.FINAL, dto.getStatus());
        assertEquals(List.of(), dto.getItems());
    }

    @Test
    public void testAddInvoice_SetsAllFieldsAsNullExceptId_WhenAttributeHasIdOnly() {
        Invoice mRetInvoice = new Invoice(12345L, new Supplier(), new java.sql.Date(123), InvoiceStatus.FINAL, List.of(), 1);
        Invoice mAddInvoice = new Invoice(null, new Supplier(), new java.sql.Date(123), InvoiceStatus.FINAL, List.of(), null);
        doReturn(mRetInvoice).when(mService).add(mAddInvoice);

        AddInvoiceDto payload = new AddInvoiceDto(new SupplierDto(), new Date(123), InvoiceStatus.FINAL, List.of());
        InvoiceDto dto = controller.addInvoice(payload, Set.of("id"));

        assertEquals(12345L, dto.getId());
        assertNull(dto.getSupplier());
        assertNull(dto.getDate());
        assertNull(dto.getStatus());
        assertNull(dto.getItems());
    }

    @Test
    public void testAddInvoice_ThrowsEmptyPayloadException_WhenPayloadIsNull() {
        assertThrows(EmptyPayloadException.class, () -> controller.addInvoice(null, Set.of()));
    }

    @Test
    public void testUpdateInvoice_ReturnsUpdatedDto_WhenServiceReturnsUpdatedInvoice() {
        Invoice mRetInvoice = new Invoice(12345L, new Supplier(), new java.sql.Date(123), InvoiceStatus.FINAL, List.of(), 1);
        Invoice mUpdateInvoice = new Invoice(null, new Supplier(), new java.sql.Date(123), InvoiceStatus.FINAL, List.of(), 1);
        doReturn(mRetInvoice).when(mService).update(12345L, mUpdateInvoice);

        UpdateInvoiceDto payload = new UpdateInvoiceDto(new SupplierDto(), new Date(123), InvoiceStatus.FINAL, List.of(), 1);
        controller.updateInvoice(12345L, payload, Set.of("id", "supplier", "status", "items"));

        InvoiceDto dto = controller.updateInvoice(12345L, payload, Set.of("id", "supplier", "date", "status", "items"));

        assertEquals(12345L, dto.getId());
        assertEquals(new SupplierDto(), dto.getSupplier());
        assertEquals(new Date(123), dto.getDate());
        assertEquals(InvoiceStatus.FINAL, dto.getStatus());
        assertEquals(List.of(), dto.getItems());
    }

    @Test
    public void testUpdateInvoice_SetsAllFieldsAsNullExceptId_WhenAttributesHasId() {
        Invoice mRetInvoice = new Invoice(12345L, new Supplier(), new java.sql.Date(123), InvoiceStatus.FINAL, List.of(), 1);
        Invoice mUpdateInvoice = new Invoice(null, new Supplier(), new java.sql.Date(123), InvoiceStatus.FINAL, List.of(), 1);
        doReturn(mRetInvoice).when(mService).update(12345L, mUpdateInvoice);

        UpdateInvoiceDto payload = new UpdateInvoiceDto(new SupplierDto(), new Date(123), InvoiceStatus.FINAL, List.of(), 1);
        controller.updateInvoice(12345L, payload, Set.of("id", "supplier", "status", "items"));

        InvoiceDto dto = controller.updateInvoice(12345L, payload, Set.of("id"));

        assertEquals(12345L, dto.getId());
        assertNull(dto.getSupplier());
        assertNull(dto.getDate());
        assertNull(dto.getStatus());
        assertNull(dto.getItems());
    }

    @Test
    public void testUpdateInvoice_ThrowsEmptyPayloadException_WhenPayloadIsNull() {
        assertThrows(EmptyPayloadException.class, () -> controller.updateInvoice(12345L, null, Set.of()));
    }
}
