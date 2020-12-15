package io.company.brewcraft.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.company.brewcraft.dto.AddInvoiceDto;
import io.company.brewcraft.dto.InvoiceDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.UpdateInvoiceDto;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.pojo.Invoice;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.InvoiceMapper;
import io.company.brewcraft.util.controller.AttributeFilter;
import io.company.brewcraft.util.validator.Validator;

@RestController
@RequestMapping(path = "/api/suppliers")
public class InvoiceController {
    private static InvoiceMapper mapper = InvoiceMapper.INSTANCE;

    private InvoiceService invoiceService;
    private AttributeFilter filter;

    @Autowired
    public InvoiceController(InvoiceService invoiceService, AttributeFilter filter) {
        this.invoiceService = invoiceService;
        this.filter = filter;
    }

    @GetMapping("/test")
    public void test(@RequestParam(name="value") Number value) {
        this.invoiceService.test(value);
    }

    @GetMapping("/invoices")
    public PageDto<InvoiceDto> getInvoices(
        @RequestParam(required = false, name="ids") Set<Long> ids,
        @RequestParam(required = false, name="from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
        @RequestParam(required = false, name="to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
        @RequestParam(required = false, name="status") Set<InvoiceStatus> status,
        @RequestParam(required = false, name="supplier_id") Set<Long> supplierIds,
        @RequestParam(required = false, name="sort") Set<String> sort,         
        @RequestParam(name="order_asc", defaultValue = "true") boolean orderAscending,
        @RequestParam(name="page", defaultValue = "0") int page,
        @RequestParam(name="size", defaultValue = "10") int size,
        @RequestParam(name="attr") @Size(min = 1) @NotNull Set<String> attributes
    ) {
        Page<Invoice> invoices = invoiceService.getInvoices(ids, from, to, status, supplierIds, sort, orderAscending, page, size);
        return response(invoices, attributes);
    }

    @GetMapping("/invoices/{invoiceId}")
    public InvoiceDto getInvoice(@PathVariable(required = true, name = "invoiceId") Long invoiceId, @Size(min = 1) @NotNull @RequestParam(required = false, name = "attr") Set<String> attributes) {
        Validator validator = new Validator();

        Invoice invoice = invoiceService.getInvoice(invoiceId);
        validator.assertion(invoice != null, EntityNotFoundException.class, "Invoice", invoiceId.toString());

        InvoiceDto dto = InvoiceMapper.INSTANCE.toDto(invoice);
        filter.retain(dto, attributes);

        return dto;
    }

    @DeleteMapping("/invoices/{invoiceId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void deleteInvoice(@PathVariable(required = true, name = "invoiceId") Long invoiceId) {
        invoiceService.delete(invoiceId);
    }

    @PostMapping("/{supplierId}/invoices/")
    @ResponseStatus(value = HttpStatus.CREATED)
    public InvoiceDto addInvoice(@PathVariable(required=true, name="supplierId") Long supplierId, @RequestBody @NotNull AddInvoiceDto payload, @RequestParam(required = false, name = "attr") Set<String> attributes) {
        Invoice invoice = InvoiceMapper.INSTANCE.fromDto(payload);
        Invoice added = invoiceService.add(supplierId, invoice);

        InvoiceDto dto = InvoiceMapper.INSTANCE.toDto(added);
        filter.retain(dto, attributes);

        return dto;
    }

    @PutMapping("/{supplierId}/invoices/{invoiceId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public InvoiceDto updateInvoice(@PathVariable(required=true, name="supplierId") Long supplierId, @PathVariable(required = true, name = "invoiceId") Long invoiceId, @NotNull @RequestBody UpdateInvoiceDto payload, @RequestParam(name = "attr") Set<String> attributes) {
        Invoice invoice = InvoiceMapper.INSTANCE.fromDto(payload);
        Invoice updated = invoiceService.update(supplierId, invoiceId, invoice);

        InvoiceDto dto = InvoiceMapper.INSTANCE.toDto(updated);
        filter.retain(dto, attributes);

        return dto;
    }

    private PageDto<InvoiceDto> response(Page<Invoice> invoices, Set<String> attributes) {
        List<InvoiceDto> content = invoices.stream().map(i -> mapper.toDto(i)).collect(Collectors.toList());
        content.forEach(invoice -> filter.retain(invoice, attributes));

        PageDto<InvoiceDto> dto = new PageDto<InvoiceDto>();
        dto.setContent(content);
        dto.setTotalElements(invoices.getTotalElements());
        dto.setTotalPages(invoices.getTotalPages());
        return dto;
    }
}
