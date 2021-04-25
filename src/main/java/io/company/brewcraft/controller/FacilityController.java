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
import io.company.brewcraft.util.controller.AttributeFilter;
import io.company.brewcraft.util.validator.Validator;

@RestController
@RequestMapping(path = "/api/v1/facilities", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class FacilityController extends BaseController {
    
    private FacilityService facilityService;
    
    private FacilityMapper facilityMapper = FacilityMapper.INSTANCE;

    public FacilityController(FacilityService facilityService, AttributeFilter filter) {
        super(filter);
        this.facilityService = facilityService;
    }
    
    @GetMapping(value = "", consumes = MediaType.ALL_VALUE)
    public PageDto<FacilityDto> getAllFacilities(
        @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) Set<String> sort,
        @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
        @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
        @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size
    ) {        
        Page<Facility> facilitiesPage = facilityService.getAllFacilities(page, size, sort, orderAscending);
        
        List<FacilityDto> facilitiesList = facilitiesPage.stream().map(facility -> facilityMapper.toDto(facility)).collect(Collectors.toList());
    
        PageDto<FacilityDto> dto = new PageDto<FacilityDto>(facilitiesList, facilitiesPage.getTotalPages(), facilitiesPage.getTotalElements());
        return dto;
    }
    
    @GetMapping(value = "/{facilityId}", consumes = MediaType.ALL_VALUE)
    public FacilityDto getFacility(@PathVariable Long facilityId) {   
        Validator validator = new Validator();

        Facility facility =  facilityService.getFacility(facilityId);
        
        validator.assertion(facility != null, EntityNotFoundException.class, "Facility", facilityId.toString());
        
        return facilityMapper.toDto(facility);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public FacilityDto addFacility(@Valid @RequestBody AddFacilityDto facilityDto) {
        Facility facility = facilityMapper.fromDto(facilityDto);    
        
        Facility addedFacility = facilityService.addFacility(facility);
        
        return facilityMapper.toDto(addedFacility);   
    }
    
    @PutMapping("/{facilityId}")
    public FacilityDto putFacility(@PathVariable Long facilityId, @Valid @RequestBody UpdateFacilityDto facilityDto) {
        Facility facility = facilityMapper.fromDto(facilityDto);

        Facility putFacility = facilityService.putFacility(facilityId, facility);
        
        return facilityMapper.toDto(putFacility);   
    }
    
    @PatchMapping("/{facilityId}")
    public FacilityDto patchFacility(@PathVariable Long facilityId, @Valid @RequestBody UpdateFacilityDto facilityDto) {
        Facility facility = facilityMapper.fromDto(facilityDto);

        Facility patchedFacility = facilityService.patchFacility(facilityId, facility);
        
        return facilityMapper.toDto(patchedFacility);   
    }

    @DeleteMapping(value = "/{facilityId}", consumes = MediaType.ALL_VALUE)
    public void deleteFacility(@PathVariable Long facilityId) {
        facilityService.deleteFacility(facilityId);
    }
}
