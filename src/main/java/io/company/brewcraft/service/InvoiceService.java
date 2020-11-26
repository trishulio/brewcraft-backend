package io.company.brewcraft.service;

import static io.company.brewcraft.repository.RepositoryUtil.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import io.company.brewcraft.model.Invoice;
import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.repository.InvoiceRepository;
import io.company.brewcraft.repository.InvoiceRepositoryGetAllInvoicesSpecification;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class InvoiceService {

    private InvoiceRepository repo;

    public InvoiceService(InvoiceRepository repo) {
        this.repo = repo;
    }

    public Page<Invoice> getInvoices(Date from, Date to, List<InvoiceStatus> statuses, List<Long> supplierIds, List<String> sort, boolean orderAscending, int page, int size) {
        if ((from == null || to == null) && (from != null || to != null)) {
            throw new IllegalArgumentException("Date to and from both need to be null or non-null together.");
        }

        Page<Invoice> invoicePage = repo.findAll(new InvoiceRepositoryGetAllInvoicesSpecification(from, to, statuses, supplierIds), pageRequest(sort, orderAscending, page, size));

        return invoicePage;
    }

    public Invoice getInvoice(Long id) {
        Invoice invoice = null;
        Optional<Invoice> optional = repo.findById(id);
        if (optional.isPresent()) {
            invoice = optional.get();
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

    public Invoice update(Long id, Invoice invoice) {
        Invoice existing = getInvoice(id);
        if (existing == null) {
            throw new EntityNotFoundException("Invoice", id.toString());
        }
        invoice.outerJoin(existing);

        Invoice updated = repo.save(invoice);

        return updated;
    }

    public Invoice add(Invoice invoice) {
        return repo.save(invoice);
    }
}
