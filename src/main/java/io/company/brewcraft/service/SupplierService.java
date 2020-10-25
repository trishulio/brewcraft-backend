package io.company.brewcraft.service;

import java.util.List;

import io.company.brewcraft.dto.SupplierDto;

public interface SupplierService {

    public List<SupplierDto> getSuppliers();
    
    public SupplierDto getSupplier(Long id);

    public void addSupplier(SupplierDto tenant);
    
    public void updateSupplier(SupplierDto tenant, Long id);

    public void deleteSupplier(Long id);
    
}
