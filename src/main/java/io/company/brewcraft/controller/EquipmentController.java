package io.company.brewcraft.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping(path = "/api/facilities")
public class EquipmentController {
    
    private EquipmentService equipmentService;
    
    private EquipmentMapper equipmentMapper = EquipmentMapper.INSTANCE;
        
    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }
    
    @GetMapping("/equipment")
    public PageDto<EquipmentDto> getAllEquipment(
            @RequestParam(required = false, name = "ids") Set<Long> ids,
            @RequestParam(required = false, name = "type") Set<String> types,
            @RequestParam(required = false, name = "status") Set<String> statuses,
            @RequestParam(required = false, name = "facility_id") Set<Long> facilityIds,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "id") String[] sort, @RequestParam(defaultValue = "true") boolean order_asc) {
        
        Page<Equipment> equipmentPage = equipmentService.getAllEquipment(ids, types, statuses, facilityIds, page, size, sort, order_asc);

        List<EquipmentDto> equipmentList = equipmentPage.stream()
                .map(equipment -> equipmentMapper.equipmentToEquipmentDto(equipment)).collect(Collectors.toList());

        PageDto<EquipmentDto> dto = new PageDto<EquipmentDto>(equipmentList, equipmentPage.getTotalPages(), equipmentPage.getTotalElements());
        return dto;
    }
        
    @GetMapping("/equipment/{equipmentId}")
    public EquipmentDto getEquipment(@PathVariable Long equipmentId) {
        Equipment equipment = equipmentService.getEquipment(equipmentId);
        
        if (equipment != null) {
            return equipmentMapper.equipmentToEquipmentDto(equipment);
        } else {
            throw new EntityNotFoundException("Equipment", equipmentId.toString());
        }
    }

    @PostMapping("/{facilityId}/equipment")
    @ResponseStatus(HttpStatus.CREATED)
    public void addEquipment(@PathVariable Long facilityId, @Valid @RequestBody AddEquipmentDto equipmentDto) {
        Equipment equipment = equipmentMapper.equipmentDtoToEquipment(equipmentDto);
        
        equipmentService.addEquipment(facilityId, equipment);
    }
    
    @PutMapping("/equipment/{equipmentId}")
    public void putEquipment(@Valid @RequestBody AddEquipmentDto equipmentDto,  @PathVariable Long equipmentId) {
        Equipment equipment = equipmentMapper.equipmentDtoToEquipment(equipmentDto);
        
        equipmentService.putEquipment(equipmentId, equipment);
    }
    
    @PatchMapping("/equipment/{equipmentId}")
    public void patchEquipment(@Valid @RequestBody UpdateEquipmentDto equipmentDto,  @PathVariable Long equipmentId) {
        Equipment equipment = equipmentMapper.equipmentDtoToEquipment(equipmentDto);
        
        equipmentService.patchEquipment(equipmentId, equipment);
    }

    @DeleteMapping("/equipment/{equipmentId}")
    public void deleteEquipment(@PathVariable Long equipmentId) {
        equipmentService.deleteEquipment(equipmentId);
    }

}
