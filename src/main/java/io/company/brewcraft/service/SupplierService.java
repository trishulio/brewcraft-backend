package io.company.brewcraft.service;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.SupplierEntity;

public interface SupplierService {

    public Page<SupplierEntity> getSuppliers(int page, int size, String[] sort, boolean order_asc);
    
    public SupplierEntity getSupplier(Long id);

    public SupplierEntity addSupplier(SupplierEntity supplier);
    
    public SupplierEntity putSupplier(Long id, SupplierEntity supplier);
    
    public SupplierEntity patchSupplier(Long id, SupplierEntity supplier);

    public void deleteSupplier(Long id);
    
    public boolean supplierExists(Long supplierId);   
}
