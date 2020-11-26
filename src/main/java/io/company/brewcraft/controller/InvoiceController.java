package io.company.brewcraft.controller;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.company.brewcraft.dto.AddInvoiceDto;
import io.company.brewcraft.dto.InvoiceDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.UpdateInvoiceDto;
import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.service.InvoiceService;
import io.company.brewcraft.service.exception.EmptyPayloadException;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.InvoiceMapper;
import io.company.brewcraft.util.controller.AttributeFilter;
import io.company.brewcraft.util.validator.Validator;

@RestController
@RequestMapping(value = "/v1/invoices", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class InvoiceController {
    private static InvoiceMapper mapper = InvoiceMapper.INSTANCE;

    private InvoiceService service;
    private AttributeFilter filter;

    @Autowired
    public InvoiceController(InvoiceService service, AttributeFilter filter) {
        this.service = service;
        this.filter = filter;
    }

    @GetMapping("/")
    public PageDto<InvoiceDto> getInvoices(
        @RequestParam(required = false, name="from") Date from,
        @RequestParam(required = false, name="to") Date to,
        @RequestParam(required = false, name="status") List<InvoiceStatus> status,
        @RequestParam(required = false, name="supplier_id") List<Long> supplierIds,
        @RequestParam(required = false, name="sort") List<String> sort,         
        @RequestParam(required = false, name="order_asc") boolean orderAscending,
        @RequestParam(required = false, name="page") int page,
        @RequestParam(required = false, name="size") int size,
        @RequestParam(required = false, name="attr") Set<String> attributes
    ) {
        Validator validator = new Validator();
        validator.rule((from == null && to == null) || (from != null && to != null), "Date to and from both need to be null or non-null together");
        validator.rule(attributes.size() > 0, "Attributes param cannot be empty");
        validator.raiseErrors();
        Page<Invoice> invoices = service.getInvoices(from, to, status, supplierIds, sort, orderAscending, page, size);
        return response(invoices, attributes);
    }

    @GetMapping("/{id}")
    public InvoiceDto getInvoice(@PathVariable(required = true, name = "id") Long id, @RequestParam(required = false, name = "attr") Set<String> attributes) {
        Validator validator = new Validator();

        Invoice invoice = service.getInvoice(id);
        validator.assertion(invoice != null, EntityNotFoundException.class, "Invoice", id.toString());

        InvoiceDto dto = InvoiceMapper.INSTANCE.toDto(invoice);
        filter.retain(dto, attributes);

        return dto;
    }

    @DeleteMapping("/{id}")
    public void deleteInvoice(@PathVariable(required = true, name = "id") Long id) {
        service.delete(id);
    }

    @PostMapping("/")
    public InvoiceDto addInvoice(@RequestBody AddInvoiceDto payload, @RequestParam(required = false, name = "attr") Set<String> attributes) {
        Validator validator = new Validator();
        validator.assertion(payload != null, EmptyPayloadException.class);

        Invoice invoice = InvoiceMapper.INSTANCE.fromDto(payload);
        Invoice added = service.add(invoice);

        InvoiceDto dto = InvoiceMapper.INSTANCE.toDto(added);
        filter.retain(dto, attributes);

        return dto;
    }

    @PutMapping("/{id}")
    public InvoiceDto updateInvoice(@PathVariable(required = true, name = "id") Long id, @RequestBody UpdateInvoiceDto payload, @RequestParam(required = false, name = "attr") Set<String> attributes) {
        Validator validator = new Validator();
        validator.assertion(payload != null, EmptyPayloadException.class);

        Invoice invoice = InvoiceMapper.INSTANCE.fromDto(payload);
        Invoice updated = service.update(id, invoice);

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
