package io.company.brewcraft.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.ProcurementLotDto;
import io.company.brewcraft.dto.StockLotDto;
import io.company.brewcraft.model.ProcurementLot;
import io.company.brewcraft.model.StockLot;
import io.company.brewcraft.service.AggregationFunction;
import io.company.brewcraft.service.LotAggregationService;
import io.company.brewcraft.service.ProcurementLotAggregationFieldCollection;
import io.company.brewcraft.service.StockLotAggregationFieldCollection;
import io.company.brewcraft.service.mapper.ProcurementLotMapper;
import io.company.brewcraft.service.mapper.StockLotMapper;
import io.company.brewcraft.util.controller.AttributeFilter;

@RestController
@RequestMapping(path = "/api/v1/inventory")
public class LotAggregationController extends BaseController {

    private final LotAggregationService service;

    public LotAggregationController(LotAggregationService service, AttributeFilter filter) {
        super(filter);
        this.service = service;
    }

    @GetMapping("/procurements/quantity")
    public PageDto<ProcurementLotDto> getAggregatedProcurementQuantity(
        @RequestParam(name = "ids", required = false) Set<Long> ids,
        @RequestParam(name = "exclude_ids", required = false) Set<Long> excludeIds,
        @RequestParam(name = "lot_numbers", required = false) Set<String> lotNumbers,
        @RequestParam(name = "material_ids", required = false) Set<Long> materialIds,
        @RequestParam(name = "material_category_ids", required = false) Set<Long> materialCategoryIds,
        @RequestParam(name = "shipment_ids", required = false) Set<Long> shipmentIds,
        @RequestParam(name = "storage_ids", required = false) Set<Long> storageIds,
        @RequestParam(name = "shipment_numbers", required = false) Set<String> shipmentNumbers,
        @RequestParam(name = "delivered_date_from", required = false) LocalDateTime deliveredDateFrom,
        @RequestParam(name = "delivered_date_to", required = false) LocalDateTime deliveredDateTo,
        @RequestParam(name = "aggr_fn", defaultValue = "SUM") AggregationFunction aggrFn,
        @RequestParam(name = "group_by", defaultValue = "ID") ProcurementLotAggregationFieldCollection groupBy,
        @RequestParam(name = BaseController.PROPNAME_SORT_BY, defaultValue = "material.name") SortedSet<String> sort,
        @RequestParam(name = BaseController.PROPNAME_ORDER_ASC, defaultValue = BaseController.VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
        @RequestParam(name = BaseController.PROPNAME_PAGE_INDEX, defaultValue = BaseController.VALUE_DEFAULT_PAGE_INDEX) int page,
        @RequestParam(name = BaseController.PROPNAME_PAGE_SIZE, defaultValue = BaseController.VALUE_DEFAULT_PAGE_SIZE) int size,
        @RequestParam(name = BaseController.PROPNAME_ATTR, defaultValue = BaseController.VALUE_DEFAULT_ATTR) Set<String> attributes
    ) {
        final Page<ProcurementLot> inventory = this.service.getAggregatedProcurementQuantity(
            ids,
            excludeIds,
            lotNumbers,
            materialIds,
            materialCategoryIds,
            shipmentIds,
            storageIds,
            shipmentNumbers,
            deliveredDateFrom,
            deliveredDateTo,
            aggrFn,
            groupBy.getFields(),
            sort,
            orderAscending,
            page,
            size
        );

        final List<ProcurementLotDto> contentDto = inventory.stream().map(i -> ProcurementLotMapper.INSTANCE.toDto(i)).toList();

        return new PageDto<>(contentDto, inventory.getTotalPages(), inventory.getTotalElements());
    }

    @GetMapping("/stock/quantity")
    public PageDto<StockLotDto> getAggregatedStockQuantity(
        @RequestParam(name = "ids", required = false) Set<Long> ids,
        @RequestParam(name = "exclude_ids", required = false) Set<Long> excludeIds,
        @RequestParam(name = "lot_numbers", required = false) Set<String> lotNumbers,
        @RequestParam(name = "material_ids", required = false) Set<Long> materialIds,
        @RequestParam(name = "material_category_ids", required = false) Set<Long> materialCategoryIds,
        @RequestParam(name = "shipment_ids", required = false) Set<Long> shipmentIds,
        @RequestParam(name = "storage_ids", required = false) Set<Long> storageIds,
        @RequestParam(name = "shipment_numbers", required = false) Set<String> shipmentNumbers,
        @RequestParam(name = "delivered_date_from", required = false) LocalDateTime deliveredDateFrom,
        @RequestParam(name = "delivered_date_to", required = false) LocalDateTime deliveredDateTo,
        @RequestParam(name = "aggr_fn", defaultValue = "SUM") AggregationFunction aggrFn,
        @RequestParam(name = "group_by", defaultValue = "ID") StockLotAggregationFieldCollection groupBy,
        @RequestParam(name = BaseController.PROPNAME_SORT_BY, defaultValue = "material.name") SortedSet<String> sort,
        @RequestParam(name = BaseController.PROPNAME_ORDER_ASC, defaultValue = BaseController.VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
        @RequestParam(name = BaseController.PROPNAME_PAGE_INDEX, defaultValue = BaseController.VALUE_DEFAULT_PAGE_INDEX) int page,
        @RequestParam(name = BaseController.PROPNAME_PAGE_SIZE, defaultValue = BaseController.VALUE_DEFAULT_PAGE_SIZE) int size,
        @RequestParam(name = BaseController.PROPNAME_ATTR, defaultValue = BaseController.VALUE_DEFAULT_ATTR) Set<String> attributes
    ) {
        final Page<StockLot> inventory = this.service.getAggregatedStockQuantity(
            ids,
            excludeIds,
            lotNumbers,
            materialIds,
            materialCategoryIds,
            shipmentIds,
            storageIds,
            shipmentNumbers,
            deliveredDateFrom,
            deliveredDateTo,
            aggrFn,
            groupBy.getFields(),
            sort,
            orderAscending,
            page,
            size
        );

        final List<StockLotDto> contentDto = inventory.stream().map(i -> StockLotMapper.INSTANCE.toDto(i)).toList();

        return new PageDto<>(contentDto, inventory.getTotalPages(), inventory.getTotalElements());
    }
}
