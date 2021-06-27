package io.company.brewcraft.controller;

import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import io.company.brewcraft.dto.AddSupplierContactDto;
import io.company.brewcraft.dto.GetSupplierContactsDto;
import io.company.brewcraft.dto.SupplierContactDto;
import io.company.brewcraft.dto.SupplierContactWithSupplierDto;
import io.company.brewcraft.dto.UpdateSupplierContactDto;
import io.company.brewcraft.dto.UpdateSupplierContactWithSupplierDto;
import io.company.brewcraft.model.SupplierContact;
import io.company.brewcraft.service.SupplierContactService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.SupplierContactMapper;
import io.company.brewcraft.util.controller.AttributeFilter;

@RestController
@RequestMapping(path = "/api/v1/suppliers")
public class SupplierContactController extends BaseController {
    
    private SupplierContactService supplierContactService;
    
    private SupplierContactMapper supplierContactMapper = SupplierContactMapper.INSTANCE;

    public SupplierContactController(SupplierContactService supplierContactService, AttributeFilter filter) {
        super(filter);
        this.supplierContactService = supplierContactService;
    }
    
    @GetMapping("/contacts")
    public GetSupplierContactsDto getContacts(
        @RequestParam(name = PROPNAME_SORT_BY, defaultValue = VALUE_DEFAULT_SORT_BY) SortedSet<String> sort,
        @RequestParam(name = PROPNAME_ORDER_ASC, defaultValue = VALUE_DEFAULT_ORDER_ASC) boolean orderAscending,
        @RequestParam(name = PROPNAME_PAGE_INDEX, defaultValue = VALUE_DEFAULT_PAGE_INDEX) int page,
        @RequestParam(name = PROPNAME_PAGE_SIZE, defaultValue = VALUE_DEFAULT_PAGE_SIZE) int size
    ) {                
        Page<SupplierContact> supplierContacts = supplierContactService.getSupplierContacts(page, size, sort, orderAscending);
      
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
