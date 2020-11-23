package io.company.brewcraft.service;

import java.util.List;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.model.SupplierContact;

public interface SupplierService {

    public Page<Supplier> getSuppliers(int page, int size, String[] sort, boolean order_asc);
    
    public Supplier getSupplier(Long id);

    public void addSupplier(Supplier supplier);
    
    public void updateSupplier(Long id, Supplier supplier);

    public void deleteSupplier(Long id);
    
    public boolean supplierExists(Long supplierId);
    
    public List<SupplierContact> getContacts(Long supplierId);
    
    public SupplierContact getContact(Long supplierId, Long contactId);
    
    public void addContact(Long supplierId, SupplierContact supplierContact);
    
    public void updateContact(Long supplierId, Long contactId, SupplierContact supplierContact);
    
    public void deleteContact(Long supplierId, Long contactId);
}
