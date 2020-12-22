package io.company.brewcraft.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.model.SupplierContact;
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
    
    public Page<SupplierContact> getSupplierContacts(int page, int size, String[] sort, boolean order_asc) {
        Pageable paging = PageRequest.of(page, size, Sort.by(order_asc ? Direction.ASC : Direction.DESC, sort));

        Page<SupplierContact> supplierContacts = supplierContactRepository.findAll(paging);

        return supplierContacts; 
    }
    
    @Override
    public SupplierContact getContact(Long contactId) {        
        return supplierContactRepository.findById(contactId).orElse(null);
    }

    @Override
    public SupplierContact addContact(Long supplierId, SupplierContact supplierContact) {
        Supplier supplier = supplierRepository.findById(supplierId).orElse(null);
        
        supplierContact.setSupplier(supplier);
        
        return supplierContactRepository.save(supplierContact);
    }

    @Override
    public SupplierContact putContact(Long supplierId, Long contactId, SupplierContact updatedSupplierContact) {
        SupplierContact supplierContact = supplierContactRepository.findById(contactId).orElse(null);
        
        if (supplierContact != null) {
            updatedSupplierContact.outerJoin(supplierContact);
        }

        updatedSupplierContact.setId(contactId);
        
        return addContact(supplierId, updatedSupplierContact);
    }
    
    @Override
    public SupplierContact patchContact(Long contactId, SupplierContact updatedContact) {
        SupplierContact contact = supplierContactRepository.findById(contactId).orElseThrow(() -> new EntityNotFoundException("SupplierContact", contactId.toString()));

        updatedContact.outerJoin(contact);

        return supplierContactRepository.save(updatedContact);
    }

    @Override
    public void deleteContact(Long contactId) {
        supplierContactRepository.deleteById(contactId);        
    }

}
