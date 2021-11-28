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

import io.company.brewcraft.dto.AddMixtureMaterialPortionDto;
import io.company.brewcraft.dto.MixtureMaterialPortionDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.UpdateMixtureMaterialPortionDto;
import io.company.brewcraft.model.BaseMixtureMaterialPortion;
import io.company.brewcraft.model.MixtureMaterialPortion;
import io.company.brewcraft.model.UpdateMixtureMaterialPortion;
import io.company.brewcraft.service.MixtureMaterialPortionService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.MixtureMaterialPortionMapper;
import io.company.brewcraft.util.controller.AttributeFilter;
import io.company.brewcraft.util.validator.Validator;

@RestController
@RequestMapping(path = "/api/v1/brews/mixtures/portions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class MixtureMaterialPortionController extends BaseController {

    private MixtureMaterialPortionService materialPortionService;

    private MixtureMaterialPortionMapper materialPortionMapper = MixtureMaterialPortionMapper.INSTANCE;

    public MixtureMaterialPortionController(MixtureMaterialPortionService materialPortionService, AttributeFilter filter) {
        super(filter);
        this.materialPortionService = materialPortionService;
    }

    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public PageDto<MixtureMaterialPortionDto> getMaterialPortions(
            @RequestParam(name = "ids", required = false) Set<Long> ids,
            @RequestParam(name = "mixture_ids", required = false) Set<Long> mixtureIds,
            @RequestParam(name = "material_lot_ids", required = false) Set<Long> materialLotIds,
            @RequestParam(name = "brew_stage_ids", required = false) Set<Long> brewStageIds,
            @RequestParam(name = "brew_ids", required = false) Set<Long> brewIds,
            @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
            @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
            @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
            @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size) {

        Page<MixtureMaterialPortion> materialPortionPage = materialPortionService.getMaterialPortions(ids, mixtureIds, materialLotIds, brewStageIds, brewIds, page, size, sort, orderAscending);

        List<MixtureMaterialPortionDto> materialPortionList = materialPortionPage.stream()
                                                                                 .map(materialPortion -> materialPortionMapper.toDto(materialPortion))
                                                                                 .collect(Collectors.toList());

        PageDto<MixtureMaterialPortionDto> dto = new PageDto<>(materialPortionList, materialPortionPage.getTotalPages(), materialPortionPage.getTotalElements());

        return dto;
    }

    @GetMapping(value = "/{materialPortionId}", consumes = MediaType.ALL_VALUE)
    public MixtureMaterialPortionDto getMaterialPortion(@PathVariable Long materialPortionId) {
        MixtureMaterialPortion materialPortion = materialPortionService.getMaterialPortion(materialPortionId);

        Validator.assertion(materialPortion != null, EntityNotFoundException.class, "MixtureMaterialPortion", materialPortionId.toString());

        return materialPortionMapper.toDto(materialPortion);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public List<MixtureMaterialPortionDto> addMMaterialPortions(@Valid @RequestBody List<AddMixtureMaterialPortionDto> addMaterialPortionDtos) {
        List<BaseMixtureMaterialPortion> materialPortions = addMaterialPortionDtos.stream()
                                                                                  .map(dto -> materialPortionMapper.fromDto(dto))
                                                                                  .collect(Collectors.toList());

        List<MixtureMaterialPortion> addedMaterialPortions = materialPortionService.addMaterialPortions(materialPortions);

        return addedMaterialPortions.stream()
                                    .map(addedMaterialPortion -> materialPortionMapper.toDto(addedMaterialPortion))
                                    .collect(Collectors.toList());
    }

    @PutMapping("/{materialPortionId}")
    public MixtureMaterialPortionDto putMaterialPortion(@PathVariable Long materialPortionId, @Valid @RequestBody UpdateMixtureMaterialPortionDto updateMaterialPortionDto) {
        MixtureMaterialPortion materialPortion = materialPortionMapper.fromDto(updateMaterialPortionDto);
        materialPortion.setId(materialPortionId);

        MixtureMaterialPortion putMaterialPortion = materialPortionService.putMaterialPortions(List.of(materialPortion)).get(0);

        return materialPortionMapper.toDto(putMaterialPortion);
    }

    @PutMapping("")
    public List<MixtureMaterialPortionDto> putMaterialPortions(@Valid @RequestBody List<UpdateMixtureMaterialPortionDto> updateMaterialPortionDtos) {
        List<UpdateMixtureMaterialPortion> materialPortions = updateMaterialPortionDtos.stream()
                                                                                       .map(updateMaterialPortionDto -> materialPortionMapper.fromDto(updateMaterialPortionDto))
                                                                                       .collect(Collectors.toList());

            List<MixtureMaterialPortion> putMaterialPortions = materialPortionService.putMaterialPortions(materialPortions);

            return putMaterialPortions.stream()
                                      .map(putMaterialPortion -> materialPortionMapper.toDto(putMaterialPortion))
                                      .collect(Collectors.toList());

    }

    @PatchMapping("/{materialPortionId}")
    public MixtureMaterialPortionDto patchMaterialPortion(@PathVariable Long materialPortionId, @Valid @RequestBody UpdateMixtureMaterialPortionDto updateMaterialPortionDto) {
        MixtureMaterialPortion materialPortion = materialPortionMapper.fromDto(updateMaterialPortionDto);
        materialPortion.setId(materialPortionId);

        MixtureMaterialPortion patchedMaterialPortion = materialPortionService.patchMaterialPortions(List.of(materialPortion)).get(0);

        return materialPortionMapper.toDto(patchedMaterialPortion);
    }

    @DeleteMapping(value = "", consumes = MediaType.ALL_VALUE)
    public void deleteMaterialPortion(@RequestParam("ids") Set<Long> materialPortionIds) {
        materialPortionService.deleteMaterialPortions(materialPortionIds);
    }
}
