package io.company.brewcraft.service;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.SupplierContactEntity;

public interface SupplierContactService {
    
    public Page<SupplierContactEntity> getSupplierContacts(int page, int size, String[] sort, boolean order_asc);
    
    public SupplierContactEntity getContact(Long contactId);
    
    public SupplierContactEntity addContact(Long supplierId, SupplierContactEntity supplierContact);
    
    public SupplierContactEntity putContact(Long supplierId, Long contactId, SupplierContactEntity supplierContact);
    
    public SupplierContactEntity patchContact(Long contactId, SupplierContactEntity updatedContact);
    
    public void deleteContact(Long contactId);

}
