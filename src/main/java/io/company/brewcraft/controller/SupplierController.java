package io.company.brewcraft.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.company.brewcraft.dto.AddSupplierDto;
import io.company.brewcraft.dto.GetSuppliersDto;
import io.company.brewcraft.dto.SupplierDto;
import io.company.brewcraft.dto.UpdateSupplierDto;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.service.SupplierService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.SupplierMapper;
import io.company.brewcraft.util.controller.AttributeFilter;

@RestController
@RequestMapping(path = "/api/v1")
public class SupplierController extends BaseController {
    
    private SupplierService supplierService;
    
    private SupplierMapper supplierMapper = SupplierMapper.INSTANCE;

    public SupplierController(SupplierService supplierService, AttributeFilter filter) {
        super(filter);
        this.supplierService = supplierService;
    }

    @GetMapping("/suppliers")
    public GetSuppliersDto getSuppliers(
        @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) Set<String> sort,
        @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
        @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
        @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size
    ) {        
        Page<Supplier> suppliers = supplierService.getSuppliers(page, size, sort, orderAscending);
      
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
        Supplier supplier = supplierMapper.fromDto(supplierDto);
        
        Supplier putSupplier = supplierService.putSupplier(supplierId, supplier);
        
        return supplierMapper.toDto(putSupplier);   
    }

    @PatchMapping("/suppliers/{supplierId}")
    public SupplierDto patchSupplier(@Valid @RequestBody UpdateSupplierDto supplierDto, @PathVariable Long supplierId) {
        Supplier supplier = supplierMapper.fromDto(supplierDto);
        
        Supplier patchedSupplier = supplierService.patchSupplier(supplierId, supplier);
        
        return supplierMapper.toDto(patchedSupplier);   
    }
    
    @DeleteMapping("/suppliers/{supplierId}")
    public void deleteSupplier(@PathVariable Long supplierId) {
        supplierService.deleteSupplier(supplierId);
    }

}
