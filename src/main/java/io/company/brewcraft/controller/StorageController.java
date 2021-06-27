package io.company.brewcraft.controller;

import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import io.company.brewcraft.dto.AddStorageDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.StorageDto;
import io.company.brewcraft.dto.UpdateStorageDto;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.service.StorageService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.StorageMapper;
import io.company.brewcraft.util.controller.AttributeFilter;
import io.company.brewcraft.util.validator.Validator;

@RestController
@RequestMapping(path = "/api/v1/facilities", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class StorageController extends BaseController {
   
    private StorageService storageService;
        
    private StorageMapper storageMapper = StorageMapper.INSTANCE;
    
    public StorageController(StorageService storageService, AttributeFilter filter) {
        super(filter);
        this.storageService = storageService;
    }
    
    @GetMapping(value = "/storages" , consumes = MediaType.ALL_VALUE)
    public PageDto<StorageDto> getAllStorages(
        @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
        @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
        @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
        @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size
    ) {        
        Page<Storage> storagePage = storageService.getAllStorages(page, size, sort, orderAscending);
        
        List<StorageDto> storageList = storagePage.stream().map(storage -> storageMapper.toDto(storage)).collect(Collectors.toList());
    
        PageDto<StorageDto> dto = new PageDto<StorageDto>(storageList, storagePage.getTotalPages(), storagePage.getTotalElements());
        
        return dto;
    }
        
    @GetMapping(value = "/storages/{storageId}", consumes = MediaType.ALL_VALUE)
    public StorageDto getStorage(@PathVariable Long storageId) {
        Storage storage = storageService.getStorage(storageId);
        
        Validator.assertion(storage != null, EntityNotFoundException.class, "Storage", storageId.toString());
        
        StorageDto storageDto = storageMapper.toDto(storage);
        
        return storageDto;
    }

    @PostMapping("/{facilityId}/storages")
    @ResponseStatus(HttpStatus.CREATED)
    public StorageDto addStorage(@PathVariable Long facilityId, @Valid @RequestBody AddStorageDto storageDto) {
        Storage storage = storageMapper.fromDto(storageDto);          
        
        Storage addedStorage = storageService.addStorage(facilityId, storage);
        
        return storageMapper.toDto(addedStorage);
    }
    
    @PutMapping("/{facilityId}/storages/{storageId}")
    public StorageDto putStorage(@Valid @RequestBody UpdateStorageDto storageDto, @PathVariable Long facilityId, @PathVariable Long storageId) {      
        Storage storage = storageMapper.fromDto(storageDto);
        
        Storage putStorage = storageService.putStorage(facilityId, storageId, storage);
        
        return storageMapper.toDto(putStorage);
    }
    
    @PatchMapping("/storages/{storageId}")
    public StorageDto patchStorage(@Valid @RequestBody UpdateStorageDto storageDto, @PathVariable Long storageId) {      
        Storage storage = storageMapper.fromDto(storageDto);
        
        Storage patchedStorage = storageService.patchStorage(storageId, storage);
        
        return storageMapper.toDto(patchedStorage);
    }

    @DeleteMapping(value = "/storages/{storageId}", consumes = MediaType.ALL_VALUE)
    public void deleteStorage(@PathVariable Long storageId) {
        storageService.deleteStorage(storageId);
    }

}
