package io.company.brewcraft.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.SupplierEntity;
import io.company.brewcraft.repository.SupplierRepository;
import io.company.brewcraft.service.SupplierService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class SupplierServiceImpl implements SupplierService {
    public static final Logger log = LoggerFactory.getLogger(SupplierServiceImpl.class);

    private SupplierRepository supplierRepository;
      
    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Page<SupplierEntity> getSuppliers(int page, int size, String[] sort, boolean order_asc) {
        Pageable paging = PageRequest.of(page, size, Sort.by(order_asc ? Direction.ASC : Direction.DESC, sort));

        Page<SupplierEntity> suppliers = supplierRepository.findAll(paging);

        return suppliers; 
    }

    @Override
    public SupplierEntity getSupplier(Long id) {
        SupplierEntity supplier = supplierRepository.findById(id).orElse(null);
        
        return supplier;
    }

    @Override
    public SupplierEntity addSupplier(SupplierEntity supplier) {       
        return supplierRepository.save(supplier);
    }

    @Override
    public SupplierEntity putSupplier(Long id, SupplierEntity updatedSupplier) {
        SupplierEntity supplier = supplierRepository.findById(id).orElse(null);
        
        if (supplier != null) {
            updatedSupplier.outerJoin(supplier);
        }

        updatedSupplier.setId(id);

        return supplierRepository.save(updatedSupplier);
    }
    
    @Override
    public SupplierEntity patchSupplier(Long id, SupplierEntity updatedSupplier) {
        SupplierEntity supplier = supplierRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Supplier", id.toString()));

        updatedSupplier.outerJoin(supplier);

        return supplierRepository.save(updatedSupplier);
    }

    @Override
    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);        
    }
    
    @Override
    public boolean supplierExists(Long supplierId) {
        return supplierRepository.existsById(supplierId);     
    }
          
}
