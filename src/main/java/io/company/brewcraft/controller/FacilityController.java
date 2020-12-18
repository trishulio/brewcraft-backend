package io.company.brewcraft.controller;

import java.util.List;
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

import io.company.brewcraft.dto.AddFacilityDto;
import io.company.brewcraft.dto.FacilityDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.UpdateFacilityDto;
import io.company.brewcraft.model.Facility;
import io.company.brewcraft.service.FacilityService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.FacilityMapper;

@RestController
@RequestMapping(path = "/api/facilities")
public class FacilityController {
    
    private FacilityService facilityService;
    
    FacilityMapper facilityMapper = FacilityMapper.INSTANCE;

    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }
    
    @GetMapping("/")
    public PageDto<FacilityDto> getAllFacilities(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size, 
            @RequestParam(defaultValue = "id") String[] sort, @RequestParam(defaultValue = "true") boolean order_asc) {        
        Page<Facility> facilitiesPage = facilityService.getAllFacilities(page, size, sort, order_asc);
        
        List<FacilityDto> facilitiesList = facilitiesPage.stream().map(facility -> facilityMapper.facilityToFacilityDto(facility)).collect(Collectors.toList());
    
        PageDto<FacilityDto> dto = new PageDto<FacilityDto>(facilitiesList, facilitiesPage.getTotalPages(), facilitiesPage.getTotalElements());
        return dto;
    }
    
    @GetMapping("/{facilityId}")
    public FacilityDto getFacility(@PathVariable Long facilityId) {    
        Facility facility =  facilityService.getFacility(facilityId);
        
        if (facility != null) {
            return facilityMapper.facilityToFacilityDto(facility);
        } else {
            throw new EntityNotFoundException("Facility", facilityId.toString());
        }        
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public void addFacility(@Valid @RequestBody AddFacilityDto facilityDto) {
        Facility facility = facilityMapper.facilityDtoToFacility(facilityDto);    
        
        facilityService.addFacility(facility);
    }
    
    @PutMapping("/{facilityId}")
    public void putFacility(@PathVariable Long facilityId, @Valid @RequestBody AddFacilityDto facilityDto) {
        Facility facility = facilityMapper.facilityDtoToFacility(facilityDto);

        facilityService.putFacility(facilityId, facility);
    }
    
    @PatchMapping("/{facilityId}")
    public void patchFacility(@PathVariable Long facilityId, @Valid @RequestBody UpdateFacilityDto facilityDto) {
        Facility facility = facilityMapper.facilityDtoToFacility(facilityDto);

        facilityService.patchFacility(facilityId, facility);
    }

    @DeleteMapping("/{facilityId}")
    public void deleteFacility(@PathVariable Long facilityId) {
        facilityService.deleteFacility(facilityId);
    }
}
