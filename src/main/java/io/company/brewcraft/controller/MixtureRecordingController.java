package io.company.brewcraft.controller;

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

import io.company.brewcraft.dto.AddMixtureRecordingDto;
import io.company.brewcraft.dto.MixtureRecordingDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.UpdateMixtureRecordingDto;
import io.company.brewcraft.model.BaseMixtureRecording;
import io.company.brewcraft.model.MixtureRecording;
import io.company.brewcraft.model.UpdateMixtureRecording;
import io.company.brewcraft.service.MixtureRecordingService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.MixtureRecordingMapper;
import io.company.brewcraft.util.controller.AttributeFilter;
import io.company.brewcraft.util.validator.Validator;

@RestController
@RequestMapping(path = "/api/v1/mixtures/recordings", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class MixtureRecordingController extends BaseController {

    private MixtureRecordingService mixtureRecordingService;

    private MixtureRecordingMapper mixtureRecordingMapper = MixtureRecordingMapper.INSTANCE;

    public MixtureRecordingController(MixtureRecordingService mixtureRecordingService, AttributeFilter filter) {
        super(filter);
        this.mixtureRecordingService = mixtureRecordingService;
    }

    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public PageDto<MixtureRecordingDto> getMixtureRecordings(
            @RequestParam(name = "ids", required = false) Set<Long> ids,
            @RequestParam(name = "mixture_ids", required = false) Set<Long> mixtureIds,
            @RequestParam(name = "brew_stage_ids", required = false) Set<Long> brewStageIds,
            @RequestParam(name = "brew_ids", required = false) Set<Long> brewIds,
            @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
            @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
            @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
            @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size) {

        Page<MixtureRecording> mixtureRecordingPage = mixtureRecordingService.getMixtureRecordings(ids, mixtureIds, brewStageIds, brewIds, page, size, sort, orderAscending);

        List<MixtureRecordingDto> mixtureRecordingList = mixtureRecordingPage.stream()
                .map(mixtureRecording -> mixtureRecordingMapper.toDto(mixtureRecording)).collect(Collectors.toList());

        PageDto<MixtureRecordingDto> dto = new PageDto<>(mixtureRecordingList, mixtureRecordingPage.getTotalPages(), mixtureRecordingPage.getTotalElements());

        return dto;
    }

    @GetMapping(value = "/{mixtureRecordingId}", consumes = MediaType.ALL_VALUE)
    public MixtureRecordingDto getMixtureRecording(@PathVariable Long mixtureRecordingId) {
        MixtureRecording mixtureRecording = mixtureRecordingService.getMixtureRecording(mixtureRecordingId);

        Validator.assertion(mixtureRecording != null, EntityNotFoundException.class, "MixtureRecording", mixtureRecordingId.toString());

        return mixtureRecordingMapper.toDto(mixtureRecording);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public List<MixtureRecordingDto> addMixtureRecordings(@Valid @RequestBody List<AddMixtureRecordingDto> addMixtureRecordingDtos) {
        List<BaseMixtureRecording> mixtureRecordings = addMixtureRecordingDtos.stream()
                                                                              .map(addMixtureRecordingDto -> mixtureRecordingMapper.fromDto(addMixtureRecordingDto))
                                                                              .collect(Collectors.toList());

        List<MixtureRecording> addedMixtureRecordings = mixtureRecordingService.addMixtureRecordings(mixtureRecordings);

        return addedMixtureRecordings.stream()
                                     .map(addedMixtureRecording -> mixtureRecordingMapper.toDto(addedMixtureRecording))
                                     .collect(Collectors.toList());
    }

    @PutMapping("/{mixtureRecordingId}")
    public MixtureRecordingDto putMixtureRecording(@Valid @RequestBody UpdateMixtureRecordingDto updateMixtureRecordingDto, @PathVariable Long mixtureRecordingId) {
        MixtureRecording mixtureRecording = mixtureRecordingMapper.fromDto(updateMixtureRecordingDto);
        mixtureRecording.setId(mixtureRecordingId);

        MixtureRecording putMixtureRecording = mixtureRecordingService.putMixtureRecordings(List.of(mixtureRecording)).get(0);

        return mixtureRecordingMapper.toDto(putMixtureRecording);
    }

    @PutMapping("")
    public List<MixtureRecordingDto> putMixtureRecordings(@Valid @RequestBody List<UpdateMixtureRecordingDto> updateMixtureRecordingDtos) {
        List<UpdateMixtureRecording> mixtureRecordings = updateMixtureRecordingDtos.stream()
                                                                                   .map(updateMixtureRecordingDto -> mixtureRecordingMapper.fromDto(updateMixtureRecordingDto))
                                                                                   .collect(Collectors.toList());

        List<MixtureRecording> putMixtureRecordings = mixtureRecordingService.putMixtureRecordings(mixtureRecordings);

        return putMixtureRecordings.stream()
                                   .map(putMixtureRecording -> mixtureRecordingMapper.toDto(putMixtureRecording))
                                   .collect(Collectors.toList());
    }

    @PatchMapping("/{mixtureRecordingId}")
    public MixtureRecordingDto patchMixtureRecording(@Valid @RequestBody UpdateMixtureRecordingDto updateMixtureRecordingDto, @PathVariable Long mixtureRecordingId) {
        UpdateMixtureRecording mixtureRecording = mixtureRecordingMapper.fromDto(updateMixtureRecordingDto);
        mixtureRecording.setId(mixtureRecordingId);

        MixtureRecording patchedMixtureRecording = mixtureRecordingService.patchMixtureRecordings(List.of(mixtureRecording)).get(0);

        return mixtureRecordingMapper.toDto(patchedMixtureRecording);
    }

    @DeleteMapping(value = "", consumes = MediaType.ALL_VALUE)
    public void deleteMixtureRecordings(@RequestParam("ids") Set<Long> mixtureRecordingIds) {
        mixtureRecordingService.deleteMixtureRecordings(mixtureRecordingIds);
    }

    @DeleteMapping(value = "/{mixtureRecordingId}", consumes = MediaType.ALL_VALUE)
    public void deleteSku(@PathVariable Long mixtureRecordingId) {
        mixtureRecordingService.deleteMixtureRecordings(Set.of(mixtureRecordingId));
    }
}
