package io.company.brewcraft.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.SupplierContact;
import io.company.brewcraft.repository.SupplierContactRepository;
import io.company.brewcraft.service.SupplierContactService;

@Transactional
public class SupplierContactServiceImpl implements SupplierContactService {
    public static final Logger log = LoggerFactory.getLogger(SupplierContactServiceImpl.class);

    private SupplierContactRepository supplierContactRepository;

    public SupplierContactServiceImpl(SupplierContactRepository supplierContactRepository) {
        this.supplierContactRepository = supplierContactRepository;
    }
    
    public Page<SupplierContact> getSupplierContacts(int page, int size, String[] sort, boolean order_asc) {
        Pageable paging = PageRequest.of(page, size, Sort.by(order_asc ? Direction.ASC : Direction.DESC, sort));

        Page<SupplierContact> supplierContacts = supplierContactRepository.findAll(paging);

        return supplierContacts; 
    }

}
