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

import io.company.brewcraft.dto.AddFacilityDto;
import io.company.brewcraft.dto.FacilityDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.UpdateFacilityDto;
import io.company.brewcraft.model.Facility;
import io.company.brewcraft.service.FacilityService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.FacilityMapper;
import io.company.brewcraft.util.validator.Validator;

@RestController
@RequestMapping(path = "/api/v1/facilities", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class FacilityController {
    
    private FacilityService facilityService;
    
    private FacilityMapper facilityMapper = FacilityMapper.INSTANCE;

    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }
    
    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public PageDto<FacilityDto> getAllFacilities(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size, 
            @RequestParam(defaultValue = "id") Set<String> sort, @RequestParam(defaultValue = "true") boolean orderAscending) {        
        Page<Facility> facilitiesPage = facilityService.getAllFacilities(page, size, sort, orderAscending);
        
        List<FacilityDto> facilitiesList = facilitiesPage.stream().map(facility -> facilityMapper.facilityToFacilityDto(facility)).collect(Collectors.toList());
    
        PageDto<FacilityDto> dto = new PageDto<FacilityDto>(facilitiesList, facilitiesPage.getTotalPages(), facilitiesPage.getTotalElements());
        return dto;
    }
    
    @GetMapping(value = "/{facilityId}", consumes = MediaType.ALL_VALUE)
    public FacilityDto getFacility(@PathVariable Long facilityId) {   
        Validator validator = new Validator();

        Facility facility =  facilityService.getFacility(facilityId);
        
        validator.assertion(facility != null, EntityNotFoundException.class, "Facility", facilityId.toString());
        
        return facilityMapper.facilityToFacilityDto(facility);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public FacilityDto addFacility(@Valid @RequestBody AddFacilityDto facilityDto) {
        Facility facility = facilityMapper.facilityDtoToFacility(facilityDto);    
        
        Facility addedFacility = facilityService.addFacility(facility);
        
        return facilityMapper.facilityToFacilityDto(addedFacility);   
    }
    
    @PutMapping("/{facilityId}")
    public FacilityDto putFacility(@PathVariable Long facilityId, @Valid @RequestBody UpdateFacilityDto facilityDto) {
        Facility facility = facilityMapper.facilityDtoToFacility(facilityDto);

        Facility putFacility = facilityService.putFacility(facilityId, facility);
        
        return facilityMapper.facilityToFacilityDto(putFacility);   
    }
    
    @PatchMapping("/{facilityId}")
    public FacilityDto patchFacility(@PathVariable Long facilityId, @Valid @RequestBody UpdateFacilityDto facilityDto) {
        Facility facility = facilityMapper.facilityDtoToFacility(facilityDto);

        Facility patchedFacility = facilityService.patchFacility(facilityId, facility);
        
        return facilityMapper.facilityToFacilityDto(patchedFacility);   
    }

    @DeleteMapping(value = "/{facilityId}", consumes = MediaType.ALL_VALUE)
    public void deleteFacility(@PathVariable Long facilityId) {
        facilityService.deleteFacility(facilityId);
    }
}
