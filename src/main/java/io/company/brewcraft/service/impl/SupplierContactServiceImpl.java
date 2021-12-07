package io.company.brewcraft.service.impl;

import static io.company.brewcraft.repository.RepositoryUtil.*;

import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.dto.UpdateSupplierContact;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.model.SupplierContact;
import io.company.brewcraft.repository.SupplierContactRepository;
import io.company.brewcraft.repository.WhereClauseBuilder;
import io.company.brewcraft.service.BaseService;
import io.company.brewcraft.service.SupplierContactService;
import io.company.brewcraft.service.SupplierService;
import io.company.brewcraft.service.exception.EntityNotFoundException;

@Transactional
public class SupplierContactServiceImpl extends BaseService implements SupplierContactService {
    private static final Logger log = LoggerFactory.getLogger(SupplierContactServiceImpl.class);

    private SupplierContactRepository supplierContactRepository;

    private SupplierService supplierService;

    public SupplierContactServiceImpl(SupplierContactRepository supplierContactRepository, SupplierService supplierService) {
        this.supplierContactRepository = supplierContactRepository;
        this.supplierService = supplierService;
    }

    @Override
    public Page<SupplierContact> getSupplierContacts(Set<Long> ids, Set<Long> supplierIds, int page, int size, SortedSet<String> sort, boolean orderAscending) {
        Specification<SupplierContact> spec = WhereClauseBuilder
                .builder()
                .in(SupplierContact.FIELD_ID, ids)
                .in(new String[] {SupplierContact.FIELD_SUPPLIER, Supplier.FIELD_ID}, supplierIds)
                .build();

        Page<SupplierContact> supplierContacts = supplierContactRepository.findAll(spec, pageRequest(sort, orderAscending, page, size));

        return supplierContacts;
    }

    @Override
    public SupplierContact getContact(Long contactId) {
        SupplierContact contact = supplierContactRepository.findById(contactId).orElse(null);

        return contact;
    }

    @Override
    public SupplierContact addContact(Long supplierId, SupplierContact supplierContact) {
        Supplier supplier = Optional.ofNullable(supplierService.getSupplier(supplierId)).orElseThrow(() -> new EntityNotFoundException("Supplier", supplierId.toString()));

        supplierContact.setSupplier(supplier);

        SupplierContact addedContact = supplierContactRepository.saveAndFlush(supplierContact);

        return addedContact;
    }

    @Override
    public SupplierContact putContact(Long supplierId, Long contactId, SupplierContact updatedSupplierContact) {
        updatedSupplierContact.setId(contactId);

        return addContact(supplierId, updatedSupplierContact);
    }

    @Override
    public SupplierContact patchContact(Long contactId, Long supplierId, SupplierContact updatedContact) {
        UpdateSupplierContact existing = Optional.ofNullable(getContact(contactId)).orElseThrow(() -> new EntityNotFoundException("SupplierContact", contactId.toString()));

        updatedContact.copyToNullFields(existing);

        Long patchSupplierId = supplierId != null ? supplierId : existing.getSupplier().getId();

        return addContact(patchSupplierId, updatedContact);
    }

    @Override
    public void deleteContact(Long contactId) {
        supplierContactRepository.deleteById(contactId);
    }
}
