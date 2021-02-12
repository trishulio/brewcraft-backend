package io.company.brewcraft.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.SupplierEntity;
import io.company.brewcraft.model.SupplierContactEntity;
import io.company.brewcraft.repository.SupplierContactRepository;
import io.company.brewcraft.repository.SupplierRepository;
import io.company.brewcraft.service.SupplierContactService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class SupplierContactServiceImpl implements SupplierContactService {
    public static final Logger log = LoggerFactory.getLogger(SupplierContactServiceImpl.class);

    private SupplierContactRepository supplierContactRepository;
    
    private SupplierRepository supplierRepository;

    public SupplierContactServiceImpl(SupplierContactRepository supplierContactRepository, SupplierRepository supplierRepository) {
        this.supplierContactRepository = supplierContactRepository;
        this.supplierRepository = supplierRepository;
    }
    
    public Page<SupplierContactEntity> getSupplierContacts(int page, int size, String[] sort, boolean order_asc) {
        Pageable paging = PageRequest.of(page, size, Sort.by(order_asc ? Direction.ASC : Direction.DESC, sort));

        Page<SupplierContactEntity> supplierContacts = supplierContactRepository.findAll(paging);

        return supplierContacts; 
    }
    
    @Override
    public SupplierContactEntity getContact(Long contactId) {        
        return supplierContactRepository.findById(contactId).orElse(null);
    }

    @Override
    public SupplierContactEntity addContact(Long supplierId, SupplierContactEntity supplierContact) {
        SupplierEntity supplier = supplierRepository.findById(supplierId).orElse(null);
        
        supplierContact.setSupplier(supplier);
        
        return supplierContactRepository.save(supplierContact);
    }

    @Override
    public SupplierContactEntity putContact(Long supplierId, Long contactId, SupplierContactEntity updatedSupplierContact) {
        SupplierContactEntity supplierContact = supplierContactRepository.findById(contactId).orElse(null);
        
        if (supplierContact != null) {
            updatedSupplierContact.outerJoin(supplierContact);
        }

        updatedSupplierContact.setId(contactId);
        
        return addContact(supplierId, updatedSupplierContact);
    }
    
    @Override
    public SupplierContactEntity patchContact(Long contactId, SupplierContactEntity updatedContact) {
        SupplierContactEntity contact = supplierContactRepository.findById(contactId).orElseThrow(() -> new EntityNotFoundException("SupplierContact", contactId.toString()));

        updatedContact.outerJoin(contact);

        return supplierContactRepository.save(updatedContact);
    }

    @Override
    public void deleteContact(Long contactId) {
        supplierContactRepository.deleteById(contactId);        
    }

}
