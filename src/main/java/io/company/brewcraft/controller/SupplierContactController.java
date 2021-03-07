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

import io.company.brewcraft.dto.AddSupplierContactDto;
import io.company.brewcraft.dto.GetSupplierContactsDto;
import io.company.brewcraft.dto.SupplierContactDto;
import io.company.brewcraft.dto.SupplierContactWithSupplierDto;
import io.company.brewcraft.dto.UpdateSupplierContactDto;
import io.company.brewcraft.dto.UpdateSupplierContactWithSupplierDto;
import io.company.brewcraft.pojo.SupplierContact;
import io.company.brewcraft.service.SupplierContactService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.SupplierContactMapper;

@RestController
@RequestMapping(path = "/api/v1/suppliers")
public class SupplierContactController {
    
    private SupplierContactService supplierContactService;
    
    private SupplierContactMapper supplierContactMapper = SupplierContactMapper.INSTANCE;

    public SupplierContactController(SupplierContactService supplierContactService) {
        this.supplierContactService = supplierContactService;
    }
    
    @GetMapping("/contacts")
    public GetSupplierContactsDto getContacts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size, @RequestParam(defaultValue = "id") String[] sort, @RequestParam(defaultValue = "true") boolean order_asc) {                
        Page<SupplierContact> supplierContacts = supplierContactService.getSupplierContacts(page, size, sort, order_asc);
      
        List<SupplierContactWithSupplierDto> supplierContactDtos = supplierContacts.stream().map(supplierContact -> supplierContactMapper.toDtoWithSupplier(supplierContact)).collect(Collectors.toList());
    
        return new GetSupplierContactsDto(supplierContactDtos, supplierContacts.getTotalElements(), supplierContacts.getTotalPages());   
    }
    
    @GetMapping("/contacts/{contactId}")
    public SupplierContactWithSupplierDto getContact(@PathVariable Long contactId) {
        SupplierContact supplierContact = supplierContactService.getContact(contactId);
        
        if (supplierContact == null) {
            throw new EntityNotFoundException("SupplierContact", contactId.toString());
        } else {
            
        }
        
        return supplierContactMapper.toDtoWithSupplier(supplierContact);
    }

    @PostMapping("{supplierId}/contacts")
    @ResponseStatus(HttpStatus.CREATED)
    public SupplierContactDto addContact(@PathVariable Long supplierId, @Valid @RequestBody AddSupplierContactDto supplierContactDo) {
        SupplierContact supplierContact = supplierContactMapper.fromDto(supplierContactDo);    
        
        SupplierContact addedContact = supplierContactService.addContact(supplierId, supplierContact);
        
        return supplierContactMapper.toDto(addedContact);
    }
    
    @PutMapping("{supplierId}/contacts/{contactId}")
    public SupplierContactDto putContact(@PathVariable Long supplierId, @PathVariable Long contactId, @Valid @RequestBody UpdateSupplierContactDto supplierContactDto) {
        SupplierContact supplierContact = supplierContactMapper.fromDto(supplierContactDto);
        
        SupplierContact putContact = supplierContactService.putContact(supplierId, contactId, supplierContact);
        
        return supplierContactMapper.toDto(putContact);
    }
    
    @PatchMapping("/contacts/{contactId}")
    public SupplierContactWithSupplierDto patchContact(@PathVariable Long contactId, @Valid @RequestBody UpdateSupplierContactWithSupplierDto supplierContactDto) {
        SupplierContact supplierContact  = supplierContactMapper.fromDto(supplierContactDto);
        
        SupplierContact patchedContact = supplierContactService.patchContact(contactId, supplierContactDto.getSupplierId(), supplierContact);
        
        return supplierContactMapper.toDtoWithSupplier(patchedContact);
    }

    @DeleteMapping("/contacts/{contactId}")
    public void deleteContact(@PathVariable Long contactId) {
        supplierContactService.deleteContact(contactId);
    }

}
