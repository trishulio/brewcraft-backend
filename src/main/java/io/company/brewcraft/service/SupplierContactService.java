package io.company.brewcraft.service;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.SupplierContact;

public interface SupplierContactService {
    
    public Page<SupplierContact> getSupplierContacts(int page, int size, String[] sort, boolean order_asc);

}
