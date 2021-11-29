package io.company.brewcraft.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
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

import io.company.brewcraft.dto.AddBrewStageDto;
import io.company.brewcraft.dto.BrewStageDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.UpdateBrewStageDto;
import io.company.brewcraft.model.BrewStage;
import io.company.brewcraft.service.BrewStageService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.BrewStageMapper;
import io.company.brewcraft.util.controller.AttributeFilter;
import io.company.brewcraft.util.validator.Validator;

@RestController
@RequestMapping(path = "/api/v1/brews/stages", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class BrewStageController extends BaseController {

    private BrewStageService brewStageService;

    private BrewStageMapper brewStageMapper = BrewStageMapper.INSTANCE;

    public BrewStageController(BrewStageService brewStageService, AttributeFilter filter) {
        super(filter);
        this.brewStageService = brewStageService;
    }

    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public PageDto<BrewStageDto> getBrewStages(
            @RequestParam(required = false) Set<Long> ids,
            @RequestParam(required = false, name = "brew_ids") Set<Long> brewIds,
            @RequestParam(required = false, name = "status_ids") Set<Long> statusIds,
            @RequestParam(required = false, name = "task_ids") Set<Long> taskIds,
            @RequestParam(required = false, name = "started_at_from") LocalDateTime startedAtFrom,
            @RequestParam(required = false, name = "started_at_to") LocalDateTime startedAtTo,
            @RequestParam(required = false, name = "ended_at_from") LocalDateTime endedAtFrom,
            @RequestParam(required = false, name = "ended_at_to") LocalDateTime endedAtTo,
            @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
            @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
            @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
            @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size) {

        Page<BrewStage> brewStagePage = brewStageService.getBrewStages(ids, brewIds, statusIds, taskIds, startedAtFrom, startedAtTo, endedAtFrom, endedAtTo, page, size, sort, orderAscending);

        List<BrewStageDto> brewStageList = brewStagePage.stream()
                                                        .map(brewStage -> brewStageMapper.toDto(brewStage))
                                                        .collect(Collectors.toList());

        PageDto<BrewStageDto> dto = new PageDto<>(brewStageList, brewStagePage.getTotalPages(), brewStagePage.getTotalElements());

        return dto;
    }

    @GetMapping(value = "/{stageId}", consumes = MediaType.ALL_VALUE)
    public BrewStageDto getBrewStage(@PathVariable Long brewStageId) {
        BrewStage brewStage = brewStageService.getBrewStage(brewStageId);

        Validator.assertion(brewStage != null, EntityNotFoundException.class, "BrewStage", brewStageId.toString());

        return brewStageMapper.toDto(brewStage);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public List<BrewStageDto> addBrewStages(@Valid @RequestBody List<AddBrewStageDto> addBrewStageDtos) {
        List<BrewStage> brewStages = addBrewStageDtos.stream()
                                                     .map(addBrewStageDto -> brewStageMapper.fromDto(addBrewStageDto))
                                                     .collect(Collectors.toList());

        List<BrewStage> addedBrewStages = brewStageService.addBrewStages(brewStages);

        return addedBrewStages.stream()
                              .map(addedBrewStage -> brewStageMapper.toDto(addedBrewStage))
                              .collect(Collectors.toList());
    }

    @PutMapping("/{stageId}")
    public BrewStageDto putBrewStage(@Valid @RequestBody UpdateBrewStageDto updateBrewStageDto, @PathVariable Long stageId) {
        BrewStage brewStage = brewStageMapper.fromDto(updateBrewStageDto);

        BrewStage putBrewStage = brewStageService.putBrewStage(stageId, brewStage);

        return brewStageMapper.toDto(putBrewStage);
    }

    @PatchMapping("/{stageId}")
    public BrewStageDto patchBrewStage(@PathVariable Long stageId, @Valid @RequestBody UpdateBrewStageDto updateBrewStageDto) {
        BrewStage brewStage = brewStageMapper.fromDto(updateBrewStageDto);

        BrewStage patchedBrewStage = brewStageService.patchBrewStage(stageId, brewStage);

        return brewStageMapper.toDto(patchedBrewStage);
    }

    @DeleteMapping(value = "/{stageId}", consumes = MediaType.ALL_VALUE)
    public void deleteBrewStage(@PathVariable Long stageId) {
        brewStageService.deleteBrewStage(stageId);
    }
}
