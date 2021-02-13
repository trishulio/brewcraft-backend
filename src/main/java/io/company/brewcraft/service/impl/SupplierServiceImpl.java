package io.company.brewcraft.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.dto.UpdateSupplier;
import io.company.brewcraft.model.SupplierEntity;
import io.company.brewcraft.pojo.Supplier;
import io.company.brewcraft.repository.SupplierRepository;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.SupplierService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.SupplierMapper;

@Transactional
public class SupplierServiceImpl extends BaseService implements SupplierService {
    public static final Logger log = LoggerFactory.getLogger(SupplierServiceImpl.class);
    
    private static final SupplierMapper supplierMapper = SupplierMapper.INSTANCE;

    private SupplierRepository supplierRepository;
      
    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Page<Supplier> getSuppliers(int page, int size, String[] sort, boolean order_asc) {
        Pageable paging = PageRequest.of(page, size, Sort.by(order_asc ? Direction.ASC : Direction.DESC, sort));

        Page<SupplierEntity> suppliers = supplierRepository.findAll(paging);

        return suppliers.map(supplierMapper::fromEntity);
    }

    @Override
    public Supplier getSupplier(Long id) {
        SupplierEntity supplier = supplierRepository.findById(id).orElse(null);
        
        return supplierMapper.fromEntity(supplier);
    }

    @Override
    public Supplier addSupplier(Supplier supplier) {   
        SupplierEntity supplierEntity = supplierMapper.toEntity(supplier);
        
        SupplierEntity addedSupplier = supplierRepository.save(supplierEntity);
        
        return supplierMapper.fromEntity(addedSupplier);
    }

    @Override
    public Supplier putSupplier(Long id, UpdateSupplier updatedSupplier) {
        Supplier existing = getSupplier(id);

        if (existing == null) {
            existing = new Supplier();
            existing.setId(id);
            existing.setCreatedAt(LocalDateTime.now()); // TODO: This is a hack. Need a fix at hibernate level to avoid any hibernate issues.
        }

        existing.override(updatedSupplier, getPropertyNames(UpdateSupplier.class));

        return addSupplier(existing);
    }
    
    @Override
    public Supplier patchSupplier(Long id, UpdateSupplier updatedSupplier) {
        Supplier existing = Optional.ofNullable(getSupplier(id)).orElseThrow(() -> new EntityNotFoundException("Supplier", id.toString()));

        existing.outerJoin(updatedSupplier, getPropertyNames(UpdateSupplier.class));
        
        return addSupplier(existing);
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
