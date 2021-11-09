package io.company.brewcraft.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.procurement.AddProcurementDto;
import io.company.brewcraft.dto.procurement.ProcurementDto;
import io.company.brewcraft.dto.procurement.ProcurementIdDto;
import io.company.brewcraft.dto.procurement.UpdateProcurementDto;
import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.model.procurement.BaseProcurement;
import io.company.brewcraft.model.procurement.Procurement;
import io.company.brewcraft.model.procurement.ProcurementId;
import io.company.brewcraft.model.procurement.ProcurementItem;
import io.company.brewcraft.model.procurement.UpdateProcurement;
import io.company.brewcraft.service.impl.procurement.ProcurementService;
import io.company.brewcraft.service.mapper.procurement.ProcurementIdMapper;
import io.company.brewcraft.service.mapper.procurement.ProcurementMapper;
import io.company.brewcraft.util.controller.AttributeFilter;

@RestController
@RequestMapping(path = "/api/v1/procurements", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProcurementController extends BaseController {

    private ProcurementService service;

    private CrudControllerService<
        ProcurementId,
        Procurement,
        BaseProcurement<InvoiceItem, MaterialLot, ProcurementItem>,
        UpdateProcurement<InvoiceItem, MaterialLot, ProcurementItem>,
        ProcurementDto,
        AddProcurementDto,
        UpdateProcurementDto
    > controller;

    @Autowired
    public ProcurementController(AttributeFilter filter, ProcurementService service) {
        this(new CrudControllerService<>(filter, ProcurementMapper.INSTANCE, service, "Procurement"), service);
    }

    protected ProcurementController(
            CrudControllerService<
            ProcurementId,
            Procurement,
            BaseProcurement<InvoiceItem, MaterialLot, ProcurementItem>,
            UpdateProcurement<InvoiceItem, MaterialLot, ProcurementItem>,
            ProcurementDto,
            AddProcurementDto,
            UpdateProcurementDto
        > controller,
        ProcurementService service) {
        this.controller = controller;
        this.service = service;
    }

    @GetMapping("/")
    public PageDto<ProcurementDto> getAll(
        // shipment filters
        @RequestParam(required = false, name = "shipment_ids") Set<Long> shipmentIds,
        @RequestParam(required = false, name = "shipment_exclude_ids") Set<Long> shipmentExcludeIds,
        @RequestParam(required = false, name = "shipment_numbers") Set<String> shipmentNumbers,
        @RequestParam(required = false, name = "descriptions") Set<String> descriptions,
        @RequestParam(required = false, name = "shipment_status_ids") Set<Long> shipmentStatusIds,
        @RequestParam(required = false, name = "delivery_due_date_from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deliveryDueDateFrom,
        @RequestParam(required = false, name = "delivery_due_date_to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deliveryDueDateTo,
        @RequestParam(required = false, name = "delivered_date_from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deliveredDateFrom,
        @RequestParam(required = false, name = "delivered_date_to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime deliveredDateTo,
        // invoice filters
        @RequestParam(required = false, name = "invoice_ids") Set<Long> invoiceIds,
        @RequestParam(required = false, name = "invoice_exclude_ids") Set<Long> invoiceExcludeIds,
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
        // misc
        @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
        @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
        @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
        @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size,
        @RequestParam(name = PROPNAME_ATTR, defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes
    ) {
        Page<Procurement> procurements = this.service.getAll(
            // shipment filters
            shipmentIds,
            shipmentExcludeIds,
            shipmentNumbers,
            descriptions,
            shipmentStatusIds,
            deliveryDueDateFrom,
            deliveryDueDateTo,
            deliveredDateFrom,
            deliveredDateTo,
            // invoice filters
            invoiceIds,
            invoiceExcludeIds,
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
            // misc
            sort,
            orderAscending,
            page,
            size
        );

        return this.controller.getAll(procurements, attributes);
    }

    @GetMapping("/{shipmentId}/{invoiceId}")
    public ProcurementDto get(@PathVariable(required = true, name = "shipmentId") Long shipmentId, @PathVariable(required = true, name = "invoiceId") Long invoiceId, @RequestParam(name = PROPNAME_ATTR, defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
        ProcurementId id = new ProcurementId(shipmentId, invoiceId);
        return this.controller.get(id, attributes);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ProcurementDto> add(@Valid @RequestBody @NotNull List<AddProcurementDto> dtos) {
        return this.controller.add(dtos);
    }

    @PutMapping("/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<ProcurementDto> put(@Valid @RequestBody @NotNull List<UpdateProcurementDto> dtos) {
        return this.controller.put(dtos);
    }

    @PatchMapping("/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<ProcurementDto> patch(@Valid @RequestBody @NotNull List<UpdateProcurementDto> dtos) {
        return this.controller.patch(dtos);
    }

    @DeleteMapping("/")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public int delete(@Valid @RequestBody @NotNull Set<ProcurementIdDto> ids) {
        Set<ProcurementId> pIds = ids.stream().map(id -> ProcurementIdMapper.INSTANCE.fromDto(id)).collect(Collectors.toSet());
        return this.controller.delete(pIds);
    }
}
