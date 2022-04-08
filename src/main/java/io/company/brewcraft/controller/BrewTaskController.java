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

import io.company.brewcraft.dto.BrewTaskDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.model.BrewTask;
import io.company.brewcraft.service.BrewTaskService;
import io.company.brewcraft.service.mapper.BrewTaskMapper;
import io.company.brewcraft.util.controller.AttributeFilter;

@RestController
@RequestMapping(path = "/api/v1/brews/tasks")
public class BrewTaskController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(BrewTaskController.class);

    private BrewTaskService brewTaskService;

    private BrewTaskMapper brewTaskMapper = BrewTaskMapper.INSTANCE;

    public BrewTaskController(BrewTaskService brewTaskService, AttributeFilter filter) {
        super(filter);
        this.brewTaskService = brewTaskService;
    }

    @GetMapping(value = "", consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PageDto<BrewTaskDto> getBrewTasks(
            @RequestParam(required = false) Set<Long> ids,
            @RequestParam(required = false) Set<String> names,
            @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
            @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
            @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
            @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size) {
        Page<BrewTask> brewTaskPage = brewTaskService.getTasks(ids, names, page, size, sort, orderAscending);

        List<BrewTaskDto> brewTaskList = brewTaskPage.stream()
                                                     .map(brewTask -> brewTaskMapper.toDto(brewTask))
                                                     .toList();

        PageDto<BrewTaskDto> dto = new PageDto<>(brewTaskList, brewTaskPage.getTotalPages(), brewTaskPage.getTotalElements());

        return dto;
    }
}
