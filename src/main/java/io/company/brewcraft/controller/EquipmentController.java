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
import io.company.brewcraft.model.EquipmentEntity;
import io.company.brewcraft.service.EquipmentService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.EquipmentMapper;
import io.company.brewcraft.util.validator.Validator;

@RestController
@RequestMapping(path = "/api/v1/facilities", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class EquipmentController {
    
    private EquipmentService equipmentService;
    
    private EquipmentMapper equipmentMapper = EquipmentMapper.INSTANCE;
        
    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }
    
    @GetMapping(value = "/equipment", consumes = MediaType.ALL_VALUE)
    public PageDto<EquipmentDto> getAllEquipment(
            @RequestParam(required = false) Set<Long> ids,
            @RequestParam(required = false) Set<String> types,
            @RequestParam(required = false) Set<String> statuses,
            @RequestParam(required = false) Set<Long> facilityIds,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "id") Set<String> sort, @RequestParam(defaultValue = "true") boolean orderAscending) {
        
        Page<EquipmentEntity> equipmentPage = equipmentService.getAllEquipment(ids, types, statuses, facilityIds, page, size, sort, orderAscending);

        List<EquipmentDto> equipmentList = equipmentPage.stream()
                .map(equipment -> equipmentMapper.equipmentToEquipmentDto(equipment)).collect(Collectors.toList());

        PageDto<EquipmentDto> dto = new PageDto<EquipmentDto>(equipmentList, equipmentPage.getTotalPages(), equipmentPage.getTotalElements());
        
        return dto;
    }
        
    @GetMapping(value = "/equipment/{equipmentId}", consumes = MediaType.ALL_VALUE)
    public EquipmentDto getEquipment(@PathVariable Long equipmentId) {
        Validator validator = new Validator();

        EquipmentEntity equipment = equipmentService.getEquipment(equipmentId);
        
        validator.assertion(equipment != null, EntityNotFoundException.class, "Equipment", equipmentId.toString());

        return equipmentMapper.equipmentToEquipmentDto(equipment);
    }

    @PostMapping("/{facilityId}/equipment")
    @ResponseStatus(HttpStatus.CREATED)
    public EquipmentDto addEquipment(@PathVariable Long facilityId, @Valid @RequestBody AddEquipmentDto equipmentDto) {
        EquipmentEntity equipment = equipmentMapper.equipmentDtoToEquipment(equipmentDto);
        
        EquipmentEntity addedEquipment = equipmentService.addEquipment(facilityId, equipment);
        
        return equipmentMapper.equipmentToEquipmentDto(addedEquipment);
    }
    
    @PutMapping("/{facilityId}/equipment/{equipmentId}")
    public EquipmentDto putEquipment(@Valid @RequestBody UpdateEquipmentDto equipmentDto, @PathVariable Long facilityId,  @PathVariable Long equipmentId) {
        EquipmentEntity equipment = equipmentMapper.equipmentDtoToEquipment(equipmentDto);
        
        EquipmentEntity putEquipment = equipmentService.putEquipment(facilityId, equipmentId, equipment);

        return equipmentMapper.equipmentToEquipmentDto(putEquipment);
    }
    
    @PatchMapping("/equipment/{equipmentId}")
    public EquipmentDto patchEquipment(@Valid @RequestBody UpdateEquipmentDto equipmentDto, @PathVariable Long equipmentId) {
        EquipmentEntity equipment = equipmentMapper.equipmentDtoToEquipment(equipmentDto);
        
        EquipmentEntity patchedEquipment = equipmentService.patchEquipment(equipmentId, equipment);
        
        return equipmentMapper.equipmentToEquipmentDto(patchedEquipment);
    }

    @DeleteMapping(value = "/equipment/{equipmentId}", consumes = MediaType.ALL_VALUE)
    public void deleteEquipment(@PathVariable Long equipmentId) {
        equipmentService.deleteEquipment(equipmentId);
    }

}
