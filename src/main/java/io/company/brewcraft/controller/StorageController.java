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

import io.company.brewcraft.dto.AddStorageDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.StorageDto;
import io.company.brewcraft.dto.UpdateStorageDto;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.service.StorageService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.StorageMapper;
import io.company.brewcraft.util.validator.Validator;

@RestController
@RequestMapping(path = "/api/v1/facilities", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class StorageController {
   
    private StorageService storageService;
        
    private StorageMapper storageMapper = StorageMapper.INSTANCE;
    
    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }
    
    @GetMapping(value = "/storages" , consumes = MediaType.ALL_VALUE)
    public PageDto<StorageDto> getAllStorages(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size, 
            @RequestParam(defaultValue = "id") Set<String> sort, @RequestParam(defaultValue = "true") boolean orderAscending) {        
        Page<Storage> storagePage = storageService.getAllStorages(page, size, sort, orderAscending);
        
        List<StorageDto> storageList = storagePage.stream().map(storage -> storageMapper.storageToStorageDto(storage)).collect(Collectors.toList());
    
        PageDto<StorageDto> dto = new PageDto<StorageDto>(storageList, storagePage.getTotalPages(), storagePage.getTotalElements());
        
        return dto;
    }
        
    @GetMapping(value = "/storages/{storageId}", consumes = MediaType.ALL_VALUE)
    public StorageDto getStorage(@PathVariable Long storageId) {
        Validator validator = new Validator();

        Storage storage = storageService.getStorage(storageId);
        
        validator.assertion(storage != null, EntityNotFoundException.class, "Storage", storageId.toString());
        
        StorageDto temp = storageMapper.storageToStorageDto(storage);
        
        return temp;
    }

    @PostMapping("/{facilityId}/storages")
    @ResponseStatus(HttpStatus.CREATED)
    public StorageDto addStorage(@PathVariable Long facilityId, @Valid @RequestBody AddStorageDto storageDto) {
        Storage storage = storageMapper.storageDtoToStorage(storageDto);          
        
        Storage addedStorage = storageService.addStorage(facilityId, storage);
        
        return storageMapper.storageToStorageDto(addedStorage);
    }
    
    @PutMapping("/storages/{storageId}")
    public StorageDto putStorage(@Valid @RequestBody UpdateStorageDto storageDto, @PathVariable Long storageId) {      
        Storage storage = storageMapper.storageDtoToStorage(storageDto);
        
        Storage putStorage = storageService.putStorage(storageId, storage);
        
        return storageMapper.storageToStorageDto(putStorage);
    }
    
    @PatchMapping("/storages/{storageId}")
    public StorageDto patchStorage(@Valid @RequestBody UpdateStorageDto storageDto, @PathVariable Long storageId) {      
        Storage storage = storageMapper.storageDtoToStorage(storageDto);
        
        Storage patchedStorage = storageService.patchStorage(storageId, storage);
        
        return storageMapper.storageToStorageDto(patchedStorage);
    }

    @DeleteMapping(value = "/storages/{storageId}", consumes = MediaType.ALL_VALUE)
    public void deleteStorage(@PathVariable Long storageId) {
        storageService.deleteStorage(storageId);
    }

}
