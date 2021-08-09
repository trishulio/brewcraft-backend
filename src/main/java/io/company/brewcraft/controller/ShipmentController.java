package io.company.brewcraft.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import io.company.brewcraft.model.Shipment;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.impl.ShipmentService;
import io.company.brewcraft.service.mapper.ShipmentMapper;
import io.company.brewcraft.util.controller.AttributeFilter;

@RestController
@RequestMapping(path = "/api/v1/purchases", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ShipmentController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(ShipmentController.class);

    private ShipmentService service;

    @Autowired
    public ShipmentController(ShipmentService service, AttributeFilter filter) {
        super(filter);
        this.service = service;
    }

    @GetMapping(value = "/shipments/{shipmentId}")
    public ShipmentDto getShipment(@PathVariable(name = "shipmentId") Long shipmentId) {
        log.debug("Fetching shipment with Id: {}", shipmentId);
        Shipment shipment = service.getShipment(shipmentId);

        if (shipment == null) {
            log.debug("No shipment found with Id: {}", shipmentId);
            throw new EntityNotFoundException("Shipment", shipmentId);
        }

        return ShipmentMapper.INSTANCE.toDto(shipment);
    }

    @GetMapping("/shipments")
    public PageDto<ShipmentDto> getShipments(
        @RequestParam(required = false, name = "ids") Set<Long> ids,
        @RequestParam(required = false, name = "exclude_ids") Set<Long> excludeIds,
        @RequestParam(required = false, name = "shipment_numbers") Set<String> shipmentNumbers,
        @RequestParam(required = false, name = "descriptions") Set<String> descriptions,
        @RequestParam(required = false, name = "status_ids") Set<Long> statusIds,
        @RequestParam(required = false, name = "delivery_due_date_from") LocalDateTime deliveryDueDateFrom,
        @RequestParam(required = false, name = "delivery_due_date_to") LocalDateTime deliveryDueDateTo,
        @RequestParam(required = false, name = "delivered_date_from") LocalDateTime deliveredDateFrom,
        @RequestParam(required = false, name = "delivered_date_to") LocalDateTime deliveredDateTo,
        @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
        @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
        @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
        @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size,
        @RequestParam(name = PROPNAME_ATTR, defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes
    ) {
        Page<Shipment> shipmentsPage = service.getShipments(ids, excludeIds, shipmentNumbers, descriptions, statusIds, deliveryDueDateFrom, deliveryDueDateTo, deliveredDateFrom, deliveredDateTo, sort, orderAscending, page, size);
        List<ShipmentDto> shipments = shipmentsPage.stream().map(shipment -> ShipmentMapper.INSTANCE.toDto(shipment)).collect(Collectors.toList());
        shipments.stream().forEach(shipment -> filter(shipment, attributes));

        return new PageDto<ShipmentDto>(shipments, shipmentsPage.getTotalPages(), shipmentsPage.getTotalElements());
    }

    @DeleteMapping("/shipments")
    public int deleteShipments(@RequestParam("ids") Set<Long> shipmentIds) {
        log.debug("Attempting to delete shipments with Ids: {}", shipmentIds);
        int count = service.delete(shipmentIds);
        log.debug("Number of shipments deleted: {}", count);

        return count;
    }

    @PutMapping("/shipments/{shipmentId}")
    public ShipmentDto putShipment(@PathVariable(name = "shipmentId") Long shipmentId, @RequestBody @Valid @NotNull UpdateShipmentDto updateDto) {
        log.debug("Updating shipment with Id: {}", shipmentId);
        Shipment update = ShipmentMapper.INSTANCE.fromDto(updateDto);

        Shipment updated = service.put(shipmentId, update);

        log.debug("Put successful");
        return ShipmentMapper.INSTANCE.toDto(updated);
    }

    @PatchMapping("/shipments/{shipmentId}")
    public ShipmentDto patchShipment(@PathVariable(name = "shipmentId") Long shipmentId, @RequestBody @Valid @NotNull UpdateShipmentDto updateDto) {
        log.debug("Patching shipment with Id: {}", shipmentId);
        Shipment update = ShipmentMapper.INSTANCE.fromDto(updateDto);

        Shipment updated = service.patch(shipmentId, update);

        log.debug("Patch successful");
        return ShipmentMapper.INSTANCE.toDto(updated);
    }

    @PostMapping("/shipments")
    public ShipmentDto addShipment(@RequestBody @Valid @NotNull AddShipmentDto addDto) {
        log.debug("Adding a new shipment item");
        Shipment shipment = ShipmentMapper.INSTANCE.fromDto(addDto);

        Shipment added = service.add(shipment);

        log.debug("Added a new shipment item with Id: {}", shipment.getId());
        return ShipmentMapper.INSTANCE.toDto(added);
    }

}
