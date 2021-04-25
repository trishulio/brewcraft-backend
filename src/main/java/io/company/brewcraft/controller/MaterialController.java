package io.company.brewcraft.controller;

import java.util.List;
import java.util.Set;
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

import io.company.brewcraft.dto.AddMaterialDto;
import io.company.brewcraft.dto.MaterialDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.UpdateMaterialDto;
import io.company.brewcraft.model.Material;
import io.company.brewcraft.service.MaterialService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.MaterialMapper;
import io.company.brewcraft.util.controller.AttributeFilter;
import io.company.brewcraft.util.validator.Validator;

@RestController
@RequestMapping(path = "/api/v1/materials", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class MaterialController extends BaseController {
    
    private MaterialService materialService;
    
    private MaterialMapper materialMapper = MaterialMapper.INSTANCE;
        
    public MaterialController(MaterialService materialService, AttributeFilter filter) {
        super(filter);
        this.materialService = materialService;
    }
    
    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public PageDto<MaterialDto> getMaterials(
        @RequestParam(required = false) Set<Long> ids,
        @RequestParam(required = false) Set<Long> categoryIds,
        @RequestParam(required = false) Set<String> categoryNames,
        @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) Set<String> sort,
        @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
        @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
        @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size
    ) {
        
        Page<Material> materialsPage = materialService.getMaterials(ids, categoryIds, categoryNames, page, size, sort, orderAscending);
        
        List<MaterialDto> materialsList = materialsPage.stream()
                .map(material -> materialMapper.toDto(material)).collect(Collectors.toList());

        PageDto<MaterialDto> dto = new PageDto<MaterialDto>(materialsList, materialsPage.getTotalPages(), materialsPage.getTotalElements());
        
        return dto;
    }
        
    @GetMapping(value = "/{materialId}", consumes = MediaType.ALL_VALUE)
    public MaterialDto getMaterial(@PathVariable Long materialId) {
        Validator validator = new Validator();

        Material material = materialService.getMaterial(materialId);
        
        validator.assertion(material != null, EntityNotFoundException.class, "Material", materialId.toString());

        return materialMapper.toDto(material);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public MaterialDto addMaterial(@Valid @RequestBody AddMaterialDto addMaterialDto) {
        Material material = materialMapper.fromDto(addMaterialDto);
        
        Material addedMaterial = materialService.addMaterial(material, addMaterialDto.getCategoryId(), addMaterialDto.getBaseQuantityUnit());
        
        return materialMapper.toDto(addedMaterial);
    }
    
    @PutMapping("/{materialId}")
    public MaterialDto putMaterial(@Valid @RequestBody UpdateMaterialDto updateMaterialDto, @PathVariable Long materialId) {
        Material material = materialMapper.fromDto(updateMaterialDto);
        
        Material putMaterial = materialService.putMaterial(materialId, material, updateMaterialDto.getCategoryId(), updateMaterialDto.getBaseQuantityUnit());

        return materialMapper.toDto(putMaterial);
    }
    
    @PatchMapping("/{materialId}")
    public MaterialDto patchMaterial(@Valid @RequestBody UpdateMaterialDto updateMaterialDto, @PathVariable Long materialId) {        
        Material material = materialMapper.fromDto(updateMaterialDto);
        
        Material patchedMaterial = materialService.patchMaterial(materialId, material, updateMaterialDto.getCategoryId(), updateMaterialDto.getBaseQuantityUnit());
        
        return materialMapper.toDto(patchedMaterial);
    }

    @DeleteMapping(value = "/{materialId}", consumes = MediaType.ALL_VALUE)
    public void deleteMaterial(@PathVariable Long materialId) {
        materialService.deleteMaterial(materialId);
    }
}
