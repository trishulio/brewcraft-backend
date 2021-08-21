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

import io.company.brewcraft.dto.AddMaterialPortionDto;
import io.company.brewcraft.dto.MaterialPortionDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.UpdateMaterialPortionDto;
import io.company.brewcraft.model.MaterialPortion;
import io.company.brewcraft.service.MaterialPortionService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.MaterialPortionMapper;
import io.company.brewcraft.util.controller.AttributeFilter;
import io.company.brewcraft.util.validator.Validator;

@RestController
@RequestMapping(path = "/api/v1/brews/mixtures/portions", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class MaterialPortionController extends BaseController {

    private MaterialPortionService materialPortionService;

    private MaterialPortionMapper materialPortionMapper = MaterialPortionMapper.INSTANCE;

    public MaterialPortionController(MaterialPortionService materialPortionService, AttributeFilter filter) {
        super(filter);
        this.materialPortionService = materialPortionService;
    }

    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public PageDto<MaterialPortionDto> getMaterialPortions(
            @RequestParam(name = "ids", required = false) Set<Long> ids,
            @RequestParam(name = "mixture_ids", required = false) Set<Long> mixtureIds,
            @RequestParam(name = "material_lot_ids", required = false) Set<Long> materialLotIds,
            @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
            @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
            @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
            @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size) {

        Page<MaterialPortion> materialPortionPage = materialPortionService.getMaterialPortions(ids, mixtureIds, materialLotIds, page, size, sort, orderAscending);

        List<MaterialPortionDto> materialPortionList = materialPortionPage.stream()
                .map(materialPortion -> materialPortionMapper.toDto(materialPortion)).collect(Collectors.toList());

        PageDto<MaterialPortionDto> dto = new PageDto<>(materialPortionList, materialPortionPage.getTotalPages(), materialPortionPage.getTotalElements());

        return dto;
    }

    @GetMapping(value = "/{materialPortionId}", consumes = MediaType.ALL_VALUE)
    public MaterialPortionDto getMaterialPortion(@PathVariable Long materialPortionId) {
        MaterialPortion materialPortion = materialPortionService.getMaterialPortion(materialPortionId);

        Validator.assertion(materialPortion != null, EntityNotFoundException.class, "MixtureRecording", materialPortionId.toString());

        return materialPortionMapper.toDto(materialPortion);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public List<MaterialPortionDto> addMMaterialPortion(@Valid @RequestBody List<AddMaterialPortionDto> addMaterialPortionDtos) {
        List<MaterialPortion> materialPortions = addMaterialPortionDtos.stream()
                                                                       .map(dto -> materialPortionMapper.fromDto(dto))
                                                                       .collect(Collectors.toList());

        List<MaterialPortion> addedMaterialPortions = materialPortionService.addMaterialPortions(materialPortions);

        return addedMaterialPortions.stream()
                                    .map(addedMaterialPortion -> materialPortionMapper.toDto(addedMaterialPortion))
                                    .collect(Collectors.toList());
    }

    @PutMapping("/{materialPortionId}")
    public MaterialPortionDto putMaterialPortion(@Valid @RequestBody UpdateMaterialPortionDto updateMaterialPortionDto, @PathVariable Long materialPortionId) {
        MaterialPortion materialPortion = materialPortionMapper.fromDto(updateMaterialPortionDto);

        MaterialPortion putMaterialPortion = materialPortionService.putMaterialPortion(materialPortionId, materialPortion);

        return materialPortionMapper.toDto(putMaterialPortion);
    }

    @PatchMapping("/{materialPortionId}")
    public MaterialPortionDto patchMaterialPortion(@Valid @RequestBody UpdateMaterialPortionDto updateMaterialPortionDto, @PathVariable Long materialPortionId) {
        MaterialPortion materialPortion = materialPortionMapper.fromDto(updateMaterialPortionDto);

        MaterialPortion patchedMaterialPortion = materialPortionService.patchMaterialPortion(materialPortionId, materialPortion);

        return materialPortionMapper.toDto(patchedMaterialPortion);
    }

    @DeleteMapping(value = "/{materialPortionId}", consumes = MediaType.ALL_VALUE)
    public void deleteMaterialPortion(@PathVariable Long materialPortionId) {
        materialPortionService.deleteMaterialPortion(materialPortionId);
    }
}
