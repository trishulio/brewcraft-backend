package io.company.brewcraft.service;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.repository.InvoiceStatusRepository;

@Transactional
public class InvoiceStatusService {
    private InvoiceStatusRepository repo;

    public InvoiceStatusService(InvoiceStatusRepository repo) {
        this.repo = repo;
    }

    public InvoiceStatus getStatus(Long id) {
        InvoiceStatus status = null;

        Optional<InvoiceStatus> optional = repo.findById(id);
        if (optional.isPresent()) {
            status = optional.get();
        }

        return status;
    }
}
