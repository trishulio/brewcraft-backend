package io.company.brewcraft.service;

import java.util.Iterator;
import java.util.List;
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

    public InvoiceStatus getStatus(String name) {
        if (name == null) {
            throw new NullPointerException("Non-null status name expected");
        }

        InvoiceStatus status = null;
        Iterator<InvoiceStatus> it = this.repo.findByNames(Set.of(name)).iterator();
        if (it.hasNext()) {
            status = it.next();
        }
        
        return status;
    }
    
    public List<InvoiceStatus> getStatuses(Set<String> names) {
        List<InvoiceStatus> statuses = null;
        
        if (names == null) {
            throw new NullPointerException("Non-null name-set is expected");

        } else if (!names.isEmpty()) {            
            statuses = (List<InvoiceStatus>) this.repo.findByNames(names);
        }
        
        return statuses;
    }
}
