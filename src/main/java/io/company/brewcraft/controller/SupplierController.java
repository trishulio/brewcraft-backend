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

import io.company.brewcraft.dto.GetSuppliersDto;
import io.company.brewcraft.dto.SupplierContactDto;
import io.company.brewcraft.dto.SupplierDto;
import io.company.brewcraft.dto.UpdateSupplierContactDto;
import io.company.brewcraft.dto.UpdateSupplierDto;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.model.SupplierContact;
import io.company.brewcraft.service.SupplierService;
import io.company.brewcraft.service.mapper.SupplierMapper;

@RestController
@RequestMapping(path = "/api")
public class SupplierController {
    
    private SupplierService supplierService;
    
    private SupplierMapper supplierMapper = SupplierMapper.INSTANCE;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping("/suppliers")
    public GetSuppliersDto getSuppliers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size, @RequestParam(defaultValue = "id") String[] sort, @RequestParam(defaultValue = "true") boolean order_asc) {        
        Page<Supplier> suppliers = supplierService.getSuppliers(page, size, sort, order_asc);
      
        List<SupplierDto> supplierDtos = suppliers.stream().map(supplier -> supplierMapper.supplierToSupplierDto(supplier)).collect(Collectors.toList());
    
        return new GetSuppliersDto(supplierDtos, suppliers.getTotalElements(), suppliers.getTotalPages());   
    }
    
    @GetMapping("/suppliers/{supplierId}")
    public SupplierDto getSupplier(@PathVariable Long supplierId) {
        Supplier supplier = supplierService.getSupplier(supplierId);

        return supplierMapper.supplierToSupplierDto(supplier);   
    }

    @PostMapping("/suppliers")
    @ResponseStatus(HttpStatus.CREATED)
    public void addSupplier(@Valid @RequestBody SupplierDto newSupplier) {
        Supplier supplier = supplierMapper.supplierDtoToSupplier(newSupplier);    
        supplierService.addSupplier(supplier);
    }
    
    @PutMapping("/suppliers/{supplierId}")
    public void updateSupplier(@Valid @RequestBody UpdateSupplierDto supplierDto, @PathVariable Long supplierId) {
        Supplier updatedSupplier = supplierMapper.updateSupplierDtoToSupplier(supplierDto);
        supplierService.updateSupplier(supplierId, updatedSupplier);
    }

    @PatchMapping("/suppliers/{supplierId}")
    public void patchSupplier(@Valid @RequestBody UpdateSupplierDto supplierDto, @PathVariable Long supplierId) {
        Supplier updatedSupplier = supplierMapper.updateSupplierDtoToSupplier(supplierDto);
        
        supplierService.updateSupplier(supplierId, updatedSupplier);
    }
    
    @DeleteMapping("/suppliers/{supplierId}")
    public void deleteSupplier(@PathVariable Long supplierId) {
        supplierService.deleteSupplier(supplierId);
    }
     
    @GetMapping("/suppliers/{supplierId}/contacts")
    public List<SupplierContactDto> getContacts(@PathVariable Long supplierId) {
        List<SupplierContact> supplierContacts = supplierService.getContacts(supplierId);
       
        return supplierContacts.stream().map(supplierContact -> supplierMapper.contactToContactDto(supplierContact)).collect(Collectors.toList());
    }
    
    @GetMapping("/suppliers/{supplierId}/contacts/{contactId}")
    public SupplierContactDto getContact(@PathVariable Long supplierId, @PathVariable Long contactId) {
        SupplierContact supplierContact = supplierService.getContact(supplierId, contactId);
        
        return supplierMapper.contactToContactDto(supplierContact);
    }

    @PostMapping("/suppliers/{supplierId}/contacts")
    @ResponseStatus(HttpStatus.CREATED)
    public void addContact(@PathVariable Long supplierId, @Valid @RequestBody SupplierContactDto newSupplierContact) {
        SupplierContact supplierContact = supplierMapper.contactDtoToContact(newSupplierContact);    
        
        supplierService.addContact(supplierId, supplierContact);
    }
    
    @PutMapping("/suppliers/{supplierId}/contacts/{contactId}")
    public void updateContact(@PathVariable Long supplierId, @PathVariable Long contactId, @Valid @RequestBody UpdateSupplierContactDto supplierContactDto) {
        SupplierContact updatedSupplierContact = supplierMapper.updateContactDtoToContact(supplierContactDto);
        
        supplierService.updateContact(supplierId, contactId, updatedSupplierContact);
    }
    
    @PatchMapping("/suppliers/{supplierId}/contacts/{contactId}")
    public void patchContact(@PathVariable Long supplierId, @PathVariable Long contactId, @Valid @RequestBody UpdateSupplierContactDto supplierContactDto) {
        SupplierContact updatedSupplierContact = supplierMapper.updateContactDtoToContact(supplierContactDto);
        
        supplierService.updateContact(supplierId, contactId, updatedSupplierContact);
    }

    @DeleteMapping("/suppliers/{supplierId}/contacts/{contactId}")
    public void deleteContact(@PathVariable Long supplierId, @PathVariable Long contactId) {
        supplierService.deleteContact(supplierId, contactId);
    }

}
