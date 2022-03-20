package io.company.brewcraft.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.bind.annotation.RestController;

import io.company.brewcraft.dto.AddShipmentDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.ShipmentDto;
import io.company.brewcraft.dto.UpdateShipmentDto;
import io.company.brewcraft.model.BaseMaterialLot;
import io.company.brewcraft.model.BaseShipment;
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.model.UpdateMaterialLot;
import io.company.brewcraft.model.UpdateShipment;
import io.company.brewcraft.service.impl.ShipmentService;
import io.company.brewcraft.service.mapper.ShipmentMapper;
import io.company.brewcraft.util.controller.AttributeFilter;

@RestController
@RequestMapping(path = "/api/v1/purchases/shipments", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ShipmentController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(ShipmentController.class);

    private CrudControllerService<
        Long,
        Shipment,
        BaseShipment<? extends BaseMaterialLot<?>>,
        UpdateShipment<? extends UpdateMaterialLot<?>>,
        ShipmentDto,
        AddShipmentDto,
        UpdateShipmentDto
    > controller;

    private final ShipmentService shipmentService;

    protected ShipmentController(CrudControllerService<
            Long,
            Shipment,
            BaseShipment<? extends BaseMaterialLot<?>>,
            UpdateShipment<? extends UpdateMaterialLot<?>>,
            ShipmentDto,
            AddShipmentDto,
            UpdateShipmentDto
        > controller, ShipmentService shipmentService)
    {
        this.controller = controller;
        this.shipmentService = shipmentService;
    }

    @Autowired
    public ShipmentController(ShipmentService shipmentService, AttributeFilter filter) {
        this(new CrudControllerService<>(filter, ShipmentMapper.INSTANCE, shipmentService, "Shipment"), shipmentService);
    }

    @GetMapping(value = "/{shipmentId}")
    public ShipmentDto getShipment(@PathVariable(name = "shipmentId") Long shipmentId, @RequestParam(name = PROPNAME_ATTR, defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes) {
        return this.controller.get(shipmentId, attributes);
    }

    @GetMapping("")
    public PageDto<ShipmentDto> getShipments(
        // shipment filters
        @RequestParam(required = false, name = "ids") Set<Long> ids,
        @RequestParam(required = false, name = "exclude_ids") Set<Long> excludeIds,
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
        final Page<Shipment> shipmentsPage = this.shipmentService.getShipments(
            // shipment filters
            ids,
            excludeIds,
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

        return this.controller.getAll(shipmentsPage, attributes);
    }

    @DeleteMapping
    public long deleteShipments(@RequestParam("ids") Set<Long> shipmentIds) {
        return this.controller.delete(shipmentIds);
    }

    @PutMapping
    public List<ShipmentDto> putShipment(@RequestBody @Valid @NotNull List<UpdateShipmentDto> updateDtos) {
        return this.controller.put(updateDtos);
    }

    @PatchMapping
    public List<ShipmentDto> patchShipment(@RequestBody @Valid @NotNull List<UpdateShipmentDto> updateDtos) {
        return this.controller.patch(updateDtos);
    }

    @PostMapping
    public List<ShipmentDto> addShipment(@RequestBody @Valid @NotNull List<AddShipmentDto> addDtos) {
        return this.controller.add(addDtos);
    }
}
