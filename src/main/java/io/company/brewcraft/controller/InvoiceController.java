package io.company.brewcraft.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.company.brewcraft.dto.AddInvoiceDto;
import io.company.brewcraft.dto.BaseInvoice;
import io.company.brewcraft.dto.InvoiceDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.UpdateInvoice;
import io.company.brewcraft.dto.UpdateInvoiceDto;
import io.company.brewcraft.model.BaseInvoiceItem;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.UpdateInvoiceItem;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.service.mapper.InvoiceMapper;
import io.company.brewcraft.util.controller.AttributeFilter;

@RestController
@RequestMapping(path = "/api/v1/purchases/invoices")
public class InvoiceController extends BaseController {
    private static InvoiceMapper mapper = InvoiceMapper.INSTANCE;

    private CrudControllerService<
        Long,
        Invoice,
        BaseInvoice<? extends BaseInvoiceItem<?>>,
        UpdateInvoice<? extends UpdateInvoiceItem<?>>,
        InvoiceDto,
        AddInvoiceDto,
        UpdateInvoiceDto
    > controller;

    private final InvoiceService invoiceService;

    protected InvoiceController(CrudControllerService<
            Long,
            Invoice,
            BaseInvoice<? extends BaseInvoiceItem<?>>,
            UpdateInvoice<? extends UpdateInvoiceItem<?>>,
            InvoiceDto,
            AddInvoiceDto,
            UpdateInvoiceDto
        > controller, InvoiceService invoiceService)
    {
        this.controller = controller;
        this.invoiceService = invoiceService;
    }

    @Autowired
    public InvoiceController(InvoiceService invoiceService, AttributeFilter filter) {
        this(new CrudControllerService<>(filter, InvoiceMapper.INSTANCE, invoiceService, "Invoice"), invoiceService);
    }

    @GetMapping
    public PageDto<InvoiceDto> getAll(
        @RequestParam(required = false, name = "ids") Set<Long> ids,
        @RequestParam(required = false, name = "exclude_ids") Set<Long> excludeIds,
        @RequestParam(required = false, name = "invoice_numbers") Set<String> invoiceNumbers,
        @RequestParam(required = false, name = "invoice_descriptions") Set<String> invoiceDescriptions,
        @RequestParam(required = false, name = "invoice_item_descriptions") Set<String> invoiceItemDescriptions,
        @RequestParam(required = false, name = "generated_on_from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime generatedOnFrom,
        @RequestParam(required = false, name = "generated_on_to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime generatedOnTo,
        @RequestParam(required = false, name = "received_on_from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime receivedOnFrom,
        @RequestParam(required = false, name = "received_on_to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime receivedOnTo,
        @RequestParam(required = false, name = "payment_due_date_from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime paymentDueDateFrom,
        @RequestParam(required = false, name = "payment_due_date_to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime paymentDueDateTo,
        @RequestParam(required = false, name = "purchase_order_ids") Set<Long> purchaseOrderIds,
        @RequestParam(required = false, name = "material_ids") Set<Long> materialIds,
        @RequestParam(required = false, name = "amt_from") BigDecimal amtFrom,
        @RequestParam(required = false, name = "amt_to") BigDecimal amtTo,
        @RequestParam(required = false, name = "freight_amt_from") BigDecimal freightAmtFrom,
        @RequestParam(required = false, name = "freight_amt_to") BigDecimal freightAmtTo,
        @RequestParam(required = false, name = "invoice_status_ids") Set<Long> invoiceStatusIds,
        @RequestParam(required = false, name = "supplier_ids") Set<Long> supplierIds,
        @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
        @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
        @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
        @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size,
        @RequestParam(name = PROPNAME_ATTR, defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes
    ) {
        final Page<Invoice> invoices = this.invoiceService.getInvoices(
            ids,
            excludeIds,
            invoiceNumbers,
            invoiceDescriptions,
            invoiceItemDescriptions,
            generatedOnFrom,
            generatedOnTo,
            receivedOnFrom,
            receivedOnTo,
            paymentDueDateFrom,
            paymentDueDateTo,
            purchaseOrderIds,
            materialIds,
            amtFrom,
            amtTo,
            freightAmtFrom,
            freightAmtTo,
            invoiceStatusIds,
            supplierIds,
            sort,
            orderAscending,
            page,
            size
        );

        return this.controller.getAll(invoices, attributes);
    }

    @GetMapping("/{invoiceId}")
    public InvoiceDto getInvoice(@PathVariable(required = true, name = "invoiceId") Long invoiceId, @RequestParam(name = PROPNAME_ATTR, defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
        return this.controller.get(invoiceId, attributes);
    }

    @DeleteMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public int deleteInvoices(@RequestParam("ids") Set<Long> invoiceIds) {
        return this.controller.delete(invoiceIds);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public List<InvoiceDto> addInvoice(@Valid @NotNull @RequestBody List<AddInvoiceDto> addDtos) {
        return this.controller.add(addDtos);
    }

    @PutMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public List<InvoiceDto> updateInvoice(@Valid @NotNull @RequestBody List<UpdateInvoiceDto> updateDtos) {
        return this.controller.put(updateDtos);
    }

    @PatchMapping
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public List<InvoiceDto> patchInvoice(@Valid @NotNull @RequestBody List<UpdateInvoiceDto> updateDtos) {
        return this.controller.patch(updateDtos);
    }
}
