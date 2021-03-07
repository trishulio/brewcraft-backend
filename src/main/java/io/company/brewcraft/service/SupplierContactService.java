package io.company.brewcraft.service;

import org.springframework.data.domain.Page;

import io.company.brewcraft.dto.UpdateSupplierContact;
import io.company.brewcraft.pojo.SupplierContact;

public interface SupplierContactService {
    
    public Page<SupplierContact> getSupplierContacts(int page, int size, String[] sort, boolean order_asc);
    
    public SupplierContact getContact(Long contactId);
    
    public SupplierContact addContact(Long supplierId, SupplierContact supplierContact);
    
    public SupplierContact putContact(Long supplierId, Long contactId, UpdateSupplierContact supplierContact);
    
    public SupplierContact patchContact(Long contactId, Long supplierId, UpdateSupplierContact updatedContact);
    
    public void deleteContact(Long contactId);

}
