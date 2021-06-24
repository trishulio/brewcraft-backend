package io.company.brewcraft.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.company.brewcraft.dto.MaterialLotDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.model.MaterialLot;
import io.company.brewcraft.service.AggregationFunction;
import io.company.brewcraft.service.MaterialLotInventoryService;
import io.company.brewcraft.service.mapper.MaterialLotMapper;
import io.company.brewcraft.util.controller.AttributeFilter;

@RestController
@RequestMapping(path = "/api/v1/inventory")
public class MaterialLotInventoryController extends BaseController {
    
    private MaterialLotInventoryService service;
    
    public MaterialLotInventoryController(MaterialLotInventoryService service, AttributeFilter filter) {
        super(filter);
        this.service = service;
    }

    @GetMapping("/procurements/quantity")
    public PageDto<MaterialLotDto> getAggregatedProcurementQuantity(
        @RequestParam(name = "ids", required = false) Set<Long> ids,
        @RequestParam(name = "excludeIds", required = false) Set<Long> excludeIds,
        @RequestParam(name = "lotNumbers", required = false) Set<String> lotNumbers,
        @RequestParam(name = "materialIds", required = false) Set<Long> materialIds,
        @RequestParam(name = "shipmentIds", required = false) Set<Long> shipmentIds,
        @RequestParam(name = "storageIds", required = false) Set<Long> storageIds,
        @RequestParam(name = "shipmentNumber", required = false) Set<String> shipmentNumbers,
        @RequestParam(name = "deliveredDateFrom", required = false) LocalDateTime deliveredDateFrom,
        @RequestParam(name = "deliveredDateTo", required = false) LocalDateTime deliveredDateTo,
        @RequestParam(name = "aggrFn", defaultValue = "SUM") AggregationFunction aggrFn,
        @RequestParam(name = "groupBy", defaultValue = "MATERIAL") MaterialLot.AggregationField[] groupBy,
        @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) Set<String> sort,
        @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
        @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
        @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size,
        @RequestParam(name = PROPNAME_ATTR, defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes
    ) {
        Page<MaterialLot> inventory = service.getAggregatedProcurementQuantity(
            ids,
            excludeIds,
            lotNumbers,
            materialIds,
            shipmentIds,
            storageIds,
            shipmentNumbers,
            deliveredDateFrom,
            deliveredDateTo,
            aggrFn,
            groupBy,
            sort,
            orderAscending,
            page,
            size
        );

        List<MaterialLotDto> contentDto = inventory.stream().map(i -> MaterialLotMapper.INSTANCE.toDto(i)).collect(Collectors.toList());
       
        return new PageDto<MaterialLotDto>(contentDto, inventory.getTotalPages(), inventory.getTotalElements());
    }
}
