package io.company.brewcraft.controller;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.company.brewcraft.dto.FinishedGoodInventoryAggregationDto;
import io.company.brewcraft.dto.FinishedGoodInventoryDto;
import io.company.brewcraft.dto.FinishedGoodLotDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.model.FinishedGoodInventory;
import io.company.brewcraft.model.FinishedGoodInventoryAggregation;
import io.company.brewcraft.model.FinishedGoodLot;
import io.company.brewcraft.service.AggregationFunction;
import io.company.brewcraft.service.FinishedGoodInventoryAggregationFieldCollection;
import io.company.brewcraft.service.FinishedGoodInventoryService;
import io.company.brewcraft.service.ProcurementLotAggregationFieldCollection;
import io.company.brewcraft.service.mapper.FinishedGoodInventoryMapper;
import io.company.brewcraft.util.controller.AttributeFilter;

@RestController
@RequestMapping(path = "/api/v1/inventory/finished-goods/stock", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class FinishedGoodInventoryController extends BaseController {
    private static FinishedGoodInventoryMapper mapper = FinishedGoodInventoryMapper.INSTANCE;

    private final FinishedGoodInventoryService finishedGoodInventoryService;

    @Autowired
    public FinishedGoodInventoryController(FinishedGoodInventoryService finishedGoodInventoryService, AttributeFilter filter) {
        super(filter);
        this.finishedGoodInventoryService = finishedGoodInventoryService;
    }

    @GetMapping(value = "/quantity", consumes = MediaType.ALL_VALUE)
    public PageDto<FinishedGoodInventoryAggregationDto> getAllAggregation(
        @RequestParam(required = false, name = "sku_ids") Set<Long> skuIds,
        @RequestParam(name = "aggr_fn", defaultValue = "SUM") AggregationFunction aggrFn,
        @RequestParam(name = "group_by", defaultValue = "ID") FinishedGoodInventoryAggregationFieldCollection groupBy,
        @RequestParam(name = PROPNAME_SORT_BY, defaultValue = "sku.name") SortedSet<String> sort,
        @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
        @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
        @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size,
        @RequestParam(name = PROPNAME_ATTR, defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes
    ) {
        Page<FinishedGoodInventoryAggregation> finishedGoods = this.finishedGoodInventoryService.getAllAggregation(
            skuIds, aggrFn, groupBy.getFields(), page, size, sort, orderAscending
        );

        return this.aggregationResponse(finishedGoods, attributes);
    }

    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public PageDto<FinishedGoodInventoryDto> getAll(
        @RequestParam(required = false, name = "ids") Set<Long> ids,
        @RequestParam(required = false, name = "exclude_ids") Set<Long> excludeIds,
        @RequestParam(required = false, name = "sku_ids") Set<Long> skuIds,
        @RequestParam(required = false, name = "mixture_ids") Set<Long> mixtureIds,
        @RequestParam(required = false, name = "brew_stage_ids") Set<Long> brewStageIds,
        @RequestParam(required = false, name = "brew_ids") Set<Long> brewIds,
        @RequestParam(required = false, name = "brew_batch_ids") Set<String> brewBatchIds,
        @RequestParam(required = false, name = "product_ids") Set<Long> productIds,
        @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
        @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
        @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
        @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size,
        @RequestParam(name = PROPNAME_ATTR, defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes
    ) {
        final Page<FinishedGoodInventory> finishedGoods = this.finishedGoodInventoryService.getAll(
            ids,
            excludeIds,
            skuIds,
            mixtureIds,
            brewStageIds,
            brewIds,
            brewBatchIds,
            productIds,
            sort,
            orderAscending,
            page,
            size
        );

        return this.response(finishedGoods, attributes);
    }

    private PageDto<FinishedGoodInventoryAggregationDto> aggregationResponse(Page<FinishedGoodInventoryAggregation> finishedGoods, Set<String> attributes) {
        final List<FinishedGoodInventoryAggregationDto> content = finishedGoods.stream().map(i -> mapper.toDto(i)).collect(Collectors.toList());
        content.forEach(finishedGood -> this.filter(finishedGood, attributes));

        final PageDto<FinishedGoodInventoryAggregationDto> dto = new PageDto<>();
        dto.setContent(content);
        dto.setTotalElements(finishedGoods.getTotalElements());
        dto.setTotalPages(finishedGoods.getTotalPages());
        return dto;
    }

    private PageDto<FinishedGoodInventoryDto> response(Page<FinishedGoodInventory> finishedGoods, Set<String> attributes) {
        final List<FinishedGoodInventoryDto> content = finishedGoods.stream().map(i -> mapper.toDto(i)).toList();
        content.forEach(finishedGood -> this.filter(finishedGood, attributes));

        final PageDto<FinishedGoodInventoryDto> dto = new PageDto<>();
        dto.setContent(content);
        dto.setTotalElements(finishedGoods.getTotalElements());
        dto.setTotalPages(finishedGoods.getTotalPages());
        return dto;
    }
}
