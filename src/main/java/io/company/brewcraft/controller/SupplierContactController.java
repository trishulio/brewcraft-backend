package io.company.brewcraft.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.company.brewcraft.dto.GetSupplierContactsDto;
import io.company.brewcraft.dto.SupplierContactWithSupplierDto;
import io.company.brewcraft.model.SupplierContact;
import io.company.brewcraft.service.SupplierContactService;
import io.company.brewcraft.service.mapper.SupplierContactMapper;

@RestController
@RequestMapping(path = "/api")
public class SupplierContactController {
    
    private SupplierContactService supplierContactService;
    
    private SupplierContactMapper supplierContactMapper = SupplierContactMapper.INSTANCE;

    public SupplierContactController(SupplierContactService supplierContactService) {
        this.supplierContactService = supplierContactService;
    }
    
    @GetMapping("/suppliers/contacts")
    public GetSupplierContactsDto getSuppliers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size, @RequestParam(defaultValue = "id") String[] sort, @RequestParam(defaultValue = "true") boolean order_asc) {                
        Page<SupplierContact> supplierContacts = supplierContactService.getSupplierContacts(page, size, sort, order_asc);
      
        List<SupplierContactWithSupplierDto> supplierContactDtos = supplierContacts.stream().map(supplierContact -> supplierContactMapper.supplierContactToSupplierContactWithSupplierDto(supplierContact)).collect(Collectors.toList());
    
        return new GetSupplierContactsDto(supplierContactDtos, supplierContacts.getTotalElements(), supplierContacts.getTotalPages());   
    }

}
