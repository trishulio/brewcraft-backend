package io.company.brewcraft.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.dto.SupplierDto;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.repository.SupplierRepository;
import io.company.brewcraft.service.SupplierService;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.SupplierMapper;

@Transactional
public class SupplierServiceImpl implements SupplierService {
    public static final Logger log = LoggerFactory.getLogger(SupplierServiceImpl.class);

    private SupplierRepository supplierRepository;
    
    private SupplierMapper supplierMapper;
    
    public SupplierServiceImpl(SupplierRepository supplierRepository, SupplierMapper supplierMapper) {
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
    }

    @Override
    public List<SupplierDto> getSuppliers() {
        return supplierRepository.findAll().stream().map(supplier -> supplierMapper.supplierToSupplierDto(supplier)).collect(Collectors.toList());
    }

    @Override
    public SupplierDto getSupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Supplier", id.toString()));
        
        return supplierMapper.supplierToSupplierDto(supplier);
    }

    @Override
    public void addSupplier(SupplierDto supplierDto) {
        Supplier supplier = supplierMapper.supplierDtoToSupplier(supplierDto);    
        
        supplierRepository.save(supplier);
    }

    @Override
    public void updateSupplier(SupplierDto supplierDto, Long id) {
        if (!supplierRepository.existsById(id)) {
            throw new EntityNotFoundException("Supplier", id.toString());
        }

        Supplier updatedSupplier = supplierMapper.supplierDtoToSupplier(supplierDto);
        updatedSupplier.setId(id); 

        supplierRepository.save(updatedSupplier);
    }

    @Override
    public void deleteSupplier(Long id) {
        supplierRepository.deleteById(id);        
    }

}
