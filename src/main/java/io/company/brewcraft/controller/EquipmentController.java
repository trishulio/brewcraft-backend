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

import io.company.brewcraft.dto.AddEquipmentDto;
import io.company.brewcraft.dto.EquipmentDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.UpdateEquipmentDto;
import io.company.brewcraft.model.Equipment;
import io.company.brewcraft.service.EquipmentService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.EquipmentMapper;
import io.company.brewcraft.util.controller.AttributeFilter;
import io.company.brewcraft.util.validator.Validator;

@RestController
@RequestMapping(path = "/api/v1/facilities", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class EquipmentController extends BaseController {
    
    private EquipmentService equipmentService;
    
    private EquipmentMapper equipmentMapper = EquipmentMapper.INSTANCE;
        
    public EquipmentController(EquipmentService equipmentService, AttributeFilter filter) {
        super(filter);
        this.equipmentService = equipmentService;
    }
    
    @GetMapping(value = "/equipment", consumes = MediaType.ALL_VALUE)
    public PageDto<EquipmentDto> getAllEquipment(
        @RequestParam(required = false) Set<Long> ids,
        @RequestParam(required = false) Set<String> types,
        @RequestParam(required = false) Set<String> statuses,
        @RequestParam(required = false) Set<Long> facilityIds,
        @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) Set<String> sort,
        @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
        @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
        @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size
    ) {
        
        Page<Equipment> equipmentPage = equipmentService.getAllEquipment(ids, types, statuses, facilityIds, page, size, sort, orderAscending);

        List<EquipmentDto> equipmentList = equipmentPage.stream()
                .map(equipment -> equipmentMapper.toDto(equipment)).collect(Collectors.toList());

        PageDto<EquipmentDto> dto = new PageDto<EquipmentDto>(equipmentList, equipmentPage.getTotalPages(), equipmentPage.getTotalElements());
        
        return dto;
    }
        
    @GetMapping(value = "/equipment/{equipmentId}", consumes = MediaType.ALL_VALUE)
    public EquipmentDto getEquipment(@PathVariable Long equipmentId) {
        Validator validator = new Validator();

        Equipment equipment = equipmentService.getEquipment(equipmentId);
        
        validator.assertion(equipment != null, EntityNotFoundException.class, "Equipment", equipmentId.toString());
        
        return equipmentMapper.toDto(equipment);
    }

    @PostMapping("/{facilityId}/equipment")
    @ResponseStatus(HttpStatus.CREATED)
    public EquipmentDto addEquipment(@PathVariable Long facilityId, @Valid @RequestBody AddEquipmentDto equipmentDto) {        
        Equipment equipment = equipmentMapper.fromDto(equipmentDto);
        
        Equipment addedEquipment = equipmentService.addEquipment(facilityId, equipment);
        
        return equipmentMapper.toDto(addedEquipment);
    }
    
    @PutMapping("/{facilityId}/equipment/{equipmentId}")
    public EquipmentDto putEquipment(@Valid @RequestBody UpdateEquipmentDto equipmentDto, @PathVariable Long facilityId,  @PathVariable Long equipmentId) {
        Equipment equipment = equipmentMapper.fromDto(equipmentDto);
        
        Equipment putEquipment = equipmentService.putEquipment(facilityId, equipmentId, equipment);

        return equipmentMapper.toDto(putEquipment);
    }
    
    @PatchMapping("/equipment/{equipmentId}")
    public EquipmentDto patchEquipment(@Valid @RequestBody UpdateEquipmentDto equipmentDto, @PathVariable Long equipmentId) {
        Equipment equipment = equipmentMapper.fromDto(equipmentDto);
        
        Equipment patchedEquipment = equipmentService.patchEquipment(equipmentId, equipment);
        
        return equipmentMapper.toDto(patchedEquipment);
    }

    @DeleteMapping(value = "/equipment/{equipmentId}", consumes = MediaType.ALL_VALUE)
    public void deleteEquipment(@PathVariable Long equipmentId) {
        equipmentService.deleteEquipment(equipmentId);
    }

}
