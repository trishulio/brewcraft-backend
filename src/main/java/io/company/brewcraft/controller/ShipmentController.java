package io.company.brewcraft.controller;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import io.company.brewcraft.dto.ShipmentDto;
import io.company.brewcraft.dto.UpdateShipmentDto;
import io.company.brewcraft.pojo.Shipment;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.impl.ShipmentService;
import io.company.brewcraft.service.mapper.ShipmentMapper;
import io.company.brewcraft.util.validator.Validator;

@RestController
@RequestMapping(path = "/api/v1/purchases", produces = MediaType.APPLICATION_PROBLEM_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class ShipmentController {
    private static final Logger log = LoggerFactory.getLogger(ShipmentController.class);

    private ShipmentService service;

    @Autowired
    public ShipmentController(ShipmentService service) {
        this.service = service;
    }

    @GetMapping(value = "/{shipmentId}")
    public ShipmentDto getShipment(@PathVariable(name = "shipmentId") Long shipmentId) {
        log.debug("Fetching shipment with Id: {}", shipmentId);
        Shipment shipment = service.getShipment(new Validator(), shipmentId);

        if (shipment == null) {
            log.debug("No shipment found with Id: {}", shipmentId);
            throw new EntityNotFoundException("Shipment", shipmentId);
        }

        return ShipmentMapper.INSTANCE.toDto(shipment);
    }

    @DeleteMapping("/")
    public int deleteShipments(@RequestParam("ids") Set<Long> shipmentIds) {
        log.debug("Attempting to delete shipments with Ids: {}", shipmentIds);
        int count = service.delete(new Validator(), shipmentIds);
        log.debug("Number of shipments deleted: {}", count);

        return count;
    }

    @PutMapping("/{shipmentId}")
    public ShipmentDto putShipment(@PathVariable(name = "shipmentId") Long shipmentId, @RequestBody @Valid @NotNull UpdateShipmentDto updateDto) {
        log.debug("Updating shipment with Id: {}", shipmentId);
        Shipment update = ShipmentMapper.INSTANCE.fromDto(updateDto);

        Shipment updated = service.put(new Validator(), updateDto.getInvoiceId(), shipmentId, update);

        log.debug("Put successful");
        return ShipmentMapper.INSTANCE.toDto(updated);
    }

    @PatchMapping("/{shipmentId}")
    public ShipmentDto patchShipment(@PathVariable(name = "shipmentId") Long shipmentId, @RequestBody @Valid @NotNull UpdateShipmentDto updateDto) {
        log.debug("Patching shipment with Id: {}", shipmentId);
        Shipment update = ShipmentMapper.INSTANCE.fromDto(updateDto);

        Shipment updated = service.patch(new Validator(), updateDto.getInvoiceId(), shipmentId, update);

        log.debug("Patch successful");
        return ShipmentMapper.INSTANCE.toDto(updated);
    }

    @PostMapping("")
    public ShipmentDto addShipment(UpdateShipmentDto addDto) {
        log.debug("Adding a new shipment item");
        Shipment shipment = ShipmentMapper.INSTANCE.fromDto(addDto);

        Shipment added = service.add(new Validator(), addDto.getInvoiceId(), shipment);

        log.debug("Added a new shipment item with Id: {}", shipment.getId());
        return ShipmentMapper.INSTANCE.toDto(added);
    }

}
