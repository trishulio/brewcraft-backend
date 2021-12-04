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

import io.company.brewcraft.dto.BrewStageStatusDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.model.BrewStageStatus;
import io.company.brewcraft.service.BrewStageStatusService;
import io.company.brewcraft.service.mapper.BrewStageStatusMapper;
import io.company.brewcraft.util.controller.AttributeFilter;

@RestController
@RequestMapping(path = "/api/v1/brews/stages/statuses", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class BrewStageStatusController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(BrewStageStatusController.class);

    private BrewStageStatusService brewStageStatusService;

    private BrewStageStatusMapper brewStageStatusMapper = BrewStageStatusMapper.INSTANCE;

    public BrewStageStatusController(BrewStageStatusService brewStageStatusService, AttributeFilter filter) {
        super(filter);
        this.brewStageStatusService = brewStageStatusService;
    }

    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public PageDto<BrewStageStatusDto> getStatuses(
            @RequestParam(required = false) Set<Long> ids,
            @RequestParam(required = false) Set<String> names,
            @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
            @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
            @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
            @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size) {

        Page<BrewStageStatus> brewStageStatusPage = brewStageStatusService.getStatuses(ids, names, page, size, sort, orderAscending);

        List<BrewStageStatusDto> brewStageStatusList = brewStageStatusPage.stream()
                                                                          .map(brewStageStatus -> brewStageStatusMapper.toDto(brewStageStatus))
                                                                          .collect(Collectors.toList());

        PageDto<BrewStageStatusDto> dto = new PageDto<>(brewStageStatusList, brewStageStatusPage.getTotalPages(), brewStageStatusPage.getTotalElements());

        return dto;
    }
}
