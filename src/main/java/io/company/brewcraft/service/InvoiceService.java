package io.company.brewcraft.service;

import static io.company.brewcraft.repository.RepositoryUtil.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.InvoiceEntity;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.model.Supplier;
import io.company.brewcraft.pojo.Invoice;
import io.company.brewcraft.repository.InvoiceRepository;
import io.company.brewcraft.repository.InvoiceRepositoryGetAllInvoicesSpecification;
import io.company.brewcraft.service.exception.EntityNotFoundException;
import io.company.brewcraft.service.mapper.InvoiceMapper;

@Transactional
public class InvoiceService {

    private static final InvoiceMapper INVOICE_MAPPER = InvoiceMapper.INSTANCE;

    private InvoiceRepository repo;

    private SupplierService supplierService;
    
    public InvoiceService() {
        this(null, null);
    }

    public InvoiceService(InvoiceRepository repo, SupplierService supplierService) {
        this.repo = repo;
        this.supplierService = supplierService;
    }

    public Page<Invoice> getInvoices(Set<Long> ids, LocalDateTime from, LocalDateTime to, Set<InvoiceStatus> statuses, Set<Long> supplierIds, Set<String> sort, boolean orderAscending, int page, int size) {
        if (from != null || to != null) {
            if (from == null) {
                from = LocalDateTime.MIN;
            }

            if (to == null) {
                to = LocalDateTime.MAX;
            }
        }

        Page<InvoiceEntity> entityPage = repo.findAll(new InvoiceRepositoryGetAllInvoicesSpecification(ids, from, to, statuses, supplierIds), pageRequest(sort, orderAscending, page, size));

        return entityPage.map(INVOICE_MAPPER::fromEntity);
    }

    public Invoice getInvoice(Long id) {
        Invoice invoice = null;
        Optional<InvoiceEntity> optional = repo.findById(id);
        if (optional.isPresent()) {
            invoice = INVOICE_MAPPER.fromEntity(optional.get());
        }

        return invoice;
    }

    public boolean exists(Long id) {
        return repo.existsById(id);
    }

    public void delete(Long id) {
        if (!exists(id)) {
            throw new EntityNotFoundException("Invoice", id.toString());
        }
        repo.deleteById(id);
    }

    public Invoice update(Long supplierId, Long invoiceId, Invoice invoice) {
        InvoiceEntity entity = INVOICE_MAPPER.toEntity(invoice);

        InvoiceEntity existing = repo.findById(invoiceId).orElse(null);
        if (existing != null) {
            entity.outerJoin(existing);
        }

        entity.setId(invoiceId);

        return add(supplierId, entity);
    }

    public Invoice add(Long supplierId, Invoice invoice) {
        return add(supplierId, INVOICE_MAPPER.toEntity(invoice));
    }

    private Invoice add(Long supplierId, InvoiceEntity invoice) {
        if (invoice.getSupplier() == null || invoice.getSupplier().getId() != supplierId) {
            Supplier supplier = supplierService.getSupplier(supplierId);
            if (supplier == null) {
                // SupplierService takes care of throwing exception but just in
                // case that changes, this will act as a safeguard
                throw new EntityNotFoundException("Supplier", supplierId.toString());
            }
            invoice.setSupplier(supplier);
        }

        InvoiceEntity saved = repo.save(invoice);

        return INVOICE_MAPPER.fromEntity(saved);
    }
}
