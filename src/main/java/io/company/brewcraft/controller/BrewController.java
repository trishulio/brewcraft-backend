package io.company.brewcraft.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.company.brewcraft.dto.AddBrewDto;
import io.company.brewcraft.dto.BrewDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.UpdateBrewDto;
import io.company.brewcraft.model.Brew;
import io.company.brewcraft.service.BrewService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.BrewMapper;
import io.company.brewcraft.util.controller.AttributeFilter;
import io.company.brewcraft.util.validator.Validator;

@RestController
@RequestMapping(path = "/api/v1/brews", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class BrewController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(BrewController.class);

    private BrewService brewService;

    private BrewMapper brewMapper = BrewMapper.INSTANCE;

    public BrewController(BrewService brewService, AttributeFilter filter) {
        super(filter);
        this.brewService = brewService;
    }

    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public PageDto<BrewDto> getBrews(
            @RequestParam(required = false) Set<Long> ids,
            @RequestParam(required = false, name = "batch_ids") Set<String> batchIds,
            @RequestParam(required = false, name = "names") Set<String> names,
            @RequestParam(required = false, name = "product_ids") Set<Long> productIds,
            @RequestParam(required = false, name = "stage_task_ids") Set<Long> stageTaskIds,
            @RequestParam(required = false, name = "exclude_stage_task_ids") Set<Long> excludeStageTaskIds,
            @RequestParam(required = false, name = "started_at_from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startedAtFrom,
            @RequestParam(required = false, name = "started_at_to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startedAtTo,
            @RequestParam(required = false, name = "ended_at_from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endedAtFrom,
            @RequestParam(required = false, name = "ended_at_to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endedAtTo,
            @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
            @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
            @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
            @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size) {

        Page<Brew> brewPage = brewService.getBrews(ids, batchIds, names, productIds, stageTaskIds, excludeStageTaskIds, startedAtFrom, startedAtTo, endedAtFrom, endedAtTo, page, size, sort, orderAscending);

        List<BrewDto> brewList = brewPage.stream()
                                         .map(brew -> brewMapper.toDto(brew))
                                         .collect(Collectors.toList());

        PageDto<BrewDto> dto = new PageDto<>(brewList, brewPage.getTotalPages(), brewPage.getTotalElements());

        return dto;
    }

    @GetMapping(value = "/{brewId}", consumes = MediaType.ALL_VALUE)
    public BrewDto getBrew(@PathVariable Long brewId) {
        Brew brew = brewService.getBrew(brewId);

        Validator.assertion(brew != null, EntityNotFoundException.class, "Brew", brewId.toString());

        return brewMapper.toDto(brew);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public BrewDto addBrew(@Valid @RequestBody AddBrewDto addBrewDto) {
        Brew brew = brewMapper.fromDto(addBrewDto);

        Brew addedBrew = brewService.addBrew(brew);

        return brewMapper.toDto(addedBrew);
    }

    @PutMapping("/{brewId}")
    public BrewDto putBrew(@PathVariable Long brewId, @Valid @RequestBody UpdateBrewDto updateBrewDto) {
        Brew brew = brewMapper.fromDto(updateBrewDto);

        Brew putBrew = brewService.putBrew(brewId, brew);

        return brewMapper.toDto(putBrew);
    }

    @PatchMapping("/{brewId}")
    public BrewDto patchBrew(@PathVariable Long brewId, @Valid @RequestBody UpdateBrewDto updateBrewDto) {
        Brew brew = brewMapper.fromDto(updateBrewDto);

        Brew patchedBrew = brewService.patchBrew(brewId, brew);

        return brewMapper.toDto(patchedBrew);
    }

    @DeleteMapping(value = "/{brewId}", consumes = MediaType.ALL_VALUE)
    public void deleteBrew(@PathVariable Long brewId) {
        brewService.deleteBrew(brewId);
    }
}
