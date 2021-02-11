package io.company.brewcraft.service;

import io.company.brewcraft.model.InvoiceStatusEntity;
import io.company.brewcraft.pojo.InvoiceStatus;
import io.company.brewcraft.repository.InvoiceStatusRepository;
import io.company.brewcraft.service.mapper.InvoiceStatusMapper;

public class InvoiceStatusService {
    private static final InvoiceStatusMapper mapper = InvoiceStatusMapper.INSTANCE;

    private InvoiceStatusRepository repo;

    public InvoiceStatusService(InvoiceStatusRepository repo) {
        this.repo = repo;
    }

    public InvoiceStatus getInvoiceStatus(String name) {
        InvoiceStatus status = null;
        InvoiceStatusEntity entity = this.repo.findByName(name).orElse(null);
        if (entity != null) {
            status = mapper.fromEntity(entity);
        }
        
        return status;
    }
}
