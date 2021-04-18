package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.InvoiceStatus;
import io.company.brewcraft.service.InvoiceStatusAccessor;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class EnhancedInvoiceStatusRepositoryImpl implements EnhancedInvoiceStatusRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedInvoiceStatusRepositoryImpl.class);
    
    private InvoiceStatusRepository statusRepo;
    
    public EnhancedInvoiceStatusRepositoryImpl(InvoiceStatusRepository statusRepo) {
        this.statusRepo = statusRepo;
    }

    @Override
    public void refreshAccessors(Collection<? extends InvoiceStatusAccessor> accessors) {
        if (accessors != null && accessors.size() > 0) {
            Map<String, List<InvoiceStatusAccessor>> statusToItems = accessors.stream().filter(accessor -> accessor != null && accessor.getStatus() != null).collect(Collectors.groupingBy(accessor -> accessor.getStatus().getName()));

            Collection<InvoiceStatus> statuses = statusRepo.findByNames(statusToItems.keySet());

            if (statusToItems.keySet().size() != statuses.size()) {
                List<String> statusNames = statuses.stream().map(status -> status.getName()).collect(Collectors.toList());
                throw new EntityNotFoundException(String.format("Cannot find all statuses in Name-Set: %s. InvoiceStatuses found with names: %s", statusToItems.keySet(), statusNames));
            }

            accessors.forEach(accessor -> accessor.setStatus(null));
            statuses.forEach(status -> statusToItems.get(status.getName()).forEach(item -> item.setStatus(status)));
        }
    }

}
