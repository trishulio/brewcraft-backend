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

import io.company.brewcraft.dto.AddSupplierDto;
import io.company.brewcraft.dto.GetSuppliersDto;
import io.company.brewcraft.dto.SupplierDto;
import io.company.brewcraft.dto.UpdateSupplier;
import io.company.brewcraft.dto.UpdateSupplierDto;
import io.company.brewcraft.pojo.Supplier;
import io.company.brewcraft.service.SupplierService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.SupplierMapper;

@RestController
@RequestMapping(path = "/api/v1")
public class SupplierController {
    
    private SupplierService supplierService;
    
    private SupplierMapper supplierMapper = SupplierMapper.INSTANCE;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/suppliers")
    public GetSuppliersDto getSuppliers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size, @RequestParam(defaultValue = "id") String[] sort, @RequestParam(defaultValue = "true") boolean order_asc) {        
        Page<Supplier> suppliers = supplierService.getSuppliers(page, size, sort, order_asc);
      
        List<SupplierDto> supplierDtos = suppliers.stream().map(supplier -> supplierMapper.toDto(supplier)).collect(Collectors.toList());
    
        return new GetSuppliersDto(supplierDtos, suppliers.getTotalElements(), suppliers.getTotalPages());   
    }
    
    @GetMapping("/suppliers/{supplierId}")
    public SupplierDto getSupplier(@PathVariable Long supplierId) {
        Supplier supplier = supplierService.getSupplier(supplierId);
        
        if (supplier == null) {
            throw new EntityNotFoundException("Supplier", supplierId.toString());
        } else {
            return supplierMapper.toDto(supplier);   
        }
    }

    @PostMapping("/suppliers")
    @ResponseStatus(HttpStatus.CREATED)
    public SupplierDto addSupplier(@Valid @RequestBody AddSupplierDto supplierDto) {
        Supplier supplier = supplierMapper.fromDto(supplierDto);    
        
        Supplier addedSupplier = supplierService.addSupplier(supplier);
       
        return supplierMapper.toDto(addedSupplier);   
    }
    
    @PutMapping("/suppliers/{supplierId}")
    public SupplierDto putSupplier(@Valid @RequestBody UpdateSupplierDto supplierDto, @PathVariable Long supplierId) {
        UpdateSupplier supplier = supplierMapper.fromDto(supplierDto);
        
        Supplier putSupplier = supplierService.putSupplier(supplierId, supplier);
        
        return supplierMapper.toDto(putSupplier);   
    }

    @PatchMapping("/suppliers/{supplierId}")
    public SupplierDto patchSupplier(@Valid @RequestBody UpdateSupplierDto supplierDto, @PathVariable Long supplierId) {
        UpdateSupplier supplier = supplierMapper.fromDto(supplierDto);
        
        Supplier patchedSupplier = supplierService.patchSupplier(supplierId, supplier);
        
        return supplierMapper.toDto(patchedSupplier);   
    }
    
    @DeleteMapping("/suppliers/{supplierId}")
    public void deleteSupplier(@PathVariable Long supplierId) {
        supplierService.deleteSupplier(supplierId);
    }

}
