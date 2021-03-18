package io.company.brewcraft.service;

import org.springframework.data.domain.Page;

import io.company.brewcraft.dto.UpdateSupplier;
import io.company.brewcraft.model.Supplier;

public interface SupplierService {

    public Page<Supplier> getSuppliers(int page, int size, String[] sort, boolean order_asc);
    
    public Supplier getSupplier(Long id);

    public Supplier addSupplier(Supplier supplier);
    
    public Supplier putSupplier(Long id, Supplier supplier);
    
    public Supplier patchSupplier(Long id, Supplier supplier);

    public void deleteSupplier(Long id);
    
    public boolean supplierExists(Long supplierId);   
}
