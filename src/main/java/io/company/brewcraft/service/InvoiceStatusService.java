package io.company.brewcraft.service;

import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.repository.InvoiceStatusRepository;

public class InvoiceStatusService {
    private InvoiceStatusRepository repo;

    public InvoiceStatusService(InvoiceStatusRepository repo) {
        this.repo = repo;
    }

    public InvoiceStatus getInvoiceStatus(String name) {
        return this.repo.findByName(name).orElse(null);
    }
}
