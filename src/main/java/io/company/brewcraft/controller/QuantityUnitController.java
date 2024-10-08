package io.company.brewcraft.controller;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.UnitDto;
import io.company.brewcraft.model.UnitEntity;
import io.company.brewcraft.service.QuantityUnitService;
import io.company.brewcraft.service.mapper.QuantityUnitMapper;
import io.company.brewcraft.util.controller.AttributeFilter;

@RestController
@RequestMapping(path = "/api/v1/quantity/units")
public class QuantityUnitController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(QuantityUnitController.class);

    private QuantityUnitService quantityUnitService;

    private QuantityUnitMapper quantityUnitMapper = QuantityUnitMapper.INSTANCE;

    public QuantityUnitController(QuantityUnitService quantityUnitService, AttributeFilter filter) {
        super(filter);
        this.quantityUnitService = quantityUnitService;
    }

    @GetMapping(value = "", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PageDto<UnitDto> getUnits(@RequestParam(required = false) Set<String> symbols,
            @RequestParam(name = PROPNAME_SORT_BY, defaultValue = UnitEntity.FIELD_SYMBOL) SortedSet<String> sort,
            @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
            @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
            @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size) {
        Page<UnitEntity> unitsPage = quantityUnitService.getUnits(symbols, sort, orderAscending, page, size);

        List<UnitDto> userRoles = unitsPage.stream()
                                           .map(unit -> quantityUnitMapper.toDto(unit))
                                           .toList();

        PageDto<UnitDto> dto = new PageDto<UnitDto>(userRoles, unitsPage.getTotalPages(), unitsPage.getTotalElements());

        return dto;
    }
}
