package io.company.brewcraft.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.pojo.Supplier;
import io.company.brewcraft.pojo.SupplierContact;
import io.company.brewcraft.dto.UpdateSupplierContact;
import io.company.brewcraft.model.SupplierContactEntity;
import io.company.brewcraft.repository.SupplierContactRepository;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.SupplierContactService;
import io.company.brewcraft.service.SupplierService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.CycleAvoidingMappingContext;
import io.company.brewcraft.service.mapper.SupplierContactMapper;

@Transactional
public class SupplierContactServiceImpl extends BaseService implements SupplierContactService {
    public static final Logger log = LoggerFactory.getLogger(SupplierContactServiceImpl.class);
    
    private static final SupplierContactMapper supplierContactMapper = SupplierContactMapper.INSTANCE;

    private SupplierContactRepository supplierContactRepository;
    
    private SupplierService supplierService;

    public SupplierContactServiceImpl(SupplierContactRepository supplierContactRepository, SupplierService supplierService) {
        this.supplierContactRepository = supplierContactRepository;
        this.supplierService = supplierService;
    }
    
    public Page<SupplierContact> getSupplierContacts(int page, int size, String[] sort, boolean order_asc) {
        Pageable paging = PageRequest.of(page, size, Sort.by(order_asc ? Direction.ASC : Direction.DESC, sort));

        Page<SupplierContactEntity> supplierContacts = supplierContactRepository.findAll(paging);
        
        return supplierContacts.map(contact -> supplierContactMapper.fromEntity(contact,
                new CycleAvoidingMappingContext()));
    }
    
    @Override
    public SupplierContact getContact(Long contactId) {        
        SupplierContactEntity contact = supplierContactRepository.findById(contactId).orElse(null);
        
        return supplierContactMapper.fromEntity(contact, new CycleAvoidingMappingContext());
    }

    @Override
    public SupplierContact addContact(Long supplierId, SupplierContact supplierContact) {
        Supplier supplier = Optional.ofNullable(supplierService.getSupplier(supplierId)).orElseThrow(() -> new EntityNotFoundException("Supplier", supplierId.toString()));
        
        supplierContact.setSupplier(supplier);
        
        SupplierContactEntity contactEntity = supplierContactMapper.toEntity(supplierContact, new CycleAvoidingMappingContext());
        
        SupplierContactEntity addedContact = supplierContactRepository.save(contactEntity);
        
        return supplierContactMapper.fromEntity(addedContact, new CycleAvoidingMappingContext());
    }

    @Override
    public SupplierContact putContact(Long supplierId, Long contactId, UpdateSupplierContact updatedSupplierContact) {
        SupplierContact existing = getContact(contactId);

        if (existing == null) {
            existing = new SupplierContact();
            existing.setId(contactId);
            existing.setCreatedAt(LocalDateTime.now()); // TODO: This is a hack. Need a fix at hibernate level to avoid any hibernate issues.
        }

        existing.override(updatedSupplierContact, getPropertyNames(UpdateSupplierContact.class));
        
        return addContact(supplierId, existing);
    }
    
    @Override
    public SupplierContact patchContact(Long contactId, Long supplierId, UpdateSupplierContact updatedContact) {        
        SupplierContact existing = Optional.ofNullable(getContact(contactId)).orElseThrow(() -> new EntityNotFoundException("SupplierContact", contactId.toString()));

        existing.outerJoin(updatedContact, getPropertyNames(UpdateSupplierContact.class));
        
        Long patchSupplierId = supplierId != null ? supplierId : existing.getSupplier().getId();
        
        return addContact(patchSupplierId, existing);
    }

    @Override
    public void deleteContact(Long contactId) {
        supplierContactRepository.deleteById(contactId);        
    }

}
