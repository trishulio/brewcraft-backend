package io.company.brewcraft.service;

import java.util.Set;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.SupplierContact;

public interface SupplierContactService {
    
    public Page<SupplierContact> getSupplierContacts(int page, int size, Set<String> sort, boolean orderAscending);
    
    public SupplierContact getContact(Long contactId);
    
    public SupplierContact addContact(Long supplierId, SupplierContact supplierContact);
    
    public SupplierContact putContact(Long supplierId, Long contactId, SupplierContact supplierContact);
    
    public SupplierContact patchContact(Long contactId, Long supplierId, SupplierContact updatedContact);
    
    public void deleteContact(Long contactId);

}
