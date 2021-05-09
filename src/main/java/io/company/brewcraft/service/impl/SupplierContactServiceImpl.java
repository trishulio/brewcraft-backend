package io.company.brewcraft.service.impl;

import static io.company.brewcraft.repository.RepositoryUtil.*;

import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.dto.UpdateSupplierContact;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.model.SupplierContact;
import io.company.brewcraft.repository.SupplierContactRepository;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.SupplierContactService;
import io.company.brewcraft.service.SupplierService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class SupplierContactServiceImpl extends BaseService implements SupplierContactService {
    public static final Logger log = LoggerFactory.getLogger(SupplierContactServiceImpl.class);
    
    private SupplierContactRepository supplierContactRepository;
    
    private SupplierService supplierService;

    public SupplierContactServiceImpl(SupplierContactRepository supplierContactRepository, SupplierService supplierService) {
        this.supplierContactRepository = supplierContactRepository;
        this.supplierService = supplierService;
    }
    
    public Page<SupplierContact> getSupplierContacts(int page, int size, Set<String> sort, boolean orderAscending) {
        Page<SupplierContact> supplierContacts = supplierContactRepository.findAll(pageRequest(sort, orderAscending, page, size));
        
        return supplierContacts;
    }
    
    @Override
    public SupplierContact getContact(Long contactId) {        
        SupplierContact contact = supplierContactRepository.findById(contactId).orElse(null);
        
        return contact;
    }

    @Override
    public SupplierContact addContact(Long supplierId, SupplierContact supplierContact) {
        Supplier supplier = Optional.ofNullable(supplierService.getSupplier(supplierId)).orElseThrow(() -> new EntityNotFoundException("Supplier", supplierId.toString()));
        
        supplierContact.setSupplier(supplier);
                
        SupplierContact addedContact = supplierContactRepository.saveAndFlush(supplierContact);
        
        return addedContact;
    }

    @Override
    public SupplierContact putContact(Long supplierId, Long contactId, SupplierContact updatedSupplierContact) {
        updatedSupplierContact.setId(contactId);

        return addContact(supplierId, updatedSupplierContact);
    }
    
    @Override
    public SupplierContact patchContact(Long contactId, Long supplierId, SupplierContact updatedContact) {        
        UpdateSupplierContact existing = Optional.ofNullable(getContact(contactId)).orElseThrow(() -> new EntityNotFoundException("SupplierContact", contactId.toString()));

        updatedContact.copyToNullFields(existing);
        
        Long patchSupplierId = supplierId != null ? supplierId : existing.getSupplier().getId();
        
        return addContact(patchSupplierId, updatedContact);
    }

    @Override
    public void deleteContact(Long contactId) {
        supplierContactRepository.deleteById(contactId);        
    }

}
