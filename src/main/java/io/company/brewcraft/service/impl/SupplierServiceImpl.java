package io.company.brewcraft.service.impl;

import java.util.List;

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
import io.company.brewcraft.service.SupplierService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.utils.EntityHelper;

@Transactional
public class SupplierServiceImpl implements SupplierService {
    public static final Logger log = LoggerFactory.getLogger(SupplierServiceImpl.class);

    private SupplierRepository supplierRepository;
    
    private SupplierContactRepository supplierContactRepository;
    
    private EntityHelper entityHelper;
        
    public SupplierServiceImpl(SupplierRepository supplierRepository, SupplierContactRepository supplierContactRepository, EntityHelper entityHelper) {
        this.supplierRepository = supplierRepository;
        this.supplierContactRepository = supplierContactRepository;
        this.entityHelper = entityHelper;
    }

    @Override
    public Page<Supplier> getSuppliers(int page, int size, String[] sort, boolean order_asc) {
        Pageable paging = PageRequest.of(page, size, Sort.by(order_asc ? Direction.ASC : Direction.DESC, sort));

        Page<Supplier> suppliers = supplierRepository.findAll(paging);

        return suppliers; 
    }

    @Override
    public Supplier getSupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Supplier", id.toString()));
        
        return supplier;
    }

    @Override
    public void addSupplier(Supplier supplier) {       
        supplierRepository.save(supplier);
    }

    @Override
    public void updateSupplier(Long id, Supplier updatedSupplier) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Supplier", id.toString()));

        entityHelper.applyUpdate(updatedSupplier, supplier);

        supplierRepository.save(updatedSupplier);
    }

    @Override
    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);        
    }
    
    @Override
    public boolean supplierExists(Long supplierId) {
        return supplierRepository.existsById(supplierId);     
    }
    
    @Override
    public List<SupplierContact> getContacts(Long supplierId) {
        if (!supplierExists(supplierId)) {
            throw new EntityNotFoundException("Supplier", supplierId.toString());
        }
          
        return supplierContactRepository.findAllBySupplierId(supplierId);
    }

    @Override
    public SupplierContact getContact(Long supplierId, Long contactId) {
        if (!supplierExists(supplierId)) {
            throw new EntityNotFoundException("Supplier", supplierId.toString());
        }
        
        return supplierContactRepository.findById(contactId).orElseThrow(() -> new EntityNotFoundException("SupplierContact", contactId.toString()));
    }

    @Override
    public void addContact(Long supplierId, SupplierContact supplierContact) {
        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow(() -> new EntityNotFoundException("Supplier", supplierId.toString()));
        
        supplierContact.setSupplier(supplier);
        
        supplierContactRepository.save(supplierContact);
    }

    @Override
    public void updateContact(Long supplierId, Long contactId, SupplierContact updatedSupplierContact) {
        if (!supplierExists(supplierId)) {
            throw new EntityNotFoundException("Supplier", supplierId.toString());
        }

        SupplierContact supplierContact = supplierContactRepository.findById(contactId).orElseThrow(() -> new EntityNotFoundException("SupplierContact", contactId.toString()));

        entityHelper.applyUpdate(updatedSupplierContact, supplierContact);

        supplierContactRepository.save(updatedSupplierContact);
    }

    @Override
    public void deleteContact(Long supplierId, Long contactId) {
        if (!supplierExists(supplierId)) {
            throw new EntityNotFoundException("Supplier", supplierId.toString());
        }
        
        supplierContactRepository.deleteById(contactId);        
    }
       
}
