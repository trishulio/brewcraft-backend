package io.company.brewcraft.repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.company.brewcraft.model.InvoiceItem;
import io.company.brewcraft.service.InvoiceItemAccessor;
import io.company.brewcraft.service.exception.EntityNotFoundException;

public class EnhancedInvoiceItemRepositoryImpl implements EnhancedInvoiceItemRepository {
    private static final Logger log = LoggerFactory.getLogger(EnhancedInvoiceItemRepositoryImpl.class);

    private MaterialRepository materialRepo;
    private InvoiceItemRepository itemRepo;

    public EnhancedInvoiceItemRepositoryImpl(InvoiceItemRepository itemRepo, MaterialRepository materialRepo) {
        this.materialRepo = materialRepo;
        this.itemRepo = itemRepo;
    }

    @Override
    public void refresh(Collection<InvoiceItem> items) {
        materialRepo.refreshAccessors(items);
    }

    @Override
    public void refreshAccessors(Collection<? extends InvoiceItemAccessor> accessors) {
        if (accessors != null && accessors.size() > 0) {
            Map<Long, List<InvoiceItemAccessor>> lookupAccessorByItemId = accessors.stream().filter(accessor -> accessor != null && accessor.getInvoiceItem() != null).collect(Collectors.groupingBy(accessor -> accessor.getInvoiceItem().getId()));

            List<InvoiceItem> items = itemRepo.findAllById(lookupAccessorByItemId.keySet());

            if (lookupAccessorByItemId.keySet().size() != items.size()) {
                List<Long> itemIds = items.stream().map(item -> item.getId()).collect(Collectors.toList());
                throw new EntityNotFoundException(String.format("Cannot find all materials in Id-Set: %s. InvoiceItems found with Ids: %s", lookupAccessorByItemId.keySet(), itemIds));
            }

            accessors.forEach(accessor -> accessor.setInvoiceItem(null));
            items.forEach(item -> lookupAccessorByItemId.get(item.getId()).forEach(accessor -> accessor.setInvoiceItem(item)));
        }
    }
}
