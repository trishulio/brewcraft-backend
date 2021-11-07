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

import io.company.brewcraft.dto.FinishedGoodInventoryDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.model.FinishedGoodInventory;
import io.company.brewcraft.service.FinishedGoodInventoryService;
import io.company.brewcraft.service.mapper.FinishedGoodInventoryMapper;
import io.company.brewcraft.util.controller.AttributeFilter;

@RestController
@RequestMapping(path = "/api/v1/finished-goods/quantity", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class FinishedGoodInventoryController extends BaseController {
    private static FinishedGoodInventoryMapper mapper = FinishedGoodInventoryMapper.INSTANCE;

    private final FinishedGoodInventoryService finishedGoodInventoryService;

    @Autowired
    public FinishedGoodInventoryController(FinishedGoodInventoryService finishedGoodInventoryService, AttributeFilter filter) {
        super(filter);
        this.finishedGoodInventoryService = finishedGoodInventoryService;
    }

    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public PageDto<FinishedGoodInventoryDto> getFinishedGoodInventory(
        @RequestParam(required = false, name = "sku_ids") Set<Long> skuIds,
        @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
        @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
        @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
        @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size,
        @RequestParam(name = PROPNAME_ATTR, defaultValue = VALUE_DEFAULT_ATTR) Set<String> attributes
    ) {
        Page<FinishedGoodInventory> finishedGoods = this.finishedGoodInventoryService.getAll(
            skuIds, page, size, sort, orderAscending
        );

        return this.response(finishedGoods, attributes);
    }

    private PageDto<FinishedGoodInventoryDto> response(Page<FinishedGoodInventory> finishedGoods, Set<String> attributes) {
        final List<FinishedGoodInventoryDto> content = finishedGoods.stream().map(i -> mapper.toDto(i)).collect(Collectors.toList());
        content.forEach(finishedGood -> this.filter(finishedGood, attributes));

        final PageDto<FinishedGoodInventoryDto> dto = new PageDto<>();
        dto.setContent(content);
        dto.setTotalElements(finishedGoods.getTotalElements());
        dto.setTotalPages(finishedGoods.getTotalPages());
        return dto;
    }
}
