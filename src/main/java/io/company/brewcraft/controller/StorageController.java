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

import io.company.brewcraft.dto.AddStorageDto;
import io.company.brewcraft.dto.PageDto;
import io.company.brewcraft.dto.FacilityStorageDto;
import io.company.brewcraft.dto.UpdateStorageDto;
import io.company.brewcraft.model.Storage;
import io.company.brewcraft.service.StorageService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.StorageMapper;

@RestController
@RequestMapping(path = "/api/facilities")
public class StorageController {
   
    private StorageService storageService;
        
    private StorageMapper storageMapper = StorageMapper.INSTANCE;
    
    public StorageController(StorageService storageService) {
        this.storageService = storageService;
    }
    
    @GetMapping("/storages")
    public PageDto<FacilityStorageDto> getAllStorages(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size, 
            @RequestParam(defaultValue = "id") String[] sort, @RequestParam(defaultValue = "true") boolean order_asc) {        
        Page<Storage> storagePage = storageService.getAllStorages(page, size, sort, order_asc);
        
        List<FacilityStorageDto> storageList = storagePage.stream().map(storage -> storageMapper.storageToStorageDto(storage)).collect(Collectors.toList());
    
        PageDto<FacilityStorageDto> dto = new PageDto<FacilityStorageDto>(storageList, storagePage.getTotalPages(), storagePage.getTotalElements());
        return dto;
    }
        
    @GetMapping("/storages/{storageId}")
    public FacilityStorageDto getStorage(@PathVariable Long storageId) {
        Storage storage = storageService.getStorage(storageId);
        
        if (storage != null) {
            return storageMapper.storageToStorageDto(storage);
        } else {
            throw new EntityNotFoundException("Storage", storageId.toString());
        }
    }

    @PostMapping("/{facilityId}/storages")
    @ResponseStatus(HttpStatus.CREATED)
    public void addStorage(@PathVariable Long facilityId, @Valid @RequestBody AddStorageDto storageDto) {
        Storage storage = storageMapper.storageDtoToStorage(storageDto);          
        
        storageService.addStorage(facilityId, storage);
    }
    
    @PutMapping("/storages/{storageId}")
    public void putStorage(@Valid @RequestBody AddStorageDto storageDto, @PathVariable Long storageId) {      
        Storage storage = storageMapper.storageDtoToStorage(storageDto);
        
        storageService.putStorage(storageId, storage);
    }
    
    @PatchMapping("/storages/{storageId}")
    public void patchStorage(@Valid @RequestBody UpdateStorageDto storageDto, @PathVariable Long storageId) {      
        Storage storage = storageMapper.storageDtoToStorage(storageDto);
        
        storageService.patchStorage(storageId, storage);
    }

    @DeleteMapping("/storages/{storageId}")
    public void deleteStorage(@PathVariable Long storageId) {
        storageService.deleteStorage(storageId);
    }

}
