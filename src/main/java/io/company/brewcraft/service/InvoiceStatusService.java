package io.company.brewcraft.service;

import java.util.Iterator;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.repository.InvoiceStatusRepository;

@Transactional
public class InvoiceStatusService {
    private InvoiceStatusRepository repo;

    public InvoiceStatusService(InvoiceStatusRepository repo) {
        this.repo = repo;
    }

    public InvoiceStatus getInvoiceStatus(String name) {
        InvoiceStatus status = null;
        Iterator<InvoiceStatus> it = this.repo.findByNames(Set.of(name)).iterator();
        if (it.hasNext()) {
            status = it.next();
        }
        
        return status;
    }
}
